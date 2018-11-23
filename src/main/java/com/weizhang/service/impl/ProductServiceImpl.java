package com.weizhang.service.impl;

import com.weizhang.dao.ProductInfoDao;
import com.weizhang.entity.ProductInfo;
import com.weizhang.enu.ProductStatusEnum;
import com.weizhang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> optional = productInfoDao.findById(productId);
        return optional.get();
    }

    @Override
    public List<ProductInfo> findpUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }
}
