package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/games")
public class GameController {

    @GetMapping
    public String games() {
        return "games";
    }

    @GetMapping("/shulte")
    public String shulte(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isTeacher", user.getRoles().contains(Role.TEACHER));
        return "shulteTable";
    }

    @PostMapping("/shulte")
    public String getMark(@RequestParam("mark") Integer mark) {
        System.out.println(mark);
        return "shulteTable";
    }
}
