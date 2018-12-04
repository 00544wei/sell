package com.weizhang.enu;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXISTS(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足"),
    ORDER_NOT_EXISTS(20, "订单不存在"),
    ORDERDETAIL_NOT_EXISTS(21, "订单详情不存在"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
