package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.UserAuthData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthDataRepository extends CrudRepository<UserAuthData, Long> {

    Optional<UserAuthData> findByEmail(String email);
}
