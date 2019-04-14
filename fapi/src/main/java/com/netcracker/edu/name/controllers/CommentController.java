package com.netcracker.edu.name.controllers;

import com.netcracker.edu.name.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    @Autowired
    private Config config;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity findAll(@PathVariable(name = "taskId") Long taskId) {
        String data = restTemplate.getForObject(config.getTasksUri() + "/" + taskId + "/comments", String.class);
        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity createComment(@RequestBody Object comment, @PathVariable("taskId") Long taskId) {
        restTemplate.postForObject(config.getTasksUri() + "/" + taskId + "/comments", comment, String.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
