package com.praveen.spring.service;

import com.praveen.spring.model.Product;

import java.util.List;


public interface ProductService {
    public List<Product> getFinalPrice(List<Product> items);
}
