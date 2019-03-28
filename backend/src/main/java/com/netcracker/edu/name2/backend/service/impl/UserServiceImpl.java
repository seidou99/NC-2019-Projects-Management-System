package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.repository.UserRepository;
import com.netcracker.edu.name2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository repo;

    @Autowired
    UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Iterable<User> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public User save(User user) {
        user.setId(null);
        return repo.save(user);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
