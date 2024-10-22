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
@PreAuthorize("hasRole('ADMIN')") // Ensures all methods require 'ADMIN' role.
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model){
        String username = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(username);

        if(userOpt.isPresent()){
            User user = userOpt.get();
            model.addAttribute("user", user);
            return "admin/profile";
        } else {
            // Handle the case where the user is not found
            return "redirect:/login?error";
        }
    }

    // Add more admin endpoints as needed, e.g., user management
}
