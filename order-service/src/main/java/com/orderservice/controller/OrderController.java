package com.orderservice.controller;

import com.orderservice.constansts.Endpoint;
import com.orderservice.dto.OrderRequest;
import com.orderservice.pojo.OrderResponse;
import com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Endpoint.ORDER_MAPPING)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = Endpoint.PLACE_ORDERS)
    public ResponseEntity<OrderResponse> placeOrders(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.placeOrders(orderRequest), HttpStatus.CREATED);
    }

}
