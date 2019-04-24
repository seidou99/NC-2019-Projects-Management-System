package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.entity.UserAuthData;
import com.netcracker.edu.name2.backend.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAuthData(UserAuthData authData);

    @Query("select u from User u where u.role.name in :roles")
    Iterable<User> findAllWithRoles(@Param("roles") List<String> roles);
}
