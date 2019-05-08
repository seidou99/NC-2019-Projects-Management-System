package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.UserAuthData;

import java.util.Optional;

public interface UserAuthDataService {

    Optional<UserAuthData> findByEmail(String email);

    UserAuthData save(UserAuthData userAuthData);
}
