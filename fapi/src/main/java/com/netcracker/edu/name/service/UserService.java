package com.netcracker.edu.name.service;

import com.netcracker.edu.name.models.User;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    Iterable<User> findAll();
}
