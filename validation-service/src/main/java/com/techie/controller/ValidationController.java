package com.techie.controller;

import com.techie.constants.Endpoint;
import com.techie.pojo.OrderRequest;
import com.techie.pojo.OrderResponse;
import com.techie.service.ValidateService;
import com.techie.util.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = Endpoint.VALIDATION_MAPPING)
public class ValidationController {

    private static final Logger LOGGER = LogManager.getLogger(ValidationController.class);

    @Autowired
    private ValidateService validateService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse validateRequest(@RequestBody OrderRequest orderRequest) {
        System.out.println("This is inside");
        LogMessage.setLogMessagePrefix("/VALIDATION");
        LogMessage.log(LOGGER, "validate the request" + orderRequest);
           return validateService.validateOrderRequest(orderRequest);
    }

}
