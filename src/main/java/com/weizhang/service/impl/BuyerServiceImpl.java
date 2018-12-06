package com.weizhang.service.impl;

import com.weizhang.dto.OrderDTO;
import com.weizhang.enu.ResultEnum;
import com.weizhang.exception.SellException;
import com.weizhang.service.BuyerService;
import com.weizhang.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;
    @Override
    public OrderDTO findOne(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) return null;
        return orderDTO;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if (orderDTO == null){
            return null;
        }

        if (!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】不是本人的订单，传入的openid是{}，订单所有者的openid是{}，orderId={}", openid, orderDTO.getBuyerOpenid(), orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancel(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null){
            log.error("【取消订单】订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        return orderMasterService.cancel(orderDTO);
    }
}
