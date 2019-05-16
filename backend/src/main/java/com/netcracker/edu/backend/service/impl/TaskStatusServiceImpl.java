package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.TaskStatus;
import com.netcracker.edu.backend.repository.TaskStatusRepository;
import com.netcracker.edu.backend.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    public Optional<TaskStatus> findByName(String name) {
        return taskStatusRepository.findByName(name);
    }
}
