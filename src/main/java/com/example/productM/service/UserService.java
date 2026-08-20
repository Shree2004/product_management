package com.example.productM.service;

import com.example.productM.dto.UserRequest;
import com.example.productM.entity.Role;
import com.example.productM.entity.User;
import com.example.productM.enums.RoleName;
import com.example.productM.repository.RoleRepository;
import com.example.productM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.productM.dto.LoginRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserRequest userRequest) {

        RoleName roleName = RoleName.USER;

        if (userRequest.getName().endsWith("_admin")) {
            roleName = RoleName.ADMIN;
        }
        else if (userRequest.getName().endsWith("_superAdmin")) {
            roleName = RoleName.SUPER_ADMIN;
        }

        Role role = roleRepository.findByName(roleName).orElse(null);

        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }

        User user = new User();

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);;

        if(user == null){
           throw new RuntimeException("User not found");
        }
        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if(!passwordMatches){
            throw new RuntimeException("Password Incorrect");
        }

        return "Login successful";
    }
}