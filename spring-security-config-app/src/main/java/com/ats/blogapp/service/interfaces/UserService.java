package com.ats.blogapp.service.interfaces;

import com.ats.blogapp.access.entity.User;

import java.util.List;
import java.util.Optional;

// Service Interface
public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findUserById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);

    List<User> findAllUsers();
}
