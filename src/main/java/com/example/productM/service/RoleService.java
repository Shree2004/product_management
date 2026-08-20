package com.example.productM.service;

import com.example.productM.entity.Role;
import com.example.productM.enums.RoleName;
import com.example.productM.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(RoleName roleName) {

        Role role = new Role();
        role.setName(roleName);

        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}