package com.taekwondo.metataekwondowebsite.service;

import com.taekwondo.metataekwondowebsite.model.Statistics;
import com.taekwondo.metataekwondowebsite.model.User;
import com.taekwondo.metataekwondowebsite.repository.StatisticsRepository;
import com.taekwondo.metataekwondowebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Statistics getStatisticsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return statisticsRepository.findByUser(user);
    }


    // Method to save new statistics or return existing ones
    public Statistics save(Statistics statistics) {
        // Check if statistics already exist for the user
        Statistics statistics1 = this.statisticsRepository.findByUser(statistics.getUser());
        if (statistics1 == null) {
            // Save and return new statistics if none exist
            statistics1 = statisticsRepository.save(statistics);
            return statistics1;
        } else {
            // Return the existing statistics without saving
            return statistics;
        }
    }

    @Override
    public Statistics saveOrUpdateStatistics(Statistics newStatistics) {
        // Check if statistics already exist for the given user
        Statistics existingStatistics = statisticsRepository.findByUser(newStatistics.getUser());

        if (existingStatistics != null) {
            // Update existing statistics
            existingStatistics.setQuizAttempts(newStatistics.getQuizAttempts() + 1);
            existingStatistics.setQuestionsRight(newStatistics.getQuestionsRight());
            existingStatistics.setQuestionsWrong(newStatistics.getQuestionsWrong());

            // Update the average score based on the new data
            int totalQuestionsExisting = existingStatistics.getQuestionsRight() + existingStatistics.getQuestionsWrong();
            int totalQuestionsNew = newStatistics.getQuestionsRight() + newStatistics.getQuestionsWrong();

            float newTotalScore = (existingStatistics.getAverageScore() * totalQuestionsExisting)
                    + (newStatistics.getAverageScore() * totalQuestionsNew);
            int combinedTotalQuestions = totalQuestionsExisting + totalQuestionsNew;
            float newAverageScore = combinedTotalQuestions > 0 ? newTotalScore / combinedTotalQuestions : 0;

            existingStatistics.setAverageScore(newAverageScore);

            // Update the average time per question based on the new data
            float newTotalTime = (existingStatistics.getAverageTimePerQuestion() * totalQuestionsExisting)
                    + (newStatistics.getAverageTimePerQuestion() * totalQuestionsNew);
            float newAverageTimePerQuestion = combinedTotalQuestions > 0 ? newTotalTime / combinedTotalQuestions : 0;

            existingStatistics.setAverageTimePerQuestion(newAverageTimePerQuestion);

            // Save the updated statistics
            return statisticsRepository.save(existingStatistics);
        } else {
            // Create a new statistics entry if none exists
            return statisticsRepository.save(newStatistics);
        }
    }



    @Override
    public void deleteStatistics(Long id) {
        statisticsRepository.deleteById(id);
    }
}
