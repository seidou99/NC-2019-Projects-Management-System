package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.User;
import com.netcracker.edu.backend.entity.UserAuthData;
import com.netcracker.edu.backend.entity.UserRole;
import com.netcracker.edu.backend.repository.UserRepository;
import com.netcracker.edu.backend.repository.UserRoleRepository;
import com.netcracker.edu.backend.service.UserAuthDataService;
import com.netcracker.edu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserAuthDataService userAuthDataService;
    private UserRoleRepository userRoleRepository;

    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserServiceImpl(UserRepository userRepository, UserAuthDataService userAuthDataService,
                    UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userAuthDataService = userAuthDataService;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> findAllWithRoles(List<String> roles) {
        return userRepository.findAllWithRoles(roles);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();
        Optional<UserAuthData> userAuthData = userAuthDataService.findByEmail(email);
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
        user.getAuthData().setId(null);
        user.setId(null);
        String roleName = user.getRole().getName();
        Optional<UserRole> role = userRoleRepository.findByName(roleName);
        if (!role.isPresent()) {
            throw new RuntimeException("Role '" + roleName + "' not found");
        }
        user.setRole(role.get());
        String passwordHash = encoder().encode(user.getAuthData().getPassword());
        user.getAuthData().setPassword(passwordHash);
        if (userAuthDataService.findByEmail(user.getAuthData().getEmail()).isPresent()) {
            throw new RuntimeException("User with email '" + user.getAuthData().getEmail() + "' already exists");
        }
        userAuthDataService.save(user.getAuthData());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
