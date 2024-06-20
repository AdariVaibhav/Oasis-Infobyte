import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser;

    public static void main(String[] args) {
        while (true) {
            displayHomeMenu();
        }
    }

    private static void displayHomeMenu() {
        System.out.println("===================================================");
        System.out.println("|              Welcome to ATM System              |");
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
        System.out.println("|                     Sign Up                     |");
        System.out.println("===================================================");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Deposit initial amount (minimum 2000): ");
        double balance = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        if (balance < 2000) {
            System.out.println("Initial deposit must be at least 2000. Signup failed.");
            return;
        }

        User newUser = new User(userId, pin, balance);
        users.put(userId, newUser);
        System.out.println("Sign up successful. You can now log in.");
    }

    private static void logIn() {
        System.out.println("===================================================");
        System.out.println("|                      Log In                     |");
        System.out.println("===================================================");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (users.containsKey(userId) && users.get(userId).getPin().equals(pin)) {
            currentUser = users.get(userId);
            System.out.println("Login successful.");
            displayUserMenu();
        } else {
            System.out.println("Invalid user ID or PIN. Please try again.");
        }
    }

    private static void displayUserMenu() {
        while (true) {
            System.out.println("===================================================");
            System.out.println("|                   User Menu                     |");
            System.out.println("===================================================");
            System.out.println("| 1. Transactions History                         |");
            System.out.println("| 2. Withdraw                                     |");
            System.out.println("| 3. Deposit                                      |");
            System.out.println("| 4. Transfer                                     |");
            System.out.println("| 5. Quit                                         |");
            System.out.println("===================================================");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
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

    private static void showTransactionHistory() {
        System.out.println("===================================================");
        System.out.println("|            Transactions History                 |");
        System.out.println("===================================================");
        for (String transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private static void withdraw() {
        System.out.println("===================================================");
        System.out.println("|                    Withdraw                     |");
        System.out.println("===================================================");
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        if (currentUser.getBalance() >= amount) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            currentUser.addTransaction("Withdrawn: " + amount);
            System.out.println("Withdrawal successful. Current balance: " + currentUser.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private static void deposit() {
        System.out.println("===================================================");
        System.out.println("|                     Deposit                     |");
        System.out.println("===================================================");
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        currentUser.setBalance(currentUser.getBalance() + amount);
        currentUser.addTransaction("Deposited: " + amount);
        System.out.println("Deposit successful. Current balance: " + currentUser.getBalance());
    }

    private static void transfer() {
        System.out.println("===================================================");
        System.out.println("|                    Transfer                     |");
        System.out.println("===================================================");
        System.out.print("Enter recipient user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        if (users.containsKey(recipientId) && currentUser.getBalance() >= amount) {
            User recipient = users.get(recipientId);
            currentUser.setBalance(currentUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            currentUser.addTransaction("Transferred " + amount + " to " + recipientId);
            recipient.addTransaction("Received " + amount + " from " + currentUser.getUserId());
            System.out.println("Transfer successful. Current balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid recipient user ID or insufficient balance.");
        }
    }
}

class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Initial deposit: " + balance);
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}
