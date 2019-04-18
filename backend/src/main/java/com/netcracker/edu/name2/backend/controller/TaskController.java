package com.netcracker.edu.name2.backend.controller;

import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.repository.TaskRepository;
import com.netcracker.edu.name2.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
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

    @GetMapping(params = {"page", "size"})
    public Page<Task> getAllTasksByProjectId(@PathVariable("projectId") Long projectId, @RequestParam("page") int page, @RequestParam("size") int size) {
        return this.taskService.getTasksPage(projectId, page, size);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        System.out.println(task);
        return taskService.save(task);
    }

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(value = "/{taskId}")
    public Task findTaskById(@PathVariable(name = "taskId") Long taskId) {
        return taskService.findById(taskId).get();
    }

    @PutMapping(value = "/{taskId}")
    public Task updateTask(@RequestBody Task task) {
        System.out.println(task);
        return taskService.update(task);
    }
}
