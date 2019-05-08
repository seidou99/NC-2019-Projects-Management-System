package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
