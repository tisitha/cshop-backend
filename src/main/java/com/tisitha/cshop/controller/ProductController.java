package com.tisitha.cshop.controller;

import com.tisitha.cshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProduct(@RequestParam("id")Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/category={category}")
    public ResponseEntity<?> getFromCategory(@PathVariable("category") String category){
        return new ResponseEntity<>(productService.findByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<?> getNewProducts(){
        return new ResponseEntity<>(productService.isNew(), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopProduct(){
        return new ResponseEntity<>(productService.isTop(), HttpStatus.OK);
    }

    @GetMapping("/deal")
    public ResponseEntity<?> getDeals(){
        return new ResponseEntity<>(productService.isDeal(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("item") String item){
        return new ResponseEntity<>(productService.search(item), HttpStatus.OK);
    }

}