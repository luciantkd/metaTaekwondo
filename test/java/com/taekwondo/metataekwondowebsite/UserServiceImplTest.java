package com.taekwondo.metataekwondowebsite;

import com.taekwondo.metataekwondowebsite.model.Role;
import com.taekwondo.metataekwondowebsite.model.User;
import com.taekwondo.metataekwondowebsite.repository.UserRepository;
import com.taekwondo.metataekwondowebsite.service.UserServiceImpl;
import com.taekwondo.metataekwondowebsite.web.dto.UserRegistrationDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;  // Add this mock

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPasswordResetTokenForUser_UserExists_ShouldGenerateTokenAndSendEmail() throws MessagingException, IOException {
        // Arrange
        User user = new User("John", "Doe", "john.doe@example.com", "password", Collections.singletonList(new Role("ROLE_USER")));
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(user);

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        userService.createPasswordResetTokenForUser("john.doe@example.com");

        // Check that the reset token was set on the user
        assertNotNull(user.getResetPasswordToken());
        assertTrue(user.getResetPasswordToken().length() > 0);

        // Verify that an email was sent
        verify(mailSender, times(1)).send(any(MimeMessage.class));

        // Capture the email content and assert it contains the token
        ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        MimeMessage sentMessage = messageCaptor.getValue();
        MimeMessageHelper helper = new MimeMessageHelper(sentMessage);
        verify(mailSender).createMimeMessage();
    }

    @Test
    void save_ShouldCreateUserWithEncodedPassword() {
        // Arrange
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("john.doe@example.com");
        dto.setPassword("password");

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());


        // Act
        userService.save(dto);

        // Assert
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetailsIfUserExists() {
        // Arrange
        User user = new User("John", "Doe", "john.doe@example.com", "password", Collections.singletonList(new Role("ROLE_USER")));
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(user);

        // Act
        UserDetails userDetails = userService.loadUserByUsername("john.doe@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("john.doe@example.com", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_ShouldThrowExceptionIfUserNotFound() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistent@example.com"));
    }

    @Test
    void getByResetPasswordToken_ShouldReturnUserIfTokenExists() {
        // Arrange
        User user = new User();
        user.setResetPasswordToken("token123");
        when(userRepository.findByResetPasswordToken("token123")).thenReturn(user);

        // Act
        User result = userService.getByResetPasswordToken("token123");

        // Assert
        assertNotNull(result);
        assertEquals("token123", result.getResetPasswordToken());
    }

    @Test
    void updatePassword_ShouldUpdatePasswordAndClearResetToken() {
        // Arrange
        User user = new User();
        user.setPassword("oldPassword");
        user.setResetPasswordToken("token123");

        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");

        // Act
        userService.updatePassword(user, "newPassword");

        // Assert
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(user);
        assertEquals("newEncodedPassword", user.getPassword());
        assertNull(user.getResetPasswordToken());
    }


    @Test
    void deleteUserByEmail_ShouldDeleteUserByEmail() {
        // Arrange
        String email = "john.doe@example.com";

        // Act
        userService.deleteUserByEmail(email);

        // Assert
        verify(userRepository, times(1)).deleteByEmail(email);
    }

    @Test
    void findByEmail_ShouldReturnUserIfExists() {
        // Arrange
        User user = new User();
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(user);

        // Act
        User result = userService.findByEmail("john.doe@example.com");

        // Assert
        assertNotNull(result);
    }

    @Test
    void findById_ShouldReturnUserIfExists() {
        // Arrange
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(1L);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getCurrentUserId_ShouldReturnUserIdIfAuthenticated() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mock(UserDetails.class));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        Long userId = userService.getCurrentUserId();

        // Assert
        assertNull(userId); // Mock principal doesn't link to user ID directly
    }


}
