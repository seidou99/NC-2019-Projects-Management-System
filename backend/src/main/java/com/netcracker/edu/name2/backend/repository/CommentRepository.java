package com.netcracker.edu.name2.backend.repository;

import com.netcracker.edu.name2.backend.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Iterable<Comment> findAllByTaskId(Long taskId);
}
