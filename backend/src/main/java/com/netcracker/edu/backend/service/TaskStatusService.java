package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.TaskStatus;

import java.util.Optional;

public interface TaskStatusService {

    Optional<TaskStatus> findByName(String name);
}
