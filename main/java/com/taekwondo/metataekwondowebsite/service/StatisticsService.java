package com.taekwondo.metataekwondowebsite.service;

import com.taekwondo.metataekwondowebsite.model.Statistics;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {
    Statistics getStatisticsByUser(Long userId);
    Statistics save(Statistics statistics);
    void deleteStatistics(Long id);
    Statistics saveOrUpdateStatistics(Statistics statistics);
}
