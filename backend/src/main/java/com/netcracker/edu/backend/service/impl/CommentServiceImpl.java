package com.netcracker.edu.backend.service.impl;

import com.netcracker.edu.backend.entity.Comment;
import com.netcracker.edu.backend.entity.Task;
import com.netcracker.edu.backend.entity.User;
import com.netcracker.edu.backend.repository.CommentRepository;
import com.netcracker.edu.backend.service.CommentService;
import com.netcracker.edu.backend.service.TaskService;
import com.netcracker.edu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TaskService taskService;
    private UserService userService;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, TaskService taskService, UserService userService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public Iterable<Comment> findAllByTaskId(Long taskId) {
        return this.commentRepository.findAllByTaskId(taskId);
    }

    @Override
    public Comment save(Comment comment, Long taskId) {
        Optional<Task> task = taskService.findById(taskId);
        if (!task.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Task not found");
        }
        comment.setTask(task.get());
        Optional<User> author = userService.findById(comment.getAuthor().getId());
        if (!author.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Author not found");
        }
        comment.setAuthor(author.get());
        comment.setDate(new Date());
        return this.commentRepository.save(comment);
    }
}
