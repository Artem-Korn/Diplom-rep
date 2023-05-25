package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final JpaUserDetailsService jpaUserDetailsService;

    public UserController(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(
            Model model,
            @PageableDefault(sort = {"username"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        model.addAttribute("users", jpaUserDetailsService.findAll(pageable));
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/filter")
    public String filter(@RequestParam String username) {
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}/edit")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{user}/edit")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        jpaUserDetailsService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{user}/remove")
    public String userRemove(@PathVariable User user) {
        jpaUserDetailsService.removeUser(user);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("user", full_user);
        return "profile";
    }

    @GetMapping("/{user}/profile")
    public String getUserProfile(Model model, @PathVariable User user, @AuthenticationPrincipal User cur_user) {
        if(user.getId().equals(cur_user.getId())) return "redirect:/user/profile";
        model.addAttribute("is_guest", true);
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password
    ) {
        jpaUserDetailsService.updateProfile(user, password);
        return "redirect:/user/profile";
    }
}
