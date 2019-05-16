package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<User> findAll();

    Iterable<User> findAllWithRoles(List<String> roles);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    User save(User user);

    void delete(Long id);
}