package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import com.example.trainbrain.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/games")
public class GameController {

    private final TaskService taskService;
    private final JpaUserDetailsService jpaUserDetailsService;

    public GameController(TaskService taskService, JpaUserDetailsService jpaUserDetailsService) {
        this.taskService = taskService;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @GetMapping
    public String games(Model model, @AuthenticationPrincipal User user) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("isTeacher", user.getRoles().contains(Role.TEACHER));
        model.addAttribute("tasks", full_user.getTasks());
        model.addAttribute("myTasks", full_user.getMyTasks());
        return "games";
    }

    @GetMapping("/shulte")
    public String shulte(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isTeacher", user.getRoles().contains(Role.TEACHER));
        return "shulteTable";
    }

    @GetMapping("/mole")
    public String mole(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isTeacher", user.getRoles().contains(Role.TEACHER));
        return "moleGame";
    }

    @PostMapping("/mark")
    public String getMark(@RequestParam("mark") Integer mark) {
        System.out.println(mark);
        return "redirect:/games";
    }

    @PostMapping("/createTask")
    public String createTask(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam Map<String, String> form,
            Model model
    ) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("newTask", taskService.addTask(name, form, full_user));
        model.addAttribute("students", jpaUserDetailsService.findAllStudents());
        return "sendTask";
    }

    @PostMapping("/sendTask")
    public String sendTask(
            @RequestParam Task newTask,
            @RequestParam List<User> students
    ) {
        jpaUserDetailsService.sendTask(students, newTask);
        return "redirect:/games";
    }
}
