package com.ats.blogapp.access.repository;

import com.ats.blogapp.access.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Data Access Layer
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username.
    Optional<User> findByUsername(String username);

    // Find user by email.
    Optional<User> findByEmail(String email);
}
