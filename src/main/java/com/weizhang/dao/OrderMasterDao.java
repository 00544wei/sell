package com.weizhang.dao;

import com.weizhang.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDao extends JpaRepository<OrderMaster, String> {

    public Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
