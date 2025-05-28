package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.user.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
    AbstractUser findByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE abstract_user SET user_type = 'ADMIN' WHERE id = :userId")
    void promoteToAdmin(@Param("userId") Long id);

}
