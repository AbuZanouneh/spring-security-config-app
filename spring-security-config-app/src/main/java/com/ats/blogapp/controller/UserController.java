package com.ats.blogapp.controller;

import com.ats.blogapp.access.entity.User;
import com.ats.blogapp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

// Controller Layer
@PreAuthorize("hasRole('USER')") // Ensures all methods require 'USER' role.
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model){
        String username = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(username);

        if(userOpt.isPresent()){
            User user = userOpt.get();
            model.addAttribute("user", user);
            return "user/profile";
        } else {
            // Handle the case where the user is not found
            return "redirect:/login?error";
        }
    }
}
