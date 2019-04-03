package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.TaskPriority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskPriorityRepository extends CrudRepository<TaskPriority, Long> {

    Optional<TaskPriority> findByName(String name);
}
