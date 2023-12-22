package com.techie.service.impl.validators;

import com.techie.constants.ErrorCodeEnum;
import com.techie.exception.ValidationException;
import com.techie.pojo.OrderLineItemsDTO;
import com.techie.pojo.OrderRequest;
import com.techie.service.Validator;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuCodeValidator implements Validator {

    private static final String SKUCODE_FORMAT = "skuCode_";
    @Override
    public void doValidate(OrderRequest orderRequest) {
        List<String> skuCodeList = orderRequest.getOrderLineItemsDTOS()
                .stream().map(OrderLineItemsDTO::getSkuCode).toList();

        boolean result = skuCodeList.stream()
                .allMatch(skuCode -> !StringUtils.isBlank(skuCode) && skuCode.startsWith(SKUCODE_FORMAT));

        if (!result) {
            throw new ValidationException(ErrorCodeEnum.SKU_CODE_VALIDATION_FAILED.getErrorCode(),
                    ErrorCodeEnum.SKU_CODE_VALIDATION_FAILED.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
