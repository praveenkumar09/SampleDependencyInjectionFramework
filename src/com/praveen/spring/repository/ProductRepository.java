package com.praveen.spring.repository;

import com.praveen.spring.annotation.Component;
import com.praveen.spring.model.Product;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProductRepository {
    public List<Product> getPrice(List<Product> items) {
        return items.stream()
                .peek(product -> product.setPrice((double) Math.round(30 * Math.random())))
                .collect(Collectors.toList());
    }
}
