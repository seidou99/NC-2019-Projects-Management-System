package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.TaskPriority;

import java.util.Optional;

public interface TaskPriorityService {

    Optional<TaskPriority> findByName(String name);
}
