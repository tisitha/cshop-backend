package com.tisitha.cshop.repository;

import com.tisitha.cshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByCategory(String category);

    List<Product> findAllByIsNew(boolean b);

    List<Product> findAllByIsTop(boolean b);

    List<Product> findAllByDealNot(int i);

    List<Product> findByNameContainingIgnoreCase(String text);
}