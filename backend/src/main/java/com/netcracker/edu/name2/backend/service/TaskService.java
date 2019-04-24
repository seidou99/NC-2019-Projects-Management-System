package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Iterable<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    void delete(Long id);

    Task update(Task task);

    Page<Task> getTasksPageByProjectId(Long id, int pageNumber, int pageSize);

    Page<Task> getTasksPageByProjectIdAndReporterEmail(Long projectId, String reporterEmail, int pageNumber, int pageSize);

    Page<Task> getTasksPageByProjectIdAndAssigneeEmail(Long projectId, String assigneeEmail, int pageNumber, int pageSize);
}
