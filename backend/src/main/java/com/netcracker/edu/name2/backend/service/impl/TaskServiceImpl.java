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
//        task.setReporter(userService.findById(task.getReporter().getId()).get());
        task.setReporter(userService.findByEmail(task.getReporter().getAuthData().getEmail()).get());
        if (task.getAssignee() == null) {
            task.setAssignee(task.getReporter());
        }
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
    public Page<Task> getTasksPageByProjectId(Long id, int pageNumber, int pageSize) {
        Optional<Project> project = projectService.findById(id);
        if (project.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return taskRepository.findAllByProject(project.get(), pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Task> getTasksPageByProjectIdAndReporterEmail(Long projectId, String reporterEmail, int pageNumber, int pageSize) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<User> reporter = userService.findByEmail(reporterEmail);
        if (project.isPresent() && reporter.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return taskRepository.findAllByProjectAndReporter(project.get(), reporter.get(), pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Task> getTasksPageByProjectIdAndAssigneeEmail(Long projectId, String assigneeEmail, int pageNumber, int pageSize) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<User> assignee = userService.findByEmail(assigneeEmail);
        if (project.isPresent() && assignee.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return taskRepository.findAllByProjectAndAssignee(project.get(), assignee.get(), pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
