package com.netcracker.edu.name.controllers;

import com.netcracker.edu.name.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    @GetMapping
    public ResponseEntity findAllProjects() {
        return ResponseEntity.ok(restTemplate.getForObject(config.getProjectsUri(), String.class));
    }

    @PostMapping
    public ResponseEntity createProject(@RequestBody Object project) {
        restTemplate.postForObject(config.getProjectsUri(), project, String.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
