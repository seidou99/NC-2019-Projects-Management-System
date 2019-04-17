package com.netcracker.edu.name.controllers;

import com.netcracker.edu.name.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping(params = {"page", "size", "projectId"})
    public ResponseEntity getAllTasksByProjectId(@RequestParam("page") int page, @RequestParam("size") int size,
                                                 @RequestParam("projectId") Long projectId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(config.getTasksUri())
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("projectId", projectId);
        return ResponseEntity.ok(restTemplate.getForObject(uriBuilder.toUriString(), String.class));
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody Object task) {
        restTemplate.postForObject(config.getTasksUri(), task, String.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity findTaskById(@PathVariable(name = "taskId") Long taskId) {
        return ResponseEntity.ok(restTemplate.getForObject(config.getTasksUri() + "/" + taskId, String.class));
    }

    @PutMapping(value = "/{taskId}")
    public ResponseEntity updateTask(@PathVariable(name = "taskId") Long taskId, @RequestBody Object task) {
        restTemplate.put(config.getTasksUri() + "/" + taskId, task);
        return new ResponseEntity(HttpStatus.OK);
    }
}
