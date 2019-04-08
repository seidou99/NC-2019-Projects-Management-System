package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.Comment;

import java.awt.print.Pageable;
import java.util.Optional;

public interface CommentService {

    Iterable<Comment> findAllByTaskId(Long taskId);

    Comment save(Comment comment, Long taskId);
}
