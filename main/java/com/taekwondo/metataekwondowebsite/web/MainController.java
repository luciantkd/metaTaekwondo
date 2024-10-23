package com.taekwondo.metataekwondowebsite.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "homePage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "registration";
    }


    @GetMapping("/statistics")
    public String statistics(Model model) {
        return "statistics";
    }


    @GetMapping("/quiz")
    public String quiz(@RequestParam(required = false) String belt, Model model) {
        if (belt == null || belt.isEmpty()) {
            return "redirect:/home";
        }
        model.addAttribute("belt", belt);
        return "quiz";
    }
}
