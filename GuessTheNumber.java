import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        int rounds = 0;

        System.out.println("==========================================");
        System.out.println("🎉 Welcome to the Guess the Number Game! 🎉");
        System.out.println("==========================================");

        while (true) {
            rounds++;
            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("Round " + rounds + ":");
            System.out.println("I have selected a number between 1 and 100. Can you guess it?");

            while (attempts < MAX_ATTEMPTS && !guessed) {
                System.out.print("🔸 Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    guessed = true;
                    int points = MAX_ATTEMPTS - attempts + 1;
                    totalScore += points;
                    System.out.println("✅ Correct! You guessed the number in " + attempts + " attempts. You earned " + points + " points.");
                } else if (userGuess < numberToGuess) {
                    System.out.println("🔺 Higher!");
                } else {
                    System.out.println("🔻 Lower!");
                }
            }

            if (!guessed) {
                System.out.println("❌ You've used all your attempts. The number was: " + numberToGuess);
            }

            System.out.print("🔹 Do you want to play another round? (yes/no): ");
            scanner.nextLine(); // Consume newline
            String response = scanner.nextLine();

            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("==========================================");
        System.out.println("🏆 Game Over!");
        System.out.println("Rounds Played: " + rounds);
        System.out.println("Total Score: " + totalScore);
        System.out.println("==========================================");
    }
}
