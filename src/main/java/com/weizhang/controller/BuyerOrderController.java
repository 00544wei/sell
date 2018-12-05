package com.weizhang.controller;

import com.weizhang.converter.OrderForm2OrderDTOConverter;
import com.weizhang.dto.OrderDTO;
import com.weizhang.enu.ResultEnum;
import com.weizhang.exception.SellException;
import com.weizhang.form.OrderForm;
import com.weizhang.service.impl.OrderMasterServiceImpl;
import com.weizhang.util.ResultVOUtils;
import com.weizhang.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】 创建订单失败，参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.ORDER_CREATE_FALT_PARAMETER_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 创建订单失败，购物车为空，orderForm={}", orderForm);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderMasterService.create(orderDTO);

        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtils.success(map);
    }
    //查看列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(openid, request);

        return ResultVOUtils.success(orderDTOPage.getContent());
    }
    //查看详情
    @RequestMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        return ResultVOUtils.success(orderDTO);
    }
    //支付订单

    //取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        orderMasterService.cancel(orderDTO);
        return ResultVOUtils.success();
    }
}
