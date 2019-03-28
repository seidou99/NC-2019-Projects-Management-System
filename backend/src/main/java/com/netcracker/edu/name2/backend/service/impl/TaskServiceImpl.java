package com.netcracker.edu.name2.backend.service.impl;

import com.netcracker.edu.name2.backend.entity.Task;
import com.netcracker.edu.name2.backend.repository.TaskRepository;
import com.netcracker.edu.name2.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskServiceImpl implements TaskService {

    private TaskRepository repo;

    @Autowired
    public TaskServiceImpl(TaskRepository repo) {
        this.repo = repo;
    }

    public Iterable<Task> findAll() {
        return repo.findAll();
    }

    public Optional<Task> findById(Long id) {
        return repo.findById(id);
    }

    public Task save(Task task) {
        task.setId(null);
        return repo.save(task);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
