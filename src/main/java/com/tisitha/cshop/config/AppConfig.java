package com.tisitha.cshop.config;

import com.tisitha.cshop.model.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Customer customer(){
        return new Customer();
    }

}
