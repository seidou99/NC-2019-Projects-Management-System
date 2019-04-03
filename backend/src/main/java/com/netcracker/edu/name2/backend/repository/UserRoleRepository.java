package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    Optional<UserRole> findByName(String name);
}
