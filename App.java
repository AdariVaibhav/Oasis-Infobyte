import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Answer> answers = new ArrayList<>();
    private static MCQ[] mcqs = {
            new MCQ("What is the capital of France?", "Paris", "London", "Berlin", "Rome", "A"),
            new MCQ("Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "Saturn", "A"),
            new MCQ("Who painted the Mona Lisa?", "Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Claude Monet", "A")
    };
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayHomeMenu();
        }
    }

    private static void displayHomeMenu() {
        System.out.println("===================================================");
        System.out.println("|            Welcome to the MCQ Quiz App          |");
        System.out.println("===================================================");
        System.out.println("| 1. Sign Up                                      |");
        System.out.println("| 2. Log In                                       |");
        System.out.println("| 3. Exit                                         |");
        System.out.println("===================================================");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                signUp();
                break;
            case 2:
                logIn();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void signUp() {
        System.out.println("===================================================");
        System.out.println("|                   Sign Up                       |");
        System.out.println("===================================================");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        users.add(new User(username, password, email));
        System.out.println("Sign up successful. You can now log in.");
    }

    private static void logIn() {
        System.out.println("===================================================");
        System.out.println("|                    Log In                       |");
        System.out.println("===================================================");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful.");
                displayUserMenu();
                return;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
    }

    private static void displayUserMenu() {
        while (true) {
            System.out.println("===================================================");
            System.out.println("|                User Menu                        |");
            System.out.println("===================================================");
            System.out.println("| 1. Take MCQ Quiz                                |");
            System.out.println("| 2. View Answers                                 |");
            System.out.println("| 3. Update Profile                               |");
            System.out.println("| 4. Change Password                              |");
            System.out.println("| 5. Logout                                       |");
            System.out.println("===================================================");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    takeQuiz();
                    break;
                case 2:
                    viewAnswers();
                    break;
                case 3:
                    updateProfile();
                    break;
                case 4:
                    changePassword();
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void takeQuiz() {
        int score = 0;
        answers.clear();
        for (MCQ mcq : mcqs) {
            System.out.println("===================================================");
            System.out.println(mcq.getQuestion());
            System.out.println("A. " + mcq.getOptionA());
            System.out.println("B. " + mcq.getOptionB());
            System.out.println("C. " + mcq.getOptionC());
            System.out.println("D. " + mcq.getOptionD());
            System.out.print("Your answer (A/B/C/D): ");
            String userAnswer = scanner.nextLine().toUpperCase();

            if (userAnswer.equals(mcq.getCorrectOption())) {
                score++;
            }
            answers.add(new Answer(mcq, userAnswer));
        }
        System.out.println("===================================================");
        System.out.println("Quiz completed. Your score: " + score + "/" + mcqs.length);
        System.out.println("===================================================");
    }

    private static void viewAnswers() {
        System.out.println("===================================================");
        System.out.println("|                Your Answers                     |");
        System.out.println("===================================================");
        for (Answer answer : answers) {
            MCQ mcq = answer.getMcq();
            System.out.println(mcq.getQuestion());
            System.out.println("Correct answer: " + mcq.getCorrectOption());
            System.out.println("Your answer: " + answer.getSelectedOption());
            System.out.println();
        }
    }

    private static void updateProfile() {
        System.out.println("===================================================");
        System.out.println("|                Update Profile                   |");
        System.out.println("===================================================");
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        currentUser.setEmail(newEmail);
        System.out.println("Email updated successfully.");
    }

    private static void changePassword() {
        System.out.println("===================================================");
        System.out.println("|                Change Password                  |");
        System.out.println("===================================================");
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();
        if (currentPassword.equals(currentUser.getPassword())) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            currentUser.setPassword(newPassword);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect current password.");
        }
    }
}

class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class MCQ {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;

    public MCQ(String question, String optionA, String optionB, String optionC, String optionD, String correctOption) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}

class Answer {
    private MCQ mcq;
    private String selectedOption;

    public Answer(MCQ mcq, String selectedOption) {
        this.mcq = mcq;
        this.selectedOption = selectedOption;
    }

    public MCQ getMcq() {
        return mcq;
    }

    public String getSelectedOption() {
        return selectedOption;
    }
}
