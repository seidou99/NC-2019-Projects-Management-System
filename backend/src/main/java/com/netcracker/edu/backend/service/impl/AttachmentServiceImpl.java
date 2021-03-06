package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.Attachment;
import com.netcracker.edu.backend.entity.Task;
import com.netcracker.edu.backend.repository.AttachmentRepository;
import com.netcracker.edu.backend.service.AttachmentService;
import com.netcracker.edu.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentRepository attachmentRepository;
    private TaskService taskService;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, TaskService taskService) {
        this.attachmentRepository = attachmentRepository;
        this.taskService = taskService;
    }

    @Override
    public Attachment save(MultipartFile file, Long taskId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Optional<Task> task = taskService.findById(taskId);
            if (!task.isPresent()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Could not find task to attach file");
            }
            Attachment attachment = new Attachment(task.get(), fileName, file.getContentType(), file.getBytes());
            return attachmentRepository.save(attachment);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Optional<Attachment> findById(Long id) {
        return attachmentRepository.findById(id);
    }
}
