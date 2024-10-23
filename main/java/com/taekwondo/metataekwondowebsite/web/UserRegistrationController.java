package com.taekwondo.metataekwondowebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.taekwondo.metataekwondowebsite.service.UserService;
import com.taekwondo.metataekwondowebsite.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, RedirectAttributes redirectAttributes) {
        try {
            userService.save(registrationDto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "You've successfully registered to my awesome website!");
            return "redirect:/registration?success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Registration failed. Please try again.");
            return "redirect:/registration?error";
        }
    }
}
