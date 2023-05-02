package com.example.trainbrain.controllers;

import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final JpaUserDetailsService jpaUserDetailsService;

    public RegistrationController(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        jpaUserDetailsService.addUser(user);
        return "redirect:/login";
    }
}
