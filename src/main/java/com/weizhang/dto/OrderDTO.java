package com.weizhang.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weizhang.entity.OrderDetail;
import com.weizhang.entity.OrderMaster;
import com.weizhang.enu.OrderStatusEnum;
import com.weizhang.enu.PayStatusEnum;
import com.weizhang.util.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class OrderDTO {
    //订单ID
    private String orderId;
    //买家名称
    private String buyerName;
    //买家电话
    private String buyerPhone;
    //买家的openid
    private String buyerOpenid;
    //买家的地址
    private String buyerAddress;
    //订单状态
    private BigDecimal orderAmount;
    //订单状态 默认状态是新下单 0
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //支付状态  ，默认为未支付  0
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
