package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.Project;

import java.util.Optional;

public interface ProjectService {

    Iterable<Project> findAll();

    Optional<Project> findById(Long id);

    Project save(Project project);

    Iterable<Project> findAllUserReported(Long reporterId);

    Iterable<Project> findAllUserAssigned(Long assigneeId);

    void delete(Long id);
}
