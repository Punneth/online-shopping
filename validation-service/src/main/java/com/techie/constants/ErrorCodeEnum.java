package com.techie.constants;

import lombok.Getter;

public enum ErrorCodeEnum {
    PRICE_VALIDATION_FAILED("10001", "Bad request, given price parameter is not valid or empty"),
    SKU_CODE_VALIDATION_FAILED("10002",
            "Bad request, given skuCode parameter is not valid or empty "),
    QUANTITY_VALIDATION_FAILED("10003",
            "Bad request, given quantity parameter is not valid or empty"),
    VALIDATOR_RULE_FAILED("10004", "Internal server error, please try again later"),
    GENERIC_EXCEPTION("10005", "Something went wrong, Please try again later");

    @Getter
    private String errorCode;
    @Getter
    private String errorMessage;
    private ErrorCodeEnum(String errorCode, String errorCodeMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorCodeMessage;
    }
}
