package com.minkov.heroes.web.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {
    @GetMapping("/profile")
    public String getProfile() {
        return "users/profile.html";
    }
}
