package com.netcracker.edu.fapi.service;

import com.netcracker.edu.fapi.model.User;

import java.util.List;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    Iterable<User> findAll();

    Iterable<User> findAllWithRoles(List<String> roles);
}
