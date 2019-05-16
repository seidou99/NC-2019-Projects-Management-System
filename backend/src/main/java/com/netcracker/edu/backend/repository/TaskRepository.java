package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.Project;
import com.netcracker.edu.backend.entity.Task;
import com.netcracker.edu.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT COUNT(*) FROM task t JOIN project p ON t.project_id=p.id where p.id=?1", nativeQuery = true)
    Long countTasksWithProjectId(Long projectId);

    @Query(value = "select t from Task t where concat(t.project.code, '-', t.code)=?1")
    Optional<Task> findByName(String name);

    Page<Task> findAllByProject(Project project, Pageable pageable);

    Page<Task> findAllByProjectAndReporter(Project project, User reporter, Pageable pageable);

    Page<Task> findAllByProjectAndAssignee(Project project, User assignee, Pageable pageable);
}
