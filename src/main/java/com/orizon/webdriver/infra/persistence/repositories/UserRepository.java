package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.user.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
}
