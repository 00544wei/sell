package com.weizhang.service.impl;

import com.weizhang.dao.ProductInfoDao;
import com.weizhang.dto.CartDTO;
import com.weizhang.entity.ProductInfo;
import com.weizhang.enu.ProductStatusEnum;
import com.weizhang.enu.ResultEnum;
import com.weizhang.exception.SellException;
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

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            Optional<ProductInfo> productInfo = productInfoDao.findById(cartDTO.getProductId());
            if (productInfo.get() == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            Integer stock = productInfo.get().getProductStock() - cartDTO.getProductQuantity();
            if (stock < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.get().setProductStock(stock);
            productInfoDao.save(productInfo.get());
        }
    }
}
