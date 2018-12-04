package com.weizhang.service;

import com.weizhang.dto.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderMasterService {

    /*创建订单*/
    public OrderDTO create(OrderDTO orderDTO);

    /*查询单个订单*/
    public OrderDTO findOne(String orderId);

    /*查询订单列表*/
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    /*取消订单*/
    public OrderDTO cancel(OrderDTO orderDTO);
    /*支付订单*/
    public OrderDTO payid(OrderDTO orderDTO);
    /*完结订单*/
    public OrderDTO finish(OrderDTO orderDTO);
}
