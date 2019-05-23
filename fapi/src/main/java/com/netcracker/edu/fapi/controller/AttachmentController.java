package com.netcracker.edu.fapi.controller;

import com.netcracker.edu.fapi.property.BackendApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sun.rmi.runtime.Log;

import java.io.IOException;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/attachments")
public class AttachmentController {

    private BackendApiProperties backendApiProperties;
    private RestTemplate restTemplate;

    @Autowired
    public AttachmentController(BackendApiProperties backendApiProperties, RestTemplate restTemplate) {
        this.backendApiProperties = backendApiProperties;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/{attachmentId}", produces = "application/octet-stream")
    public ResponseEntity downloadFile(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @PathVariable("attachmentId") Long attachmentId) {
        String requestUri = this.backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks/" + taskId + "/attachments/" + attachmentId;
        ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(requestUri, ByteArrayResource.class);
        HttpHeaders headers = response.getHeaders();
        String contentDisposition = headers.getContentDisposition().toString();
        MediaType contentType = headers.getContentType();
        return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(response.getBody());
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity uploadFiles(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                      @RequestParam("files") MultipartFile[] files) {
        if (files.length == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Attachments list is empty");
        }
        String attachmentsUri = backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks/"
                + taskId + "/attachments";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            for (MultipartFile file : files) {
                MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
                ContentDisposition contentDisposition = ContentDisposition.builder("form-data")
                        .name("files")
                        .filename(file.getOriginalFilename())
                        .build();
                fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
                HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
                body.add("files", fileEntity);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while reading file");
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                attachmentsUri,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
    }
}
