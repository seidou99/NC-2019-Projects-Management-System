package com.netcracker.edu.name2.backend.service;

import com.netcracker.edu.name2.backend.entity.Comment;

import java.util.Optional;

public interface CommentService {

    Iterable<Comment> findAll();

    Optional<Comment> save(Comment comment);
}
