package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.Comment;
import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.repository.CommentRepository;
import com.netcracker.edu.name2.backend.service.CommentService;
import com.netcracker.edu.name2.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TaskService taskService;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, TaskService taskService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
    }

    @Override
    public Iterable<Comment> findAllByTaskId(Long taskId) {
        return this.commentRepository.findAllByTaskId(taskId);
    }

    @Override
    public Comment save(Comment comment, Long taskId) {
        Optional<Task> task = this.taskService.findById(taskId);
        if (!task.isPresent()) {
            return null;
        }
        comment.setTask(task.get());
        return this.commentRepository.save(comment);
    }
}
