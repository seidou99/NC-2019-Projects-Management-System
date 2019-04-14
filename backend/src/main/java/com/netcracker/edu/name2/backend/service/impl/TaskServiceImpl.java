package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.*;
import com.netcracker.edu.name2.backend.repository.*;
import com.netcracker.edu.name2.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskPriorityService taskPriorityService;
    private TaskStatusService taskStatusService;
    private UserService userService;
    private ProjectService projectService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService,
                           TaskPriorityService taskPriorityService, TaskStatusService taskStatusService,
                           ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.taskPriorityService = taskPriorityService;
        this.taskStatusService = taskStatusService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        Optional<Task> optTask = taskRepository.findById(id);
        return optTask;
    }

    @Override
    public Task save(Task task) {
        task.setId(null);
        Optional<TaskPriority> priority = taskPriorityService.findByName(task.getPriority().getName());
        task.setPriority(priority.get());
        Optional<TaskStatus> openStatus = taskStatusService.findByName("Open");
        task.setStatus(openStatus.get());
        task.setCreated(new Date());
        task.setCode(taskRepository.countTasksWithProjectId(task.getProject().getId()) + 1);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task update(Task task) {
        task.setPriority(taskPriorityService.findByName(task.getPriority().getName()).get());
        task.setStatus(taskStatusService.findByName(task.getStatus().getName()).get());
        task.setAssignee(userService.findById(task.getAssignee().getId()).get());
        task.setReporter(userService.findById(task.getReporter().getId()).get());
        task.setUpdated(new Date());
        if (task.getStatus().getName() == "Resolved") {
            task.setResolved(new Date());
        } else if (task.getStatus().getName() == "Closed") {
            task.setClosed(new Date());
        }
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> findAllByProjectId(Long id, int pageNumber, int pageSize) {
        Project project = projectService.findById(id).get();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return taskRepository.findAllByProject(project, pageable);
    }

    @Override
    public Long countTasksWithProjectId(Long projectId) {
        return taskRepository.countTasksWithProjectId(projectId);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
