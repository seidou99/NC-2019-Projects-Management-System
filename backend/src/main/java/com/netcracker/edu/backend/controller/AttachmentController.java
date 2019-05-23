package com.netcracker.edu.backend.controller;

import com.netcracker.edu.backend.entity.Attachment;
import com.netcracker.edu.backend.payload.UploadFileResponse;
import com.netcracker.edu.backend.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/attachments")
public class AttachmentController {

    private AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    private UploadFileResponse saveFile(MultipartFile file, Long taskId) {
        Attachment attachment = attachmentService.save(file, taskId);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + attachment.getId())
                .toUriString();
        return new UploadFileResponse(attachment.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping
    public ResponseEntity<List<UploadFileResponse>> uploadFiles(@PathVariable("taskId") Long taskId, @RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponse> uploads = Arrays.stream(files)
                .map((file -> saveFile(file, taskId)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(uploads, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{attachmentId}")
    public ResponseEntity downloadFile(@PathVariable("attachmentId") Long attachmentId) {
        Optional<Attachment> attachment = attachmentService.findById(attachmentId);
        if (!attachment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attachment not found");
        }
        String fileName = UriUtils.encode(attachment.get().getFileName(), "UTF-8");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(attachment.get().getData()));
    }
}
