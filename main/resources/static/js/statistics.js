document.addEventListener('DOMContentLoaded', function () {
    // Fetch statistics data from the server
    fetch(`/statistics/data`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch statistics data');
            }
            return response.json();
        })
        .then(data => {
            if (data) {
                // If data is available, extract statistics and user ID
                const stats = data;
                userId = stats.user.id;
                console.log("User ID: ", userId);
                quizAttempts = stats.quizAttempts;
                console.log("QUIZ " + stats.quizAttempts + "-" + stats.questionsRight + "-" + stats.questionsWrong + "-" + stats.averageScore + "-" + stats.averageTimePerQuestion);
                // Update HTML elements with the fetched statistics
                document.getElementById('quiz-attempts').value = (stats.averageScore === 0) ? 0 : stats.quizAttempts;
                document.getElementById('questions-right').value = stats.questionsRight;
                document.getElementById('questions-wrong').value = stats.questionsWrong;
                document.getElementById('average-score').value = stats.averageScore;
                document.getElementById('average-time').value = stats.averageTimePerQuestion;
                console.log("QUIZ " + stats.quizAttempts + "-" + stats.questionsRight + "-" + stats.questionsWrong + "-" + stats.averageScore + "-" + stats.averageTimePerQuestion);

            } else {
                console.warn('No user data available. Initializing default statistics.');
                userId = 'someUniqueIdForUser';
                quizAttempts = 0;
            }
        })
        .catch(error => console.error('Error fetching statistics:', error));

});


function submitQuizResults(userId, quizAttempts, questionsRight, questionsWrong, averageScore, averageTimePerQuestion, date) {
    if (!userId) {
        console.error("User ID is null, cannot submit quiz results.");
        return;
    }

    const quizData = {
        userId: userId,
        quizAttempts: quizAttempts,
        questionsRight: questionsRight,
        questionsWrong: questionsWrong,
        averageScore: averageScore,
        averageTimePerQuestion: averageTimePerQuestion,
        date: date
    };

    console.log("Submitting quiz data:", quizData);

    // Send the quiz data to the server via a POST request
    fetch('/statistics/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(quizData)  // Convert the quizData object to a JSON string
    })
        .then(response => {
            if (response.ok) {
                console.log('Quiz data saved successfully');
            } else {
                console.error('Failed to save quiz data');
            }
        })
        .catch(error => console.error('Error:', error));
}
