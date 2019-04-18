package com.netcracker.edu.name.controllers;

import com.netcracker.edu.name.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Config config;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity getTasksPage(@PathVariable("projectId") Long projectId, @RequestParam("page") int page, @RequestParam("size") int size) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(config.getProjectsUri() + "/" + projectId + "/tasks")
                .queryParam("page", page)
                .queryParam("size", size);
        return ResponseEntity.ok(restTemplate.getForObject(uriBuilder.toUriString(), String.class));
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody Object task, @PathVariable("projectId") Long projectId) {
        restTemplate.postForObject(config.getProjectsUri() + "/" + projectId + "/tasks", task, String.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity findTaskById(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(restTemplate
                .getForObject(config.getProjectsUri() + "/" + projectId + "/tasks/" + taskId, String.class));
    }

    @PutMapping(value = "/{taskId}")
    public ResponseEntity updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @RequestBody Object task) {
        restTemplate.put(config.getProjectsUri() + "/" + projectId + "/tasks/" + taskId, task);
        return new ResponseEntity(HttpStatus.OK);
    }
}
