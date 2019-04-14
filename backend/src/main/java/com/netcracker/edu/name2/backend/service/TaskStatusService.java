package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.TaskStatus;

import java.util.Optional;

public interface TaskStatusService {

    Optional<TaskStatus> findByName(String name);
}
