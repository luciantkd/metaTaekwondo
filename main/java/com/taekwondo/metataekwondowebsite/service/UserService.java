package com.taekwondo.metataekwondowebsite.service;

import com.taekwondo.metataekwondowebsite.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.taekwondo.metataekwondowebsite.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    void deleteUserByEmail(String email);
    void createPasswordResetTokenForUser(String email);
    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;
    User getByResetPasswordToken(String token);
    void updatePassword(User user, String newPassword);
    User findByEmail(String email);
    User save(User user);
    Long getCurrentUserId();
    User findById(Long id);
}
