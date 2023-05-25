package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.StudClass;
import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import com.example.trainbrain.service.StudClassService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classes")
public class StudClassController {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final StudClassService studClassService;

    public StudClassController(JpaUserDetailsService jpaUserDetailsService, StudClassService studClassService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.studClassService = studClassService;
    }

    @GetMapping
    public String classes(Model model, @AuthenticationPrincipal User user) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("isTeacher", full_user.getRoles().contains(Role.TEACHER));
        model.addAttribute("myClasses", full_user.getMyStudclasses());
        model.addAttribute("classes", full_user.getStudclasses());
        return "classes";
    }

    @GetMapping("/createClass")
    public String createClass(Model model) {
        model.addAttribute("newClass", new StudClass());
        return "createClass";
    }

    @PostMapping("/createClass")
    public String addClass(StudClass studclass, @AuthenticationPrincipal User user) {
        studClassService.addClass(studclass, user);
        return "redirect:/classes";
    }

    @PostMapping("{studclass}/removeClass")
    public String removeClass(@PathVariable StudClass studclass) {
        studClassService.removeClass(studclass);
        return "redirect:/classes";
    }

    @GetMapping("{studclass}/inviteUser")
    public String findUser(@PathVariable StudClass studclass, Model model) {
        model.addAttribute("class",studclass);
        return "findUser";
    }

    @PostMapping("{studclass}/inviteUser")
    public String inviteUser(@PathVariable StudClass studclass, @RequestParam String username) {
        studClassService.addUserToClass(studclass, (User) jpaUserDetailsService.loadUserByUsername(username));
        return "redirect:/classes";
    }

    @PostMapping("{studclass}/removeUser/{user}")
    public String removeUser(
            @PathVariable StudClass studclass,
            @PathVariable User user
    ) {
        studClassService.removeUserFromClass(studclass, user);
        return "redirect:/classes";
    }

    @PostMapping("{studclass}/leaveClass")
    public String leaveClass(
            @PathVariable StudClass studclass,
            @AuthenticationPrincipal User user
    ) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());

        studClassService.removeUserFromClass(studclass, full_user);
        return "redirect:/classes";
    }
}
