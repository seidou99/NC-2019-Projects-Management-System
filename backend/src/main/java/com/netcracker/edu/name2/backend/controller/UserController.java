package com.netcracker.edu.name2.backend.controller;

import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.repository.UserRepository;
import com.netcracker.edu.name2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/emails/{email}")
    public User findUserByEmail(@PathVariable(name = "email") String email) {
        return userService.findByEmail(email).get();
    }

    @GetMapping(value = "/{userId}")
    public User findUserById(@PathVariable(name = "userId") Long userId) {
        return userService.findById(userId).get();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println(user);
        return userService.save(user);
    }
}
