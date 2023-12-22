package com.productservice.dao;

import com.productservice.pojo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDao extends MongoRepository<Product, String> {
}
