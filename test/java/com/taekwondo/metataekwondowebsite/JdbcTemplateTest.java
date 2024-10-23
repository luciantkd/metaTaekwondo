package com.taekwondo.metataekwondowebsite;

import com.taekwondo.metataekwondowebsite.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Clean up the database and insert test data
        jdbcTemplate.execute("DELETE FROM user WHERE email = 'johndoe@gmail.com'");
        jdbcTemplate.update(
                "INSERT INTO user (first_name, last_name, email) VALUES (?, ?, ?)",
                "John", "Doe", "johndoe@gmail.com"
        );
    }

    @Test
    void testInsertAndQuery() {
        // Query for the inserted user
        User user = jdbcTemplate.queryForObject(
                "SELECT id, first_name, last_name, email FROM user WHERE email=?",
                new Object[]{"johndoe@gmail.com"},
                new UserRowMapper()
        );

        // Assert the user was inserted and retrieved correctly
        assertThat(user).isNotNull();
        assertEquals("John", user.getFirstName());  // Correct capitalization of "John"
        assertThat(user.getLastName()).isEqualTo("Doe"); // Correct capitalization of "Doe"
        assertThat(user.getEmail()).isEqualTo("johndoe@gmail.com");
    }

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}
