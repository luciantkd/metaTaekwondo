package com.taekwondo.metataekwondowebsite;

import static org.assertj.core.api.Assertions.assertThat;

import com.taekwondo.metataekwondowebsite.web.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

    @Autowired
    private MainController mainController;

    @Autowired
    private ForgotPasswordController forgotPasswordController;

    @Autowired
    private LoginController loginController;

    @Autowired
    private ProfileController profileController;

    @Autowired
    private StatisticsController statisticsController;

    @Autowired
    private UserRegistrationController userRegistrationController;

    @Test
    void contextLoads() throws Exception {
        assertThat(mainController).isNotNull();
        assertThat(forgotPasswordController).isNotNull();
        assertThat(loginController).isNotNull();
        assertThat(profileController).isNotNull();
        assertThat(statisticsController).isNotNull();
        assertThat(userRegistrationController).isNotNull();
    }
}
