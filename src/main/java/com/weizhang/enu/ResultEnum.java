package com.weizhang.enu;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXISTS(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足"),
    ORDER_NOT_EXISTS(20, "订单不存在"),
    ORDERDETAIL_NOT_EXISTS(21, "订单详情不存在"),
    ORDER_STATUS_ERROR(22, "订单状态错误"),
    ORDER_UPDATE_FALT(23, "订单更新失败"),
    ORDER_DETAIL_EMPTY(24, "订单没有商品"),
    ORDER_PAY_STATUS_ERROR(25, "订单支付状态不正确"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
