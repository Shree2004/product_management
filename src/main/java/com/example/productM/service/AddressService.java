package com.example.productM.service;

import com.example.productM.dto.AddressRequest;
import com.example.productM.entity.Address;
import com.example.productM.entity.User;
import com.example.productM.repository.AddressRepository;
import com.example.productM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private  AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    public Address addAddress(Long userId, AddressRequest addressRequest) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Address address = new Address();

        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setPincode(addressRequest.getPincode());
        address.setCountry(addressRequest.getCountry());
        address.setAddressType(addressRequest.getAddressType());
        address.setUser(user);

        return addressRepository.save(address);
    }


    public List<Address> getAddressesByUserId(Long userId) {

        return addressRepository.findByUserId(userId);
    }


    public void deleteAddress(Long userId, Long addressId) {

        Address address = addressRepository.findById(addressId).orElse(null);

        if (address == null) {
            throw new RuntimeException("Address not found");
        }
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot delete another user's address");
        }
        addressRepository.delete(address);
    }
}