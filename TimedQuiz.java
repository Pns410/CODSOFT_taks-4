import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TimedQuiz {

    private static final int QUESTION_TIME = 15;

    public static void main(String[] args) throws InterruptedException {
        List<Question> questions = prepareQuestions();
        int score = 0;

        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            System.out.println(question.getText());
            displayOptions(question.getOptions());

            long startTime = System.currentTimeMillis();
            String answer = getUserAnswer(scanner);
            long endTime = System.currentTimeMillis();

            double timeTaken = (endTime - startTime) / 1000.0;

            if (answer.equalsIgnoreCase(question.getCorrectAnswer()) && timeTaken <= QUESTION_TIME) {
                System.out.println("Correct! You answered within the time limit.");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer is " + question.getCorrectAnswer());
            }
        }

        System.out.println("\nQuiz completed!");
        System.out.println("Your score: " + score + "/" + questions.size());

        scanner.close();
    }

    private static List<Question> prepareQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("What is the capital of France?",
                List.of("London", "Paris", "Berlin"), "Paris"));
        questions.add(new Question("What is the largest planet in our solar system?",
                List.of("Earth", "Jupiter", "Mars"), "Jupiter"));

        return questions;
    }

    private static void displayOptions(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    private static String getUserAnswer(Scanner scanner) throws InterruptedException {
        System.out.print("Enter your answer (within " + QUESTION_TIME + " seconds): ");

        long startTime = System.currentTimeMillis();
        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime / 1000 >= QUESTION_TIME) {
                System.out.println("Time's up!");
                return "";
            }

            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}

class Question {
    private String text;
    private List<String> options;
    private String correctAnswer;

    public Question(String text, List<String> options, String correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
