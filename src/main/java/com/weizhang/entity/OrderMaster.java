package com.weizhang.entity;

import com.weizhang.enu.OrderStatusEnum;
import com.weizhang.enu.PayStatusEnum;
import com.weizhang.enu.ProductStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Data
public class OrderMaster {
    //订单ID
    @Id
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
    private Date createTime;
    //更新时间
    private Date updateTime;

}
