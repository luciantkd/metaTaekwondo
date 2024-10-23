package com.taekwondo.metataekwondowebsite.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "quiz_attempts")
    private int quizAttempts;

    @Column(name = "questions_right")
    private int questionsRight;

    @Column(name = "questions_wrong")
    private int questionsWrong;

    @Column(name = "average_score")
    private float averageScore;

    @Column(name = "average_time_per_question")
    private float averageTimePerQuestion;

    @Column(name = "date")
    private LocalDate date;

    public Statistics(Long id, User user, int quizAttempts, int questionsRight, int questionsWrong, float averageScore, float averageTimePerQuestion, LocalDate date) {
        this.id = id;
        this.user = user;
        this.quizAttempts = quizAttempts;
        this.questionsRight = questionsRight;
        this.questionsWrong = questionsWrong;
        this.averageScore = averageScore;
        this.averageTimePerQuestion = averageTimePerQuestion;
        this.date = date;
    }

    public Statistics() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(int quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public int getQuestionsRight() {
        return questionsRight;
    }

    public void setQuestionsRight(int questionsRight) {
        this.questionsRight = questionsRight;
    }

    public int getQuestionsWrong() {
        return questionsWrong;
    }

    public void setQuestionsWrong(int questionsWrong) {
        this.questionsWrong = questionsWrong;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public float getAverageTimePerQuestion() {
        return averageTimePerQuestion;
    }

    public void setAverageTimePerQuestion(float averageTimePerQuestion) {
        this.averageTimePerQuestion = averageTimePerQuestion;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

