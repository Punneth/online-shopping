package com.techie.service;

import com.techie.pojo.OrderRequest;
import com.techie.pojo.OrderResponse;

public interface ValidateService {
    OrderResponse validateOrderRequest(OrderRequest orderRequest);
}
