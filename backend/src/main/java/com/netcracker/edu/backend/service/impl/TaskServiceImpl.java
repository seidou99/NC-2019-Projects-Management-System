package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.*;
import com.netcracker.edu.backend.repository.TaskRepository;
import com.netcracker.edu.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> findByName(String name) {
        return taskRepository.findByName(name);
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
        task.setReporter(userService.findById(task.getReporter().getId()).get());
        task.setAssignee(userService.findById(task.getAssignee().getId()).get());
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
        if (task.getStatus().getName().equals("Resolved")) {
            task.setResolved(new Date());
        } else if (task.getStatus().getName().equals("Closed")) {
            task.setClosed(new Date());
        }
        return taskRepository.save(task);
    }

    private Sort getSort(String sort, String order) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        if (sort.equals("task")) {
            sort = "code";
        }
        return Sort.by(direction, sort);
    }

    @Override
    public Page<Task> getTasksPageByProjectId(Long projectId, int pageNumber, int pageSize, String sortBy, String orderBy) {
        Optional<Project> project = projectService.findById(projectId);
        if (project.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(sortBy, orderBy));
            return taskRepository.findAllByProject(project.get(), pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Task> getTasksPageByProjectIdAndReporterId(Long projectId, Long reporterId, int pageNumber, int pageSize, String sortBy, String orderBy) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<User> reporter = userService.findById(reporterId);
        if (project.isPresent() && reporter.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(sortBy, orderBy));
            return taskRepository.findAllByProjectAndReporter(project.get(), reporter.get(), pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Task> getTasksPageByProjectIdAndAssigneeId(Long projectId, Long assigneeId, int pageNumber, int pageSize, String sortBy, String orderBy) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<User> assignee = userService.findById(assigneeId);
        if (project.isPresent() && assignee.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(sortBy, orderBy));
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
