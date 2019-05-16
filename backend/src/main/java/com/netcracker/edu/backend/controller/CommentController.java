package com.netcracker.edu.backend.controller;

import com.netcracker.edu.backend.entity.Comment;
import com.netcracker.edu.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Iterable<Comment> findAll(@PathVariable(name = "taskId") Long taskId) {
        return commentService.findAllByTaskId(taskId);
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment, @PathVariable("taskId") Long taskId) {
        return commentService.save(comment, taskId);
    }
}
