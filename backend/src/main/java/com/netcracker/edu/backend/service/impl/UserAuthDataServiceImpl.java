package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.UserAuthData;
import com.netcracker.edu.backend.repository.UserAuthDataRepository;
import com.netcracker.edu.backend.service.UserAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthDataServiceImpl implements UserAuthDataService {

    private UserAuthDataRepository userAuthDataRepository;

    @Autowired
    public UserAuthDataServiceImpl(UserAuthDataRepository userAuthDataRepository) {
        this.userAuthDataRepository = userAuthDataRepository;
    }

    @Override
    public Optional<UserAuthData> findByEmail(String email) {
        return userAuthDataRepository.findByEmail(email);
    }

    @Override
    public UserAuthData save(UserAuthData userAuthData) {
        return userAuthDataRepository.save(userAuthData);
    }
}
