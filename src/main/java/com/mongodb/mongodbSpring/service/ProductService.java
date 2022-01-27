package com.mongodb.mongodbSpring.service;

import com.mongodb.mongodbSpring.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product findByName(String name);
    List<Product> findAll();
    List<Product> findByStockGreaterThan(Integer stock);
    Product updateProduct(String id,Product product);
}
