package com.netcracker.edu.name2.backend.controller;

import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.repository.TaskRepository;
import com.netcracker.edu.name2.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Iterable<Task> getAllTasks() {
        return taskService.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        System.out.println(task);
        return taskService.save(task);
    }

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(value = "/amount")
    public Long getAmount() {
        return taskRepository.countTasksWithProjectId(1L);
    }

    @GetMapping(value = "/{taskId}")
    public Task findTaskById(@PathVariable(name = "taskId") Long taskId) {
        return taskService.findById(taskId).get();
    }
}
