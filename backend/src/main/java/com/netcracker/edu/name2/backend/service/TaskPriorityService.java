package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.TaskPriority;

import java.util.Optional;

public interface TaskPriorityService {

    Optional<TaskPriority> findByName(String name);
}
