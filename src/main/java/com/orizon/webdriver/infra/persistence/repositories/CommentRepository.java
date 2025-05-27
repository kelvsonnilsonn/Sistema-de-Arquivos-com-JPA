package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
