package com.weizhang.service.impl;

import com.weizhang.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1");
        System.out.print(productInfo.toString());
    }

    @Test
    public void findpUpAll() {
        List<ProductInfo> productInfoList = productService.findpUpAll();
        System.out.print(productInfoList.toString());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<ProductInfo> pageInfo = productService.findAll(pageRequest);
        System.out.println(pageInfo.getTotalElements());
        System.out.println(pageInfo.getTotalPages());
        System.out.println(pageInfo.getContent());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("s31312");
        productInfo.setProductName("basketball");
        productInfo.setProductPrice(new BigDecimal(80));
        productInfo.setProductStock(20);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("basketball.png");
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("this is a basketball");
        ProductInfo result = productService.save(productInfo);
        System.out.println(result);
    }

    @Test
    public void increaseStock() {
    }

    @Test
    public void decreaseStock() {
//        productService.decreaseStock();
    }
}