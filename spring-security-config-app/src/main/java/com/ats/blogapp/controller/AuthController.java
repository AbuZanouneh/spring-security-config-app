package com.ats.blogapp.controller;

import com.ats.blogapp.access.entity.Role;
import com.ats.blogapp.access.entity.User;
import com.ats.blogapp.service.interfaces.RoleService;
import com.ats.blogapp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

// Controller Layer
@Controller
public class AuthController {

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService,
                          RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Validated User user,
                               BindingResult result,
                               Model model){
        // Check for validation errors
        if(result.hasErrors()){
            return "register";
        }

        // Check if username already exists
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if(existingUser.isPresent()){
            model.addAttribute("usernameError", "Username is already taken.");
            return "register";
        }

        // Check if email already exists
        existingUser = userService.findByEmail(user.getEmail());
        if(existingUser.isPresent()){
            model.addAttribute("emailError", "Email is already registered.");
            return "register";
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign ROLE_USER by default
        Role userRole = roleService.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found."));
        user.setRole(userRole);

        // Save the user
        userService.saveUser(user);

        // Redirect to login page with success message
        model.addAttribute("registerSuccess", "Registration successful! Please login.");
        return "login";
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    // Show access denied page
    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "access-denied";
    }
}
