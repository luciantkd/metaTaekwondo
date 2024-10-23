package com.taekwondo.metataekwondowebsite.repository;

import com.taekwondo.metataekwondowebsite.model.Statistics;
import com.taekwondo.metataekwondowebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Statistics findByUser(User user);
}
