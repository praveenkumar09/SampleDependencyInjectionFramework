package com.praveen.spring.config;


import com.praveen.spring.annotation.ComponentScan;
import com.praveen.spring.annotation.Configuration;

@Configuration
@ComponentScan("com.praveen.spring.repository")
@ComponentScan("com.praveen.spring.serviceImpl")
public class AppConfig {
}
