package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.Project;
import com.netcracker.edu.name2.backend.repository.ProjectRepository;
import com.netcracker.edu.name2.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository repo;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repo) {
        this.repo = repo;
    }

    @Override
    public Iterable<Project> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Project save(Project project) {
        project.setId(null);
        return repo.save(project);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
