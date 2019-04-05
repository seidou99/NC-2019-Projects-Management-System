package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.repository.CommentRepository;
import com.netcracker.edu.name2.backend.repository.TaskRepository;
import com.netcracker.edu.name2.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private CommentRepository commentRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        task.setId(null);
        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
