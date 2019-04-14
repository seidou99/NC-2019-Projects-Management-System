package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.TaskStatus;
import com.netcracker.edu.name2.backend.repository.TaskStatusRepository;
import com.netcracker.edu.name2.backend.service.TaskStatusService;
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
