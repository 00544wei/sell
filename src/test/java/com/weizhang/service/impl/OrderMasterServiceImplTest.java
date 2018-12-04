package com.weizhang.service.impl;

import com.weizhang.dto.OrderDTO;
import com.weizhang.entity.OrderDetail;
import com.weizhang.entity.OrderMaster;
import com.weizhang.enu.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.plugin.javascript.navig.LinkArray;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    private final String BUYER_OPENID = "110110";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("张玮");
        orderDTO.setBuyerAddress("壹方城");
        orderDTO.setBuyerPhone("110");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(2);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("3");
        orderDetail1.setProductQuantity(3);
        orderDetailList.add(orderDetail1);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderMasterService.create(orderDTO);
        log.info("创建订单。结果是{}",result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderMasterService.findOne("1543895064412425746");
        Assert.assertNotNull(orderDTO);
        log.info("查询订单，result={}", orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(BUYER_OPENID, request);
        log.info("查询订单列表，result={}",orderDTOPage);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderMasterService.findOne("1543895064412425746");
        OrderDTO result = orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void payid() {
        OrderDTO orderDTO = orderMasterService.findOne("1543894541191195924");
        OrderDTO result = orderMasterService.payid(orderDTO);
        Assert.assertEquals(new Integer(1), result.getPayStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderMasterService.findOne("1543894943167796781");
        OrderDTO result = orderMasterService.finish(orderDTO);
        Assert.assertEquals(result.getOrderStatus(), new Integer(1));
    }
}