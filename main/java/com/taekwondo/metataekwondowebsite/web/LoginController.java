package com.taekwondo.metataekwondowebsite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private CaptchaValidator captchaValidator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String captchaResponse = request.getParameter("recaptchaToken");

        // Validate CAPTCHA response
        if (!captchaValidator.validateCaptcha(captchaResponse)) {
            // If CAPTCHA validation fails, return to the login page with an error message
            model.addAttribute("error", "Captcha validation failed. Please try again.");
            return "login";
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Create an authentication token using the username and password
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            Authentication authentication = authenticationManager.authenticate(authToken);

            // If authentication is successful, set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "login";
        }
    }
}
