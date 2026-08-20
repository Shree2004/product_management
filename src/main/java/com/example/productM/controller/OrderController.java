package com.example.productM.controller;

import com.example.productM.dto.OrderRequest;
import com.example.productM.entity.OrderEntity;
import com.example.productM.entity.User;
import com.example.productM.service.OrderService;
import com.example.productM.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @PostMapping
    public OrderEntity placeOrder(Principal principal, @Valid @RequestBody OrderRequest orderRequest) {
        User user = userService.getUserByEmail(principal.getName());
        return orderService.placeOrder(user.getId(), orderRequest);
    }
}