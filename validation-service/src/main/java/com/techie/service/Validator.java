package com.techie.service;

import com.techie.pojo.OrderRequest;

public interface Validator {
    void doValidate(OrderRequest orderRequest);
}
