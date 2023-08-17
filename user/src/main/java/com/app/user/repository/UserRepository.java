package com.app.user.repository;

import com.app.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email=?1")
    Optional<User> findUserByEmailOptional(String email);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id=:id")
    void deleteById(@Param("id") Integer id);
}
