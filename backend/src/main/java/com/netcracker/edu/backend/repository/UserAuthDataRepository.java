package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.UserAuthData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthDataRepository extends CrudRepository<UserAuthData, Long> {

    Optional<UserAuthData> findByEmail(String email);
}
