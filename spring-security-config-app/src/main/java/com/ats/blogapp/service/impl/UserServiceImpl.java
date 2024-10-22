package com.ats.blogapp.service.impl;

import com.ats.blogapp.access.entity.User;
import com.ats.blogapp.access.repository.UserRepository;
import com.ats.blogapp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service Layer
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
