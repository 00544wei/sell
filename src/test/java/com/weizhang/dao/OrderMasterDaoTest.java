package com.weizhang.dao;

import com.weizhang.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<OrderMaster> result = orderMasterDao.findByBuyerOpenid("110110", pageRequest);
        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());
    }

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setBuyerName("张玮");
        orderMaster.setBuyerAddress("壹方城中心");
        orderMaster.setBuyerPhone("110");
        orderMaster.setOrderAmount(new BigDecimal(10000));
        OrderMaster result = orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }
}