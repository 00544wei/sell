package com.weizhang.service;

import com.weizhang.dto.CartDTO;
import com.weizhang.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    /**
     * 根据商品id查询商品信息
     * @param productId
     * @return
     */
    public ProductInfo findOne(String productId);

    /**
     * 查询所有的在架商品
     * @return
     */
    public List<ProductInfo> findpUpAll();

    /**
     * 管理端查询所有的商品
     * @param pageable
     * @return
     */
    public Page<ProductInfo> findAll(Pageable pageable);

    public ProductInfo save(ProductInfo productInfo);

    //加库存
    public void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    public void decreaseStock(List<CartDTO> cartDTOList);
}
