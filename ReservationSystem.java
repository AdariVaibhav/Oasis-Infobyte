import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ReservationSystem {
    private static HashMap<String, String> users = new HashMap<>();
    private static List<Reservation> reservations = new ArrayList<>();

    static {
        // Sample users
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==========================================");
            System.out.println("🚆 Welcome to the Online Reservation System 🚆");
            System.out.println("==========================================");
            System.out.println("1️⃣  Login");
            System.out.println("2️⃣  Sign Up");
            System.out.println("3️⃣  Exit");
            System.out.print("🔹 Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    signUp(scanner);
                    break;
                case 3:
                    System.out.println("👋 Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("🔸 Enter username: ");
        String username = scanner.nextLine();
        System.out.print("🔸 Enter password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            System.out.println("✅ Login successful!");
            showMainMenu(scanner, username);
        } else {
            System.out.println("❌ Invalid credentials.");
        }
    }

    private static void signUp(Scanner scanner) {
        System.out.print("🔸 Enter desired username: ");
        String username = scanner.nextLine();
        System.out.print("🔸 Enter desired password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("❌ Username already exists. Please choose a different username.");
        } else {
            users.put(username, password);
            System.out.println("✅ Signup successful! You can now login.");
        }
    }

    private static void changePassword(Scanner scanner, String username) {
        System.out.print("🔸 Enter current password: ");
        String currentPassword = scanner.nextLine();

        if (users.get(username).equals(currentPassword)) {
            System.out.print("🔸 Enter new password: ");
            String newPassword = scanner.nextLine();
            users.put(username, newPassword);
            System.out.println("✅ Password changed successfully!");
        } else {
            System.out.println("❌ Incorrect current password.");
        }
    }

    private static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    private static void showMainMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("==========================================");
            System.out.println("🏠 Main Menu");
            System.out.println("==========================================");
            System.out.println("1️⃣  Make a Reservation");
            System.out.println("2️⃣  Cancel a Reservation");
            System.out.println("3️⃣  Change Password");
            System.out.println("4️⃣  Logout");
            System.out.print("🔹 Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    makeReservation(scanner);
                    break;
                case 2:
                    cancelReservation(scanner);
                    break;
                case 3:
                    changePassword(scanner, username);
                    break;
                case 4:
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.println("==========================================");
        System.out.println("📝 Make a Reservation");
        System.out.println("==========================================");
        System.out.print("🔸 Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("🔸 Enter train number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("🔸 Enter train name: ");
        String trainName = scanner.nextLine();
        System.out.print("🔸 Enter class type: ");
        String classType = scanner.nextLine();
        System.out.print("🔸 Enter date of journey (YYYY-MM-DD): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("🔸 Enter departure place: ");
        String from = scanner.nextLine();
        System.out.print("🔸 Enter destination place: ");
        String to = scanner.nextLine();

        Reservation reservation = new Reservation(name, trainNumber, trainName, classType, dateOfJourney, from, to);
        reservations.add(reservation);
        System.out.println("✅ Reservation successful! Your PNR is " + reservation.getPnr());
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.println("==========================================");
        System.out.println("🗑️ Cancel a Reservation");
        System.out.println("==========================================");
        System.out.print("🔸 Enter PNR number: ");
        String pnr = scanner.nextLine();

        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();
            if (reservation.getPnr().equals(pnr)) {
                System.out.println("📋 Reservation found: " + reservation);
                System.out.print("🔹 Do you want to cancel this reservation? (yes/no): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    iterator.remove();
                    System.out.println("✅ Reservation canceled.");
                } else {
                    System.out.println("❌ Cancellation aborted.");
                }
                return;
            }
        }
        System.out.println("❌ Reservation not found.");
    }
}

class Reservation {
    private static int counter = 0;
    private String pnr;
    private String name;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String from;
    private String to;

    public Reservation(String name, String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to) {
        this.pnr = "PNR" + (++counter);
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    public String getPnr() {
        return pnr;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "pnr='" + pnr + '\'' +
                ", name='" + name + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", classType='" + classType + '\'' +
                ", dateOfJourney='" + dateOfJourney + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
