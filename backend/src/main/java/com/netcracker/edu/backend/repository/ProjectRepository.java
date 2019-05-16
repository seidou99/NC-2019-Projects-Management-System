package com.netcracker.edu.backend.repository;

import com.netcracker.edu.backend.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Query(value = "select * from project p where p.id in (select project_id from task t where t.reporter_id=?1)", nativeQuery = true)
    Iterable<Project> findAllUserReported(Long reporterId);

    @Query(value = "select * from project p where p.id in (select project_id from task t where t.assignee_id=?1)", nativeQuery = true)
    Iterable<Project> findAllUserAssigned(Long assigneeId);

    Optional<Project> findByCode(String code);
}
