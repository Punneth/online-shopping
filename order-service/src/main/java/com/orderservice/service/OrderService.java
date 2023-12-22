package com.orderservice.service;

import com.orderservice.dto.OrderRequest;
import com.orderservice.pojo.OrderResponse;

public interface OrderService {
    OrderResponse placeOrders(OrderRequest orderRequest);
}
