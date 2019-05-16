package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepository extends CrudRepository<TaskStatus, Long> {

    Optional<TaskStatus> findByName(String name);
}
