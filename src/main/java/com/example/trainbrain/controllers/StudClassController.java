package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.StudClass;
import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import com.example.trainbrain.service.StudClassService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

        model.addAttribute("myClasses", full_user.getMyStudclasses().stream()
                .sorted((c1, c2) -> c1.getName().compareTo(c2.getName())));

        model.addAttribute("classes", full_user.getStudclasses().stream()
                .sorted((c1, c2) -> c1.getName().compareTo(c2.getName())));
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

    @PostMapping("/removeClass/{studclass}")
    public String removeClass(@PathVariable StudClass studclass) {
        studClassService.removeClass(studclass);
        return "redirect:/classes";
    }

    @GetMapping("/inviteUser/{studclass}")
    public String findUser(@PathVariable StudClass studclass, Model model) {
        model.addAttribute("class",studclass);
        return "findUser";
    }

    @PostMapping("/inviteUser/{studclass}")
    public String inviteUser(
            @PathVariable StudClass studclass,
            @RequestParam String username,
            Model model
    ) {
        Optional<User> new_student = jpaUserDetailsService.getUserByUsername(username);
        if(new_student.isPresent()) {
            if(new_student.get().getStudclasses().contains(studclass)) {
                model.addAttribute("class",studclass);
                model.addAttribute(
                        "userError",
                        "Користувач «" + username +
                                "» вже є учасником класу «" + studclass.getName() + "»"
                );
                return "findUser";
            }
            else {
                studClassService.addUserToClass(studclass, new_student.get());
            }
        }else {
            model.addAttribute("class",studclass);
            model.addAttribute("loginError",username);
            return "findUser";
        }
        return "redirect:/classes";
    }

    @PostMapping("/removeUser/{studclass}/{user}")
    public String removeUser(
            @PathVariable StudClass studclass,
            @PathVariable User user
    ) {
        studClassService.removeUserFromClass(studclass, user);
        return "redirect:/classes";
    }

    @PostMapping("/leaveClass/{studclass}")
    public String leaveClass(
            @PathVariable StudClass studclass,
            @AuthenticationPrincipal User user
    ) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());

        studClassService.removeUserFromClass(studclass, full_user);
        return "redirect:/classes";
    }
}
