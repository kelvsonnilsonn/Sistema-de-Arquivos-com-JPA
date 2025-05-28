package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Set<Comment> findByFileId(Long fileId);
    Set<Comment> findByAuthorId(Long userId);
}
