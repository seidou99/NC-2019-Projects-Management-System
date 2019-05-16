package com.netcracker.edu.fapi.controller;

import com.netcracker.edu.fapi.property.BackendApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BackendApiProperties backendApiProperties;

    @GetMapping
    public ResponseEntity findAllProjects() {
        return ResponseEntity.ok(restTemplate.getForObject(backendApiProperties.getProjectsUri(), String.class));
    }

    @GetMapping(params = {"assigneeId"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity findAllUserAssignedProjects(@RequestParam("assigneeId") Long assigneeId) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getProjectsUri())
                .queryParam("assigneeId", assigneeId);
        String projects = restTemplate.getForObject(uri.toUriString(), String.class);
        return ResponseEntity.ok(projects);
    }

    @GetMapping(params = {"reporterId"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity findAllUserReportedProjects(@RequestParam("reporterId") Long reporterId) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getProjectsUri())
                .queryParam("reporterId", reporterId);
        String projects = restTemplate.getForObject(uri.toUriString(), String.class);
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity createProject(@RequestBody Object project) {
        try {
            restTemplate.postForObject(backendApiProperties.getProjectsUri(), project, String.class);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

    }
}
