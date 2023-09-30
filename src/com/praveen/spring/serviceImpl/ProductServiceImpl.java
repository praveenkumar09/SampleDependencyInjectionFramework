package com.praveen.spring.serviceImpl;

import com.praveen.spring.annotation.Autowired;
import com.praveen.spring.annotation.Component;
import com.praveen.spring.model.Product;
import com.praveen.spring.repository.ProductRepository;
import com.praveen.spring.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public List<Product> getFinalPrice(List<Product> items) {
        return repo.getPrice(items).stream()
                .peek(product -> product.setPrice(product.getPrice() * (100 - product.getDiscount()) / 100))
                .collect(Collectors.toList());
    }
}
