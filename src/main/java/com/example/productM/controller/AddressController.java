package com.example.productM.controller;

import com.example.productM.dto.AddressRequest;
import com.example.productM.entity.Address;
import com.example.productM.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public Address addAddress(@Valid @RequestBody AddressRequest addressRequest) {
        return addressService.addAddress(addressRequest);
    }

    @GetMapping("/user/{userId}")
    public List<Address> getAddressesByUserId(@PathVariable Long userId) {
        return addressService.getAddressesByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "Address deleted successfully";
    }
}