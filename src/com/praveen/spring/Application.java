package com.praveen.spring;

import com.praveen.spring.config.AppConfig;
import com.praveen.spring.config.ApplicationContext;
import com.praveen.spring.model.Product;
import com.praveen.spring.repository.ProductRepository;
import com.praveen.spring.service.ProductService;
import com.praveen.spring.serviceImpl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ApplicationContext(AppConfig.class);
        ProductServiceImpl productService = appContext.getBean(ProductServiceImpl.class);
        List<Product> items = new ArrayList<>();
        items.add(new Product("Yoga Mat",40));
        items.add(new Product("Coffee",10));
        items.add(new Product("Tea",20));
        List<Product> finalProductList = productService.getFinalPrice(items);
        finalProductList
                .forEach(System.out::println);
    }
}
