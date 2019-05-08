package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AttachmentService {

    Attachment save(MultipartFile file, Long taskId);

    Optional<Attachment> findById(Long id);
}
