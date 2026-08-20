package com.example.productM.controller;

import com.example.productM.dto.CartItemRequest;
import com.example.productM.entity.Cart;
import com.example.productM.entity.CartItem;
import com.example.productM.entity.User;
import com.example.productM.service.CartService;
import com.example.productM.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping
    public CartItem addOrUpdateCartItem(Principal principal, @Valid @RequestBody CartItemRequest cartItemRequest) {
        User user = userService.getUserByEmail(principal.getName());
        return cartService.addOrUpdateCartItem(
                user.getId(),
                cartItemRequest
        );
    }

    @GetMapping
    public Cart getCart(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        return cartService.getCartByUserId(user.getId());
    }

    @DeleteMapping("/item/{cartItemId}")
    public String removeCartItem(Principal principal, @PathVariable Long cartItemId) {
        User user = userService.getUserByEmail(principal.getName());
        cartService.removeCartItem(user.getId(), cartItemId);
        return "Cart item removed successfully";
    }
}