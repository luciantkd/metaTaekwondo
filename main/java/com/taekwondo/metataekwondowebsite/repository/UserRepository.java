package com.taekwondo.metataekwondowebsite.repository;

import com.taekwondo.metataekwondowebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    User findByResetPasswordToken(String token);
}
