package com.netcracker.edu.fapi.controller;

import com.netcracker.edu.fapi.property.BackendApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BackendApiProperties backendApiProperties;

    @GetMapping(params = {"page", "size", "sortBy", "orderBy"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity getTasksPageByProjectId(@PathVariable("projectId") Long projectId, @RequestParam("page") int page,
                                                  @RequestParam("size") int size, @RequestParam("sortBy") String sortBy,
                                                  @RequestParam("orderBy") String orderBy) {
        return getTasksPage(projectId, page, size, sortBy, orderBy, null, null);
    }

    @GetMapping(params = {"page", "size", "sortBy", "orderBy", "assigneeId"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity getTasksPageByProjectIdAndAssigneeId(@PathVariable("projectId") Long projectId,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("sortBy") String sortBy,
                                                               @RequestParam("orderBy") String orderBy,
                                                               @RequestParam("assigneeId") Long assigneeId) {
        return getTasksPage(projectId, page, size, sortBy, orderBy, "assigneeId", assigneeId);
    }

    @GetMapping(params = {"page", "size", "sortBy", "orderBy", "reporterId"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity getTasksPageByProjectIdAndReporterId(@PathVariable("projectId") Long projectId,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("sortBy") String sortBy,
                                                               @RequestParam("orderBy") String orderBy,
                                                               @RequestParam("reporterId") Long reporterId) {
        return getTasksPage(projectId, page, size, sortBy, orderBy, "reporterId", reporterId);
    }

    private ResponseEntity getTasksPage(Long projectId, int page, int size, String sortBy, String orderBy, String userIdParamName,
                                        Long userId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks")
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sortBy", sortBy)
                .queryParam("orderBy", orderBy);
        if (userId != null) {
            uriBuilder.queryParam(userIdParamName, userId);
        }
        String res;
        res = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody Object task, @PathVariable("projectId") Long projectId) {
        restTemplate.postForObject(backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks", task, String.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{taskId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity findTaskById(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        String task = restTemplate.getForObject(backendApiProperties.getProjectsUri() + "/" + projectId +
                "/tasks/" + taskId, String.class);
        return ResponseEntity.ok(task);
    }

    @GetMapping(params = {"name"}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> findTaskByName(@RequestParam("name") String taskName) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(backendApiProperties.getProjectsUri() + "/all/tasks")
                .queryParam("name", taskName);
        String data = restTemplate.getForObject(uri.toUriString(), String.class);
        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                     @RequestBody Object task) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> acceptTypes = new LinkedList<>();
        acceptTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptTypes);
        HttpEntity<Object> entity = new HttpEntity<>(task, headers);
        restTemplate.put(backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks/" + taskId, entity);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
