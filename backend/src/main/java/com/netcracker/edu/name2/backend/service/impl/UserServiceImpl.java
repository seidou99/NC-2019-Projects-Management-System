package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.entity.UserAuthData;
import com.netcracker.edu.name2.backend.entity.UserRole;
import com.netcracker.edu.name2.backend.repository.UserAuthDataRepository;
import com.netcracker.edu.name2.backend.repository.UserRepository;
import com.netcracker.edu.name2.backend.repository.UserRoleRepository;
import com.netcracker.edu.name2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserAuthDataRepository userAuthDataRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository, UserAuthDataRepository userAuthDataRepository,
                    UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userAuthDataRepository = userAuthDataRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();
        Optional<UserAuthData> userAuthData = userAuthDataRepository.findByEmail(email);
        if (userAuthData.isPresent()) {
            result = userRepository.findByAuthData(userAuthData.get());
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        User result = null;
        user.getAuthData().setId(null);
        user.setId(null);
        String roleName = user.getRole().getName();
        Optional<UserRole> role = userRoleRepository.findByName(roleName);
        if (role.isPresent()) {
            user.setRole(role.get());
            userAuthDataRepository.save(user.getAuthData());
            result = userRepository.save(user);
        }

        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
