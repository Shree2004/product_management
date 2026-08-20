package com.example.productM.service;

import com.example.productM.dto.CartItemRequest;
import com.example.productM.entity.Cart;
import com.example.productM.entity.CartItem;
import com.example.productM.entity.Product;
import com.example.productM.entity.User;
import com.example.productM.repository.CartItemRepository;
import com.example.productM.repository.CartRepository;
import com.example.productM.repository.ProductRepository;
import com.example.productM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    public CartItem addOrUpdateCartItem(Long userId, CartItemRequest cartItemRequest) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Product product = productRepository.findById(cartItemRequest.getProductId()).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElse(null);

        if (cartItem == null) {

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());

        } else {

            cartItem.setQuantity(cartItemRequest.getQuantity());
        }

        return cartItemRepository.save(cartItem);
    }

    public Cart getCartByUserId(Long userId) {

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        return cart;
    }

    public void removeCartItem(Long userId, Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if (cartItem == null) {
            throw new RuntimeException("Cart item not found");
        }

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot remove another user's cart item");
        }

        cartItemRepository.delete(cartItem);
    }
}