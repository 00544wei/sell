package com.weizhang;

import com.weizhang.dao.ProductCategoryDao;
import com.weizhang.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellApplicationTests {
    @Autowired
    ProductCategoryDao productCategoryDao;
    @Test
    public void contextLoads() {
        Optional<ProductCategory> optional = productCategoryDao.findById(1);

    }

}
