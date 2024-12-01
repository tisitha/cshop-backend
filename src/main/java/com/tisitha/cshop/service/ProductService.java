package com.tisitha.cshop.service;

import com.tisitha.cshop.model.Product;
import com.tisitha.cshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findByCategory(String category){
        return productRepository.findAllByCategory(category);
    }

    public List<Product> isNew(){
        return productRepository.findAllByIsNew(true);
    }

    public List<Product> isTop(){
        return productRepository.findAllByIsTop(true);
    }

    public List<Product> isDeal(){
        return productRepository.findAllByDealNot(0);
    }

    public List<Product> search(String text){
        return productRepository.findByNameContainingIgnoreCase(text);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid Product"));
    }

}