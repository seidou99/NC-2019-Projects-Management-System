package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.Project;
import com.netcracker.edu.backend.repository.ProjectRepository;
import com.netcracker.edu.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repo) {
        this.projectRepository = repo;
    }

    @Override
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) {
        project.setId(null);
        if (projectRepository.findByCode(project.getCode()).isPresent()) {
            throw new RuntimeException("Project with code " + project.getCode() + " already exist");
        }
        return projectRepository.save(project);
    }

    @Override
    public Iterable<Project> findAllUserReported(Long reporterId) {
        return projectRepository.findAllUserReported(reporterId);
    }

    @Override
    public Iterable<Project> findAllUserAssigned(Long assigneeId) {
        return projectRepository.findAllUserAssigned(assigneeId);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
