package com.productservice.controller;

import com.productservice.constants.Endpoint;
import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Endpoint.PRODUCT_MAPPING)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = Endpoint.CREATE_PRODUCT)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping(value = Endpoint.VIEW_PRODUCTS)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        return productResponses;
    }

}
