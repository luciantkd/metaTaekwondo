package com.taekwondo.metataekwondowebsite.web;

import ch.qos.logback.classic.Logger;
import com.taekwondo.metataekwondowebsite.model.Statistics;
import com.taekwondo.metataekwondowebsite.model.User;
import com.taekwondo.metataekwondowebsite.service.StatisticsService;
import com.taekwondo.metataekwondowebsite.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private UserService userService;


    @GetMapping("/data")
    public ResponseEntity<Statistics> getStatisticsForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username);

        if (user != null) {
            Statistics statistics = statisticsService.getStatisticsByUser(user.getId());
            if (statistics == null) {

                logger.info("No statistics found for user: {}. Initializing empty statistics.", username);
                Statistics initialStats = new Statistics();
                initialStats.setUser(user);
                initialStats.setQuizAttempts(1);
                return ResponseEntity.ok(initialStats);
            }
            return ResponseEntity.ok(statistics);
        } else {
            logger.warn("User not found for username: {}", username);
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/update")
    public ResponseEntity<Statistics> updateStatistics(@RequestBody Statistics newStatistics) {
        Statistics updatedStatistics = statisticsService.saveOrUpdateStatistics(newStatistics);
        return ResponseEntity.ok(updatedStatistics);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long id) {
        statisticsService.deleteStatistics(id);
        return ResponseEntity.noContent().build();
    }
}
