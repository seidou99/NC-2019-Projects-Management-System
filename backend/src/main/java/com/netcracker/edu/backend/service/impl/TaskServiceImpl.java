package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.*;
import com.netcracker.edu.backend.repository.TaskRepository;
import com.netcracker.edu.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.ws.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskPriorityService taskPriorityService;
    private TaskStatusService taskStatusService;
    private UserService userService;
    private ProjectService projectService;
    private static final String STATUS_OPEN = "Open", STATUS_RESOLVED = "Resolved", STATUS_CLOSED = "Closed";


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
        if (!priority.isPresent()) {
            throw new RuntimeException("Task priority with name '" + task.getPriority().getName() + "' not found");
        }
        Date now = new Date();
        task.setPriority(priority.get());
        Optional<TaskStatus> openStatus = taskStatusService.findByName(STATUS_OPEN);
        task.setStatus(openStatus.get());
        task.setCreated(now);
        task.setClosed(null);
        task.setUpdated(null);
        task.setResolved(null);
        task.setCode(taskRepository.countTasksWithProjectId(task.getProject().getId()) + 1);
        Optional<User> reporter = userService.findById(task.getReporter().getId());
        Optional<User> assignee = userService.findById(task.getAssignee().getId());
        if (!reporter.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Reporter not found");
        }
        if (!assignee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Assignee not found");
        }
        task.setReporter(reporter.get());
        task.setAssignee(assignee.get());
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        Optional<Task> optionalTask = taskRepository.findById(task.getId());
        if (!optionalTask.isPresent()) {
            throw new RuntimeException("Task with id '" + task.getId() + "' not found");
        }
        Date now = new Date();
        Task oldTask = optionalTask.get();
        Optional<TaskPriority> priority = taskPriorityService.findByName(task.getPriority().getName());
        Optional<TaskStatus> status = taskStatusService.findByName(task.getStatus().getName());
        Optional<User> assignee = userService.findById(task.getAssignee().getId());
        if (!priority.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Task priority with name '" + task.getPriority().getName() + "' not found");
        }
        if (!status.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Task status with name '" + task.getStatus().getName() + "'not found");
        }
        if (!assignee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Task assignee not found");
        }
        task.setPriority(priority.get());
        task.setStatus(status.get());
        task.setAssignee(assignee.get());
        task.setReporter(oldTask.getReporter());
        task.setCreated(oldTask.getCreated());
        task.setClosed(oldTask.getClosed());
        task.setResolved(oldTask.getResolved());
        task.setUpdated(now);
        if (task.getStatus().getName().equals(STATUS_RESOLVED)) {
            task.setResolved(now);
        } else if (task.getStatus().getName().equals(STATUS_CLOSED)) {
            task.setClosed(now);
        }
        return taskRepository.save(task);
    }

    private Sort getSort(String sort, String order) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        return Sort.by(direction, sort);
    }

    @Override
    public Page<Task> getTasksPageByProjectId(Long projectId, int pageNumber, int pageSize, String sortBy, String orderBy) {
        Optional<Project> project = projectService.findById(projectId);
        if (!project.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Project not found");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(sortBy, orderBy));
        return taskRepository.findAllByProject(project.get(), pageable);
    }

    @Override
    public Page<Task> getTasksPageByProjectIdAndReporterId(Long projectId, Long reporterId, int pageNumber, int pageSize,
                                                           String sortBy, String orderBy) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<User> reporter = userService.findById(reporterId);
        if (!project.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Project not found");
        }
        if (!reporter.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Reporter not found");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(sortBy, orderBy));
        return taskRepository.findAllByProjectAndReporter(project.get(), reporter.get(), pageable);
    }

    @Override
    public Page<Task> getTasksPageByProjectIdAndAssigneeId(Long projectId, Long assigneeId, int pageNumber, int pageSize,
                                                           String sortBy, String orderBy) {
        Optional<Project> project = projectService.findById(projectId);
        Optional<User> assignee = userService.findById(assigneeId);
        if (!project.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Project not found");
        }
        if (!assignee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Assignee not found");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(sortBy, orderBy));
        return taskRepository.findAllByProjectAndAssignee(project.get(), assignee.get(), pageable);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
