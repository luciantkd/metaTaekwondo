# Development of an Educational Website for Taekwondo Grading Preparation

## Abstract
In response to the lack of specialised resources for Taekwondo grading preparation, this project developed "MetaTaekwondo," an educational website tailored to practitioners' needs. The platform integrates interactive quizzes adjusted to users' belt levels, performance analytics, and belt-specific content for smooth effective learning. Through these specialised features, MetaTaekwondo aims to enhance students' knowledge and grading readiness. This project outlines the entire development process, from initial concept creation and requirements analysis through implementation and deployment, finishing in user evaluation and suggestions for future enhancements.

## Overview
Meta Taekwondo Website is a web application for managing taekwondo classes, students, and statistics. It includes features like user registration, login, and CAPTCHA validation.

## Features
- User Registration
- User Login with CAPTCHA validation
- Profile Management
- Statistics Tracking

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- reCAPTCHA
- Thymeleaf

## Setup and Installation

1. **Clone the repository**:
    ```sh
    git clone git@github.com:luciantkd/metaTaekwondo.git
    cd metaTaekwondo
    ```

2. **Configure the database**:
    - Update the `application.properties` file with your database configuration.
    - Ensure you have MySQL installed and running.

3. **Set up environment variables**:
    - Create a `.env` file in the project root and add your environment variables.
    ```plaintext
    DB_USERNAME=your_db_username
    DB_PASSWORD=your_db_password
    EMAIL_USERNAME=your_email_username
    EMAIL_PASSWORD=your_email_password
    RECAPTCHA_SITE_KEY=your_recaptcha_site_key
    RECAPTCHA_SECRET_KEY=your_recaptcha_secret_key
    ```

4. **Build and run the project**:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

## Running Tests
To run the tests, use the following command:
```sh
mvn test
