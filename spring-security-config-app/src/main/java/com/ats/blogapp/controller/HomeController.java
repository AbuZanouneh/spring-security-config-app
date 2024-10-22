package com.ats.blogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller Layer
@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(){
        return "home";
    }
}
