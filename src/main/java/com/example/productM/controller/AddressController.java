package com.example.productM.controller;

import com.example.productM.dto.AddressRequest;
import com.example.productM.entity.Address;
import com.example.productM.entity.User;
import com.example.productM.service.AddressService;
import com.example.productM.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;


    @PostMapping
    public Address addAddress(Principal principal, @Valid @RequestBody AddressRequest addressRequest) {
        User user = userService.getUserByEmail(principal.getName());
        return addressService.addAddress(user.getId(), addressRequest);
    }

    @GetMapping
    public List<Address> getMyAddresses(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        return addressService.getAddressesByUserId(user.getId());
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(Principal principal, @PathVariable Long addressId) {
        User user = userService.getUserByEmail(principal.getName());
        addressService.deleteAddress(user.getId(), addressId);
        return "Address deleted successfully";
    }
}