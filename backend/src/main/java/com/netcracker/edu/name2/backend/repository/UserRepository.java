package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.entity.UserAuthData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAuthData(UserAuthData authData);

    Optional<User> findByAuthDataEmail(String email);
}
