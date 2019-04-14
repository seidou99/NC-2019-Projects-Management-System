package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.Project;
import com.netcracker.edu.name2.backend.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT COUNT(*) FROM task t JOIN project p ON t.project_id=p.id where p.id=?1", nativeQuery = true)
    Long countTasksWithProjectId(Long projectId);

    Page<Task> findAllByProject(Project project, Pageable pageable);
}
