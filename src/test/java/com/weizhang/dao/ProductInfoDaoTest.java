package com.weizhang.dao;

import com.weizhang.entity.ProductInfo;
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
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("网球");
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("this is a teenis");
        productInfo.setProductPrice(new BigDecimal(10.00));
        productInfo.setProductStock(100);
        productInfo.setProductIcon("teenis.png");
        productInfo.setProductStatus(0);
        productInfoDao.save(productInfo);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}