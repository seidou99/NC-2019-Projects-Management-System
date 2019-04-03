package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepository extends CrudRepository<TaskStatus, Long> {

    Optional<TaskStatus> findByName(String name);
}
