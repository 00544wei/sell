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
    ORDER_CREATE_FALT_PARAMETER_ERROR(26, "订单创建失败,参数不正确"),
    ORDER_OWNER_ERROR(27, "不是本人的订单"),

    PARAM_ERROR(30, "参数错误"),

    CART_EMPTY(40, "购物车为空"),
    SCAN_CODE_FAIL(50, "扫码失败"),

    WECHAT_AUTHORIZE_ERROR(100, "微信网页授权失败"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
