package com.netcracker.edu.name2.backend.controller;

import com.netcracker.edu.name2.backend.entity.Project;
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

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @GetMapping(value = "/{projectId}")
    public Project findProjectById(@PathVariable(name = "projectId") Long projectId) {
        return projectService.findById(projectId).get();
    }

    @GetMapping
    public Iterable<Project> findAllProjects() {
        return projectService.findAll();
    }

    @GetMapping(params = {"reporterId", "sortBy", "orderBy"})
    public Iterable<Project> findAllUserReportedProjects(@RequestParam("reporterId") Long reporterId) {
        return projectService.findAllUserReported(reporterId);
    }

    @GetMapping(params = {"assigneeId"})
    public Iterable<Project> findAllUserAssignedProjects(@RequestParam("assigneeId") Long assigneeId) {
        return projectService.findAllUserAssigned(assigneeId);
    }
}
