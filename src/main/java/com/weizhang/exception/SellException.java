package com.weizhang.exception;

import com.weizhang.enu.ResultEnum;

public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = code;
    }
}
