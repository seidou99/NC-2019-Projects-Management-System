package com.netcracker.edu.name2.backend.controller;

import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
