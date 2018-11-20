package com.weizhang.dao;

import com.weizhang.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {
}
