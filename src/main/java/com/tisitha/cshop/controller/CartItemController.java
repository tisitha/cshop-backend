package com.tisitha.cshop.controller;

import com.tisitha.cshop.model.CartItem;
import com.tisitha.cshop.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartItem(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartItemService.getCartItem(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCartItems(@RequestParam("cid") Long id) {
        return new ResponseEntity<>(cartItemService.getCartItems(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addCartItem(@RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartItemService.addCart(cartItem), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") Long id) {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable("id") Long id, @RequestBody CartItem cartItem) {
        cartItemService.updateCartItem(id, cartItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}