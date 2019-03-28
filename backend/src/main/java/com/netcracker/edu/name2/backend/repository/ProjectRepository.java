package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
