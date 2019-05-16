package com.netcracker.edu.backend.service;

import com.netcracker.edu.backend.entity.Comment;

public interface CommentService {

    Iterable<Comment> findAllByTaskId(Long taskId);

    Comment save(Comment comment, Long taskId);
}
