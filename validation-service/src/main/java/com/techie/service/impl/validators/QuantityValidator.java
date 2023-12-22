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
public class QuantityValidator implements Validator {

    private static final String QUANTITY_REGEX = "^(?:[1-9][0-9]?|100)$";
    @Override
    public void doValidate(OrderRequest orderRequest) {

        List<String> quantityList =
                orderRequest.getOrderLineItemsDTOS().stream().map(OrderLineItemsDTO::getQuantity).toList();

        boolean result = quantityList.stream()
                .allMatch(quantity -> !StringUtils.isBlank(quantity) && quantity.matches(QUANTITY_REGEX));

        if (!result) {
            throw new ValidationException(ErrorCodeEnum.QUANTITY_VALIDATION_FAILED.getErrorCode(),
                    ErrorCodeEnum.QUANTITY_VALIDATION_FAILED.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
