package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
