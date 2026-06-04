/*
Name:Alexandro Serrato
Date:6/2/2026
Program Description: AI Quiz Game
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // 1. Global constants to declare boundaries for the quiz structure uniformly across methods
    public static final int NUMBER_OF_QUESTIONS = 10;
    public static final int NUMBER_OF_CHOICES = 4;

    public static void main(String[] args) {
        // 2. A 1D array allocated to store the raw text string for each individual question
        String[] questions = new String[NUMBER_OF_QUESTIONS];
        
        // 3. A 2D array mapping each question index (rows) to its 4 multiple-choice options (columns)
        String[][] answers = new String[NUMBER_OF_QUESTIONS][NUMBER_OF_CHOICES];
        
        // 4. An integer array to keep track of the correct answer option index (0-3) for each question
        int[] correctAnswers = new int[NUMBER_OF_QUESTIONS];

        // 5. Calls the file-parsing method to load data directly from the CSV into our active arrays
        readQuizFile(questions, answers, correctAnswers);

        // 6. Creates a Scanner object to capture player response entries from the system terminal
        Scanner input = new Scanner(System.in);
        
        // 7. Counter variable tracking the absolute total of correct answers provided by the user
        int score = 0;

        System.out.println("Welcome to the AI Quiz Game!");
        System.out.println("Choose the correct answer by entering 1, 2, 3, or 4.\n");

        // 8. Outer loop iterating sequentially through each question slot inside the questions array
        for (int i = 0; i < questions.length; i++) {
            // 9. Displays the question number dynamically to the player (adjusting 0-index to human-readable 1)
            System.out.println("Question " + (i + 1) + ": " + questions[i]);

            // 10. Inner loop iterating across columns of the current row to display all four potential options
            for (int j = 0; j < answers[i].length; j++) {
                // 11. Prints option lists numbered 1 through 4 alongside the actual multiple-choice text strings
                System.out.println((j + 1) + ". " + answers[i][j]);
            }

            System.out.print("Your answer: ");
            // 12. Decrements user numerical entry by 1 to scale human inputs (1-4) down to Java index constraints (0-3)
            int userAnswer = input.nextInt() - 1;

            // 13. Comparison statement checking if the player's choice matches the verified key index
            if (userAnswer == correctAnswers[i]) {
                System.out.println("Correct!\n");
                // 14. Adds 1 point to the tracking score accumulator for a correct submission
                score++;
            } else {
                System.out.println("Incorrect.");
                // 15. Extracts the specific string text of the correct choice from the 2D array to show the player
                System.out.println("The correct answer was: " + answers[i][correctAnswers[i]] + "\n");
            }
        }

        System.out.println("Quiz complete!");
        System.out.println("Your final score is: " + score + " out of " + questions.length);

        // 16. Fixed Formula: Calculates overall percentage by utilizing the 'score' variable instead of the array reference
        int percentage = (score * 100) / NUMBER_OF_QUESTIONS;

        System.out.println("Your score: " + percentage + "%");

        // --- ENHANCEMENT START ---
        // 17. Enhancement: Reviews final performance percentage metrics to offer targeted motivational feedback
        if (percentage == 100) {
            System.out.println("Excellent work! You achieved a perfect score on this quiz.");
        } else if (percentage >= 70) {
            System.out.println("Good job! You clearly understand the core fundamentals.");
        } else {
            System.out.println("Don't give up! Look over the source material and try the quiz again.");
        }
        // --- ENHANCEMENT END ---

        // 18. Safely closes down the console data streams to minimize memory usage overhead
        input.close();
    }

    // Helper method dedicated entirely to processing file streams and structuring CSV row strings
    public static void readQuizFile(String[] questions, String[][] answers, int[] correctAnswers) {
        try {
            // 19. Instantiates a dedicated reader connection bound directly to our source CSV filename
            File file = new File("ai_quiz_questions.csv");
            Scanner fileReader = new Scanner(file);

            // 20. Advances past the initial file row to discard plain text data titles or table column headers
            fileReader.nextLine();

            int index = 0;

            // While loop to process data row-by-row until either the file ends or the arrays are full
            while (fileReader.hasNextLine() && index < questions.length) {
                String line = fileReader.nextLine();
                // Splits current comma-delimited lines into separate cells stored temporarily in a flat string array
                String[] data = line.split(",");

                // Assigns the first element of each split text sequence directly into our main question bank
                questions[index] = data[0];

                // Nested loop shifting row cell text elements into matching 2D coordinate array paths
                for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
                    answers[index][i] = data[i + 1];
                }

                // Temporary structural assignment setting baseline key indices to index 0
                correctAnswers[index] = 0;
                index++;
            }

            // Closes external file stream pipeline safely after loop criteria complete entirely
            fileReader.close();

        } catch (FileNotFoundException e) {
            // Error handling fallback block execution if the target filename or pathing references fail
            System.out.println("The quiz file could not be found.");
        }
    }
}