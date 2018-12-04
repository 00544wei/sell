package com.weizhang.service.impl;

import com.weizhang.converter.OrderMaster2OrderDTOConverter;
import com.weizhang.dao.OrderDetailDao;
import com.weizhang.dao.OrderMasterDao;
import com.weizhang.dto.CartDTO;
import com.weizhang.dto.OrderDTO;
import com.weizhang.entity.OrderDetail;
import com.weizhang.entity.OrderMaster;
import com.weizhang.entity.ProductInfo;
import com.weizhang.enu.OrderStatusEnum;
import com.weizhang.enu.PayStatusEnum;
import com.weizhang.enu.ResultEnum;
import com.weizhang.exception.SellException;
import com.weizhang.service.OrderMasterService;
import com.weizhang.util.GenerateKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = GenerateKeyUtils.generateUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //查询商品 查询商品的价格、数量
        for (OrderDetail orderDetail :orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            //计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                .add(orderAmount);
            //写入订单数据库  orderMaster orderDetail
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(GenerateKeyUtils.generateUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);
        }

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterDao.save(orderMaster);
        //扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e-> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> orderMaster = orderMasterDao.findById(orderId);
        if (orderMaster.get() == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }

        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXISTS);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster.get(), orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确 orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if (result == null){
            log.error("【取消订单】 更新失败，orderMaster={}", result);
            throw new SellException(ResultEnum.ORDER_UPDATE_FALT);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】 订单中没有商品详情，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
//        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
//        BeanUtils.copyProperties(orderDTO.getOrderDetailList(), cartDTOList);
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->
                new CartDTO(e.getProductId(), e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已支付，需要进行退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO payid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】 订单支付状态不正确， orderDTO={}",  orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null){
            log.error("【完结订单】更新订单失败，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_FALT);
        }
        return orderDTO;
    }
}
