package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.Task;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TaskService {

    Iterable<Task> findAll();

    Optional<Task> findById(Long id);

    Optional<Task> findByName(String name);

    Task save(Task task);

    void delete(Long id);

    Task update(Task task);

    Page<Task> getTasksPageByProjectId(Long projectId, int pageNumber, int pageSize, String sortBy, String orderBy);

    Page<Task> getTasksPageByProjectIdAndReporterId(Long projectId, Long reporterId, int pageNumber, int pageSize, String sortBy, String orderBy);

    Page<Task> getTasksPageByProjectIdAndAssigneeId(Long projectId, Long assigneeId, int pageNumber, int pageSize, String sortBy, String orderBy);
}
