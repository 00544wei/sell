package com.weizhang.dto;

import com.weizhang.entity.OrderDetail;
import com.weizhang.entity.OrderMaster;
import lombok.Data;

import java.util.List;
@Data
public class OrderDTO extends OrderMaster {
    private List<OrderDetail> orderDetailList;
}
