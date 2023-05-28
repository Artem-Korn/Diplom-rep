package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import com.example.trainbrain.service.MarkService;
import com.example.trainbrain.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/games")
public class GameController {

    private final TaskService taskService;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final MarkService markService;

    public GameController(TaskService taskService, JpaUserDetailsService jpaUserDetailsService, MarkService markService) {
        this.taskService = taskService;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.markService = markService;
    }

    @GetMapping
    public String games(Model model, @AuthenticationPrincipal User user) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("isTeacher", full_user.getRoles().contains(Role.TEACHER));
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

    @GetMapping("/compare")
    public String compare(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isTeacher", user.getRoles().contains(Role.TEACHER));
        return "compareGame";
    }

    @PostMapping("/mark/{task}")
    public String getMark(
            @PathVariable Task task,
            @AuthenticationPrincipal User user,
            @RequestParam Integer mark,
            @RequestParam String game
    ) {
        markService.createMark(mark, game, user, task);
        taskService.removeUserTask(task, user);
        return "redirect:/games";
    }

    @PostMapping("/createTask")
    public String createTask(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam Integer difficulty,
            @RequestParam Map<String, String> form,
            Model model
    ) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("newTask", taskService.addTask(name, difficulty, form, full_user));
        model.addAttribute("classes", full_user.getMyStudclasses());
        return "sendTask";
    }

    @GetMapping("/edit/{task}")
    public String userEditTask(
            @AuthenticationPrincipal User user,
            @PathVariable Task task,
            Model model
    ) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("newTask", task);
        model.addAttribute("classes", full_user.getMyStudclasses());
        return "sendTask";
    }

    @PostMapping("/sendTask/{task}")
    public String sendTask(
            @PathVariable Task task,
            @RequestParam(required = false) Set<User> students
    ) {
        taskService.sendTask(students, task);
        return "redirect:/games";
    }

    @PostMapping("/removeTask/{task}")
    public String removeTask(
            @PathVariable Task task
    ) {
        taskService.removeTask(task);
        return "redirect:/games";
    }

    @GetMapping("/play/{task}")
    public String userPlayTask(@PathVariable Task task, Model model) {
        model.addAttribute("task_id", task.getId());
        model.addAttribute("task_option", taskService.getOptions(task));
        return taskService.convertGameNameToTemplate(task.getGameName());
    }
}
