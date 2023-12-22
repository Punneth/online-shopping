package com.orderservice.constansts;

import lombok.Getter;


public enum ErrorCodeEnum {
    GENERIC_EXCEPTION("10001", "Something went wrong, please try again later"),
    FAILED_TO_CREATE_ORDER("1002", "Something went wrong, Order creation failed"),
    ITEM_NOT_EXIST("1003", "Bad Request, Item doesn't exists in Inventory");

    @Getter
    private String errorCode;

    @Getter
    private String errorMessage;

    private ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
