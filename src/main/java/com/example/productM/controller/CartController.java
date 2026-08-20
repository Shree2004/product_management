package com.example.productM.controller;

import com.example.productM.dto.CartItemRequest;
import com.example.productM.entity.Cart;
import com.example.productM.entity.CartItem;
import com.example.productM.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/user/{userId}")
    public CartItem addOrUpdateCartItem(@PathVariable Long userId, @Valid @RequestBody CartItemRequest cartItemRequest) {
        return cartService.addOrUpdateCartItem(userId, cartItemRequest);
    }

    @GetMapping("/user/{userId}")
    public Cart getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @DeleteMapping("/item/{cartItemId}")
    public String removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return "Cart item removed successfully";
    }
}