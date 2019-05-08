package com.netcracker.edu.name.controller;

import com.netcracker.edu.name.property.BackendApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

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

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity uploadFiles(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                                     @RequestParam("files") MultipartFile[] files) {
        String attachmentsUri = backendApiProperties.getProjectsUri() + "/" + projectId + "/tasks/" + taskId + "/attachments";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            for (MultipartFile file : files) {
                MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
                ContentDisposition contentDisposition = ContentDisposition.builder("form-data")
                        .name("files")
                        .filename(file.getName())
                        .build();
                fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
                HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
                body.add("files", fileEntity);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error while reading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    attachmentsUri,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("something goes wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
