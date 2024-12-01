package com.tisitha.cshop.service;

import com.tisitha.cshop.model.CartItem;
import com.tisitha.cshop.model.Product;
import com.tisitha.cshop.repository.CartItemRepository;
import com.tisitha.cshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public CartItem getCartItem(Long id){
        return cartItemRepository.findById(id).orElseThrow(()->new RuntimeException("Cart Item not found"));
    }

    public List<CartItem> getCartItems(Long id){
        return cartItemRepository.findAllByCustomerId(id);
    }

    public CartItem addCart(CartItem cartItem){
        CartItem oldCartItem = cartItemRepository.findByProductIdCustomerId(cartItem.getCustomerId(),cartItem.getProductId());
        if(oldCartItem!=null){
            oldCartItem.setQuantity(oldCartItem.getQuantity()+cartItem.getQuantity());
            return cartItemRepository.save(oldCartItem);
        }
        else{
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            cartItem.setTitle(product.getName());
            cartItem.setCategory(product.getCategory());
            cartItem.setImgUrl(product.getImgUrl());
            cartItem.setPrice(product.getPrice());
            cartItem.setDeal(product.getDeal());
            return cartItemRepository.save(cartItem);
        }
    }

    public void deleteCartItem(Long id){
        cartItemRepository.deleteById(id);
    }

    public void updateCartItem(Long id,CartItem updatedCartItem){
        CartItem cartItem = getCartItem(id);
        cartItem.setQuantity(updatedCartItem.getQuantity());
        cartItemRepository.save(cartItem);
    }
}