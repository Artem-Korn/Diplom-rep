package com.example.trainbrain.controllers;

import com.example.trainbrain.models.User;
import com.example.trainbrain.service.JpaUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.util.Map;
import java.util.Objects;

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
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if(isConfirmEmpty) {
            model.addAttribute("passwordError", "Пароль підтвердження не може бути порожнім");
        }
        if(user.getPassword() != null && !Objects.equals(user.getPassword(), passwordConfirm)) {
            model.addAttribute("passwordError", "Паролі не співпадають");
            return "registration";
        }
        if(isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = UtilsController.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if(!jpaUserDetailsService.addUser(user)){
            model.addAttribute("usernameError", "Такий користувач вже існує");
            return "registration";
        }
        return "redirect:/login";
    }
}
