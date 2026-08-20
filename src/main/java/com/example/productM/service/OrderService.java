package com.example.productM.service;

import com.example.productM.dto.OrderRequest;
import com.example.productM.entity.*;
import com.example.productM.enums.OrderStatus;
import com.example.productM.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;


    public OrderEntity placeOrder(Long userId, OrderRequest orderRequest) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Address address = addressRepository
                .findById(orderRequest.getAddressId())
                .orElse(null);

        if (address == null) {
            throw new RuntimeException("Address not found");
        }

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("This address does not belong to the user");
        }

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {

            Inventory inventory = inventoryRepository
                    .findByProductId(cartItem.getProduct().getId())
                    .orElse(null);

            if (inventory == null) {
                throw new RuntimeException("Inventory not found");
            }

            if (inventory.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient inventory");
            }

            BigDecimal price = cartItem.getProduct().getPrice();

            int quantity = cartItem.getQuantity();

            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));

            totalAmount = totalAmount.add(itemTotal);
        }

        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);

        order = orderRepository.save(order);


        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            orderItemRepository.save(orderItem);


            Inventory inventory = inventoryRepository
                    .findByProductId(cartItem.getProduct().getId())
                    .orElse(null);

            inventory.setQuantity(
                    inventory.getQuantity() - cartItem.getQuantity()
            );

            inventoryRepository.save(inventory);
        }


        cartItemRepository.deleteAll(cart.getCartItems());

        return order;
    }
}