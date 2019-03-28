package com.netcracker.edu.name2.backend.controller;

import com.netcracker.edu.name2.backend.entity.Project;
import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Iterable<Project> findAllProjects() {
        return projectService.findAll();
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @GetMapping(value = "/{projectId}")
    public Project findProjectById(@PathVariable(name = "projectId") Long projectId) {
        return projectService.findById(projectId).get();
    }
}
