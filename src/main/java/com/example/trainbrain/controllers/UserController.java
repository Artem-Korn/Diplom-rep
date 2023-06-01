package com.example.trainbrain.controllers;

import com.example.trainbrain.models.Mark;
import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import com.example.trainbrain.service.MarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserController {
    private final JpaUserDetailsService jpaUserDetailsService;
    private final MarkService markService;

    public UserController(JpaUserDetailsService jpaUserDetailsService, MarkService markService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.markService = markService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(
            Model model,
            @PageableDefault(sort = {"username"}, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String username
    ) {
        Page<User> page;

        if (username.isEmpty()) page = jpaUserDetailsService.findAll(pageable);
        else page = jpaUserDetailsService.findAllByUsernameContains(username, pageable);

        model.addAttribute("username", username);
        model.addAttribute("page", page);
        model.addAttribute("page_numbers", IntStream.range(0, page.getTotalPages()).toArray());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("edit/{user}")
    public String userEditForm(
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editUsername")
    public String saveUpdatedUsername(
            @RequestParam("user_id") User user,
            @RequestParam String username,
            Model model
    ) {
        if (!jpaUserDetailsService.saveUserUsername(user, username)) {
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            model.addAttribute("usernameError", "Користувач з таким логіном вже існує або Ви не змінили логін");
            return "userEdit";
        }
        return "redirect:/users/edit/" + user.getId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editRoles")
    public String saveUpdatedRoles(
            @RequestParam("user_id") User user,
            @RequestParam Map<String, String> form,
            Model model
    ) {
        if (!jpaUserDetailsService.saveUserRoles(user, form)) {
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            model.addAttribute("rolesError", "У користувача має бути мінімум одна роль");
            return "userEdit";
        }
        return "redirect:/users/edit/" + user.getId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/remove/{user}")
    public String userRemove(@PathVariable User user) {
        jpaUserDetailsService.removeUser(user);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal User user) {
        jpaUserDetailsService.loadUserByUsername(user.getUsername());
        return "redirect:/users/profile/" + user.getId();
    }

    @GetMapping("/profile/{user}")
    public String getUserProfile(
            Model model,
            @PathVariable User user,
            @AuthenticationPrincipal User cur_user,
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        boolean is_teacher = cur_user.getRoles().contains(Role.TEACHER);
        boolean is_guest = !user.getId().equals(cur_user.getId());

        User full_user = jpaUserDetailsService.getUserById(user.getId());
        model.addAttribute("user", full_user);

        model.addAttribute("is_teacher", is_teacher);
        model.addAttribute("is_guest", is_guest);

        if (is_teacher || !is_guest) {
            model.addAttribute("chart_data", markService.getChartData(full_user));

            Page<Mark> page = markService.findByUser(full_user, pageable);
            model.addAttribute("page", page);
            model.addAttribute("page_numbers", IntStream.range(0, page.getTotalPages()).toArray());
        }

        return "profile";
    }

    @PostMapping("/profile/editPassword")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password_new
    ) {
        jpaUserDetailsService.updateProfile(user, password_new);
        return "redirect:/users/profile";
    }
}
