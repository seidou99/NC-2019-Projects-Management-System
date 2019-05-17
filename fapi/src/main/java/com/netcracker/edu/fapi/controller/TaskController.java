package com.netcracker.edu.fapi.controller;

import com.netcracker.edu.fapi.property.BackendApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BackendApiProperties backendApiProperties;

    @GetMapping(params = {"page", "size", "sortBy", "orderBy"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity getTasksPageByProjectId(@PathVariable("projectId") Long projectId, @RequestParam("page") int page,
                                                  @RequestParam("size") int size, @RequestParam("sortBy") String sortBy, @RequestParam("orderBy") String orderBy) {
        return ResponseEntity.ok(getTasksPage(projectId, page, size, sortBy, orderBy, null, null));
    }

    @GetMapping(params = {"page", "size", "sortBy", "orderBy", "assigneeId"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity getTasksPageByProjectIdAndAssigneeId(@PathVariable("projectId") Long projectId,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size, @RequestParam("sortBy") String sortBy, @RequestParam("orderBy") String orderBy, @RequestParam("assigneeId") Long assigneeId) {
        return ResponseEntity.ok(getTasksPage(projectId, page, size, sortBy, orderBy, "assigneeId", assigneeId));
    }

    @GetMapping(params = {"page", "size", "sortBy", "orderBy", "reporterId"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity getTasksPageByProjectIdAndReporterId(@PathVariable("projectId") Long projectId,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size, @RequestParam("sortBy") String sortBy, @RequestParam("orderBy") String orderBy, @RequestParam("reporterId") Long reporterId) {
        return ResponseEntity.ok(getTasksPage(projectId, page, size, sortBy, orderBy, "reporterId", reporterId));
    }

    private String getTasksPage(Long projectId, int page, int size, String sortBy, String orderBy, String userIdParamName, Long userId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks")
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sortBy", sortBy)
                .queryParam("orderBy", orderBy);
        if (userId != null) {
            uriBuilder.queryParam(userIdParamName, userId);
        }
        return restTemplate.getForObject(uriBuilder.toUriString(), String.class);
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody Object task, @PathVariable("projectId") Long projectId) {
        restTemplate.postForObject(backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks", task, String.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{taskId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity findTaskById(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        try {
            return ResponseEntity.ok(restTemplate.getForObject(
                    backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks/" + taskId, String.class));
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    @GetMapping(params = {"name"}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> findTaskByName(@RequestParam("name") String taskName) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getProjectsUri() + "/all/tasks")
                .queryParam("name", taskName);
        try {
            String data = restTemplate.getForObject(uri.toUriString(), String.class);
            return ResponseEntity.ok(data);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    @PutMapping(value = "/{taskId}")
    public ResponseEntity updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @RequestBody Object task) {
        restTemplate.put(backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks/" + taskId, task);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
