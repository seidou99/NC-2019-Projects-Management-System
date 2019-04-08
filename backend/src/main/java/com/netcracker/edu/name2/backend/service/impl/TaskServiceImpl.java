package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.*;
import com.netcracker.edu.name2.backend.repository.*;
import com.netcracker.edu.name2.backend.service.TaskService;
import com.netcracker.edu.name2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskPriorityRepository taskPriorityRepository;
    private TaskStatusRepository taskStatusRepository;
    private UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService,
                           TaskPriorityRepository taskPriorityRepository, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.taskPriorityRepository = taskPriorityRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.userService = userService;
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        task.setId(null);
        Optional<TaskPriority> priority = taskPriorityRepository.findByName(task.getPriority().getName());
        task.setPriority(priority.get());
        Optional<TaskStatus> openStatus = taskStatusRepository.findByName("Open");
        task.setStatus(openStatus.get());
        task.setCreated(new Date());
        task.setCode(taskRepository.countTasksWithProjectId(task.getProject().getId()) + 1);
        taskRepository.save(task);
        return task;
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
