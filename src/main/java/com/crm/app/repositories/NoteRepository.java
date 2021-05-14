package com.crm.app.repositories;

import com.crm.app.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("noteRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
