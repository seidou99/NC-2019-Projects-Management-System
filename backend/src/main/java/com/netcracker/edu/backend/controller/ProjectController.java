package com.netcracker.edu.backend.controller;

import com.netcracker.edu.backend.entity.Project;
import com.netcracker.edu.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @GetMapping(value = "/{projectId}")
    public Project findProjectById(@PathVariable(name = "projectId") Long projectId) {
        Optional<Project> project = projectService.findById(projectId);
        if (!project.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        return project.get();
    }

    @GetMapping
    public Iterable<Project> findAllProjects() {
        return projectService.findAll();
    }

    @GetMapping(params = {"reporterId"})
    public Iterable<Project> findAllUserReportedProjects(@RequestParam("reporterId") Long reporterId) {
        return projectService.findAllUserReported(reporterId);
    }

    @GetMapping(params = {"assigneeId"})
    public Iterable<Project> findAllUserAssignedProjects(@RequestParam("assigneeId") Long assigneeId) {
        return projectService.findAllUserAssigned(assigneeId);
    }
}
