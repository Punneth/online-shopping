package com.techie.service.impl;


import com.google.gson.Gson;
import com.techie.constants.ErrorCodeEnum;
import com.techie.constants.ValidatorEnum;
import com.techie.exception.ValidationException;
import com.techie.http.HttpRequest;
import com.techie.http.WebClientEngine;
import com.techie.pojo.OrderRequest;
import com.techie.pojo.OrderResponse;
import com.techie.pojo.response.ErrorResponse;
import com.techie.service.ValidateService;
import com.techie.service.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    @Value("${shipping.validators}")
    private String validators;

    @Autowired
    private ApplicationContext applicationContext;

    private final WebClientEngine webClientEngine;

    @Value("${order.service.placeorder.url}")
    private String placeOrderUrl;

    @Override
    public OrderResponse validateOrderRequest(OrderRequest orderRequest) {

        List<String> validatorsList = Stream.of(validators.split(",")).toList();

        if (null == validatorsList) {
            throw new ValidationException(ErrorCodeEnum.VALIDATOR_RULE_FAILED.getErrorCode(),
                    ErrorCodeEnum.VALIDATOR_RULE_FAILED.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            validatorsList.forEach(validatorRule -> {
                ValidatorEnum validatorEnum = ValidatorEnum.getEnumByString(validatorRule);
                Validator validator = applicationContext.getBean(validatorEnum.getValidatorClass());
                validator.doValidate(orderRequest);
            });
        }

        Gson gson = new Gson();

        HttpRequest httpRequest = HttpRequest.builder().request(gson.toJson(orderRequest))
                .url(placeOrderUrl).httpMethod(HttpMethod.POST).build();

        ResponseEntity<String> response = webClientEngine.execute(httpRequest);

        if (null == response || null != response.getBody()) {
            throw new ValidationException(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(),
                    ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (200 != response.getStatusCodeValue()) {
            ErrorResponse errorResponse = gson.fromJson(response.getBody(), ErrorResponse.class);

            throw new ValidationException(errorResponse.getErrorCode(),
                    errorResponse.getErrorMessage(), HttpStatus.BAD_GATEWAY);
        }

        return gson.fromJson(response.getBody(), OrderResponse.class);
    }
}
