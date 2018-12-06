package com.weizhang.service;

import com.weizhang.dto.OrderDTO;

public interface BuyerService {
    //查询一个订单
    public OrderDTO findOne(String openid, String orderId);
    //取消订单
    public OrderDTO cancel(String openid, String orderId);
}
