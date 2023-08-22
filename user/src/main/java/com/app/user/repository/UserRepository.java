package com.app.user.repository;

import com.app.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id=:id")
    void deleteById(@Param("id") Integer id);
}
