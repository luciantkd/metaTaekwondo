package com.taekwondo.metataekwondowebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationPropertiesIntegrationTest {

    @Autowired
    private Environment env;

    @Test
    void testDatabaseProperties() {
        assertThat(env.getProperty("spring.datasource.url"))
                .isEqualTo("jdbc:mysql://localhost:3306/metaTaekwondo?useSSL=false&allowPublicKeyRetrieval=true");
        assertThat(env.getProperty("spring.datasource.username")).isEqualTo(System.getenv("DB_USERNAME"));
        assertThat(env.getProperty("spring.datasource.driver-class-name"))
                .isEqualTo("com.mysql.cj.jdbc.Driver");
    }

    @Test
    void testEmailProperties() {
        assertThat(env.getProperty("spring.mail.host")).isEqualTo("smtp.gmail.com");
        assertThat(env.getProperty("spring.mail.port")).isEqualTo("587");
        assertThat(env.getProperty("spring.mail.username")).isEqualTo(System.getenv("EMAIL_USERNAME"));
    }
}
