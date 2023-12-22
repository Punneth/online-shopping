package com.techie.constants;

import com.techie.service.Validator;
import com.techie.service.impl.validators.PriceValidator;
import com.techie.service.impl.validators.QuantityValidator;
import com.techie.service.impl.validators.SkuCodeValidator;

import java.util.Arrays;

public enum ValidatorEnum {
    PRICE_FILTER("PRICE_FILTER", PriceValidator.class),
    SKUCODE_FILTER("SKUCODE_FILTER", SkuCodeValidator.class),
    QUANTITY_FILTER("QUANTITY_FILTER", QuantityValidator.class)
    ;

    private String validatorName;
    private Class<? extends Validator> validatorClass;
    private ValidatorEnum(String validatorName, Class<? extends Validator> validatorClass) {
        this.validatorName = validatorName;
        this.validatorClass = validatorClass;
    }

    public static ValidatorEnum getEnumByString(String validatorName) {

        ValidatorEnum valEnum = Arrays.stream(ValidatorEnum.values()).filter(validatorEnum ->
                validatorEnum.validatorName.equals(validatorName)).findFirst().orElse(null);

        return valEnum;
    }

    public Class<? extends Validator> getValidatorClass() {
        return validatorClass;
    }

}
