package com.weizhang.dao;

import com.weizhang.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {

    public List<OrderDetail> findByOrderId (String orderId);
}
