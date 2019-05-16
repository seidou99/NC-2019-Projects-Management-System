package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.UserAuthData;

import java.util.Optional;

public interface UserAuthDataService {

    Optional<UserAuthData> findByEmail(String email);

    UserAuthData save(UserAuthData userAuthData);
}
