package com.netcracker.edu.name.service;

import com.netcracker.edu.name.models.User;

import java.util.List;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    Iterable<User> findAll();

    Iterable<User> findAllWithRoles(List<String> roles);
}
