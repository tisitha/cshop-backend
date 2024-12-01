package com.tisitha.cshop.repository;

import com.tisitha.cshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findAllByCustomerId(Long customerId);

    @Query("select ci from CartItem ci where ci.customerId = ?1 and ci.productId = ?2")
    CartItem findByProductIdCustomerId(Long customerId,Long productId);
}
