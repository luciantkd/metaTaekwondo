package com.taekwondo.metataekwondowebsite.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.taekwondo.metataekwondowebsite.model.User;
import com.taekwondo.metataekwondowebsite.repository.UserRepository;
import com.taekwondo.metataekwondowebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/reset-password")
    @ResponseBody
    public String resetPassword(@RequestParam("email") String email) {
        userService.createPasswordResetTokenForUser(email);
        return "Password reset token sent to email";
    }

    @DeleteMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserByEmail(email);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
            return "redirect:/home";
        } catch (Exception e) {
            // Log the exception
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete user. Please try again.");
            return "redirect:/home";
        }
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        Long currentUserId = userService.getCurrentUserId();
        if (currentUserId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(currentUserId).orElse(null);
        if (user == null) {
            return "redirect:/error";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(@RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("bio") String bio,
                                    Model model) {
        Long currentUserId = userService.getCurrentUserId();
        if (currentUserId == null) {
            logger.warn("No authenticated user found");
            return "redirect:/login";
        }

        User user = userRepository.findById(currentUserId).orElse(null);
        if (user == null) {
            logger.error("User with ID {} not found", currentUserId);
            return "redirect:/error";
        }

        logger.info("Updating user profile for user ID {}: firstName={}, lastName={}, bio={}",
                currentUserId, firstName, lastName, bio);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBio(bio);

        userRepository.save(user);  // Save the updated user information
        logger.info("User profile updated successfully for user ID {}", currentUserId);

        model.addAttribute("user", user);
        model.addAttribute("success", "Profile updated successfully!");

        return "profile";  // Reload the profile page with the updated information
    }
}
