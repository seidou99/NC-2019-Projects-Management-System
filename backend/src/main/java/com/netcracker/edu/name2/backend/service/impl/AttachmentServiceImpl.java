package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.Attachment;
import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.repository.AttachmentRepository;
import com.netcracker.edu.name2.backend.service.AttachmentService;
import com.netcracker.edu.name2.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid sequence " + fileName);
            }
            Optional<Task> task = taskService.findById(taskId);
            if (!task.isPresent()) {
                throw new RuntimeException("Could not find task to attach file");
            }
            Attachment attachment = new Attachment(task.get(), fileName, file.getContentType(), file.getBytes());
            return attachmentRepository.save(attachment);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Optional<Attachment> findById(Long id) {
        return attachmentRepository.findById(id);
    }
}
