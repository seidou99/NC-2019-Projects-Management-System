package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.Comment;
import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.entity.User;
import com.netcracker.edu.name2.backend.repository.CommentRepository;
import com.netcracker.edu.name2.backend.service.CommentService;
import com.netcracker.edu.name2.backend.service.TaskService;
import com.netcracker.edu.name2.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
            return null;
        }
        comment.setTask(task.get());
        Optional<User> author = userService.findById(comment.getAuthor().getId());
        if (!author.isPresent()) {
            return null;
        }
        comment.setAuthor(author.get());
        return this.commentRepository.save(comment);
    }
}
