package com.techie.controller;

import com.techie.constants.Endpoint;
import com.techie.pojo.OrderRequest;
import com.techie.pojo.OrderResponse;
import com.techie.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Endpoint.VALIDATION_MAPPING)
public class ValidationController {

    @Autowired
    private ValidateService validateService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse validateRequest(@RequestBody OrderRequest orderRequest) {
           return validateService.validateOrderRequest(orderRequest);
    }

}
