package com.techie.service.impl.validators;

import com.techie.constants.ErrorCodeEnum;
import com.techie.exception.ValidationException;
import com.techie.pojo.OrderRequest;
import com.techie.service.Validator;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PriceValidator implements Validator {

    private static final String PRICE_REGEX = "^[0-9]{1,7}([.][0-9]{2})?$";
    @Override
    public void doValidate(OrderRequest orderRequest) {
        List<String> priceList = orderRequest.getOrderLineItemsDTOS()
                .stream().map(orderLineItemsDTO -> orderLineItemsDTO.getPrice()).toList();
        boolean result = priceList.stream().allMatch(price -> !StringUtils.isBlank(price) && price.matches(PRICE_REGEX));
        if (!result) {
            throw new ValidationException(ErrorCodeEnum.PRICE_VALIDATION_FAILED.getErrorCode(),
                    ErrorCodeEnum.PRICE_VALIDATION_FAILED.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
