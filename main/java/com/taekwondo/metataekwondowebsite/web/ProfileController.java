package com.taekwondo.metataekwondowebsite.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.taekwondo.metataekwondowebsite.model.User;
import com.taekwondo.metataekwondowebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        Long currentUserId = userService.getCurrentUserId();
        if (currentUserId == null) {
            return "redirect:/login";
        }

        User user = userService.findById(currentUserId);
        if (user == null) {
            return "redirect:/error";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateUserProfile(@RequestParam("firstName") String firstName,
                                                                 @RequestParam("lastName") String lastName,
                                                                 @RequestParam("bio") String bio) {
        Long currentUserId = userService.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findById(currentUserId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setBio(bio);

            userService.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("bio", user.getBio());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
