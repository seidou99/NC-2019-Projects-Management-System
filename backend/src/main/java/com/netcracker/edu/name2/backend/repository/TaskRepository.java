package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
