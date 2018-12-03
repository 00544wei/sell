package com.weizhang.dao;

import com.weizhang.entity.OrderDetail;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId("123456");
        Assert.assertNotEquals(0, orderDetailList.size());
    }

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("10000");
        orderDetail.setOrderId("123456");
        orderDetail.setProductIcon("001.png");
        orderDetail.setProductId("10010");
        orderDetail.setProductName("红烧牛肉面");
        orderDetail.setProductPrice(new BigDecimal(5));
        orderDetail.setProductQuantity(3);
        OrderDetail result = orderDetailDao.save(orderDetail);
        Assert.assertNotNull(result);
    }
}