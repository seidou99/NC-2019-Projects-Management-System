package com.netcracker.edu.backend.controller;

import com.netcracker.edu.backend.entity.Task;
import com.netcracker.edu.backend.repository.TaskRepository;
import com.netcracker.edu.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping(params = {"page", "size", "sortBy", "orderBy"})
    public Page<Task> getTasksPageByProjectId(@PathVariable("projectId") Long projectId, @RequestParam("page") int page,
                                              @RequestParam("size") int size, @RequestParam("sortBy") String sortBy,
                                              @RequestParam("orderBy") String orderBy) {
        return this.taskService.getTasksPageByProjectId(projectId, page, size, sortBy, orderBy);
    }

    @GetMapping(params = {"page", "size", "sortBy", "orderBy", "reporterId"})
    public Page<Task> getTasksPageByProjectIdAndReporterEmail(@PathVariable("projectId") Long projectId, @RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sortBy") String sortBy, @RequestParam("orderBy") String orderBy, @RequestParam("reporterId") Long reporterId) {
        return this.taskService.getTasksPageByProjectIdAndReporterId(projectId, reporterId, page, size, sortBy, orderBy);
    }

    @GetMapping(params = {"page", "size", "sortBy", "orderBy", "assigneeId"})
    public Page<Task> getTasksPageByProjectIdAndAssigneeEmail(@PathVariable("projectId") Long projectId, @RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sortBy") String sortBy, @RequestParam("orderBy") String orderBy, @RequestParam("assigneeId") Long assigneeId) {
        return this.taskService.getTasksPageByProjectIdAndAssigneeId(projectId, assigneeId, page, size, sortBy, orderBy);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    @GetMapping(value = "/{taskId}")
    public Task findTaskById(@PathVariable(name = "taskId") Long taskId) {
        return taskService.findById(taskId).get();
    }

    @GetMapping(params = {"name"})
    public ResponseEntity findTaskByName(@RequestParam("name") String taskName) {
        Optional<Task> task = taskService.findByName(taskName);
        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{taskId}")
    public Task updateTask(@RequestBody Task task) {
        System.out.println(task);
        return taskService.update(task);
    }
}
