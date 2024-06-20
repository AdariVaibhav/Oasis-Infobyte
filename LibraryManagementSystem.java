import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {

    // Model classes
    static class Book {
        private int id;
        private String title;
        private String author;
        private String category;
        private boolean available;

        public Book(int id, String title, String author, String category, boolean available) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.category = category;
            this.available = available;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getCategory() {
            return category;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public String toString() {
            return "Book ID: " + id + "\nTitle: " + title + "\nAuthor: " + author + "\nCategory: " + category + "\nAvailable: " + available + "\n";
        }
    }

    static class User {
        private int id;
        private String name;
        private String email;
        private String username;
        private String password;

        public User(int id, String name, String email, String username, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "User ID: " + id + "\nName: " + name + "\nEmail: " + email + "\n";
        }
    }

    static class Admin {
        private String username;
        private String password;

        public Admin(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class LibraryService {
        private List<Book> books;
        private List<User> users;

        public LibraryService() {
            this.books = new ArrayList<>();
            this.users = new ArrayList<>();
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public void removeBook(int id) {
            books.removeIf(book -> book.getId() == id);
        }

        public List<Book> getAllBooks() {
            return books;
        }

        public void addUser(User user) {
            users.add(user);
        }

        public List<User> getAllUsers() {
            return users;
        }

        public User getUserByUsername(String username) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }

        public void issueBook(int bookId, int userId) {
            for (Book book : books) {
                if (book.getId() == bookId && book.isAvailable()) {
                    book.setAvailable(false);
                    System.out.println("Book issued to user ID: " + userId);
                    return;
                }
            }
            System.out.println("Book not available or not found");
        }

        public void returnBook(int bookId) {
            for (Book book : books) {
                if (book.getId() == bookId && !book.isAvailable()) {
                    book.setAvailable(true);
                    System.out.println("Book returned");
                    return;
                }
            }
            System.out.println("Book not found or already available");
        }
    }

    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        libraryService.addBook(new Book(1, "1984", "George Orwell", "Fiction", true));
        libraryService.addBook(new Book(2, "To Kill a Mockingbird", "Harper Lee", "Fiction", true));

        libraryService.addUser(new User(1, "John Doe", "john.doe@example.com", "john_doe", "password123"));
        libraryService.addUser(new User(2, "Jane Doe", "jane.doe@example.com", "jane_doe", "password456"));

        Admin admin = new Admin("admin", "1234");

        while (true) {
            System.out.println("===================================");
            System.out.println("         Library Management        ");
            System.out.println("===================================");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                    adminMenu(libraryService, scanner);
                } else {
                    User user = libraryService.getUserByUsername(username);
                    if (user != null && user.getPassword().equals(password)) {
                        userMenu(libraryService, scanner, user);
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                }
            } else if (option == 2) {
                signup(libraryService, scanner);
            } else if (option == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void adminMenu(LibraryService libraryService, Scanner scanner) {
        while (true) {
            System.out.println("===================================");
            System.out.println("            Admin Menu             ");
            System.out.println("===================================");
            System.out.println("1. View All Books");
            System.out.println("2. Add Book");
            System.out.println("3. Remove Book");
            System.out.println("4. View All Users");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                System.out.println("Books in Library:");
                for (Book book : libraryService.getAllBooks()) {
                    System.out.println(book);
                }
            } else if (option == 2) {
                System.out.print("Enter Book ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter Book Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Book Author: ");
                String author = scanner.nextLine();
                System.out.print("Enter Book Category: ");
                String category = scanner.nextLine();
                libraryService.addBook(new Book(id, title, author, category, true));
                System.out.println("Book added.");
            } else if (option == 3) {
                System.out.print("Enter Book ID to remove: ");
                int id = scanner.nextInt();
                libraryService.removeBook(id);
                System.out.println("Book removed.");
            } else if (option == 4) {
                System.out.println("Users in Library:");
                for (User user : libraryService.getAllUsers()) {
                    System.out.println(user);
                }
            } else if (option == 5) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void userMenu(LibraryService libraryService, Scanner scanner, User user) {
        while (true) {
            System.out.println("===================================");
            System.out.println("             User Menu             ");
            System.out.println("===================================");
            System.out.println("Welcome, " + user.getName());
            System.out.println("1. View All Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                System.out.println("Books in Library:");
                for (Book book : libraryService.getAllBooks()) {
                    System.out.println(book);
                }
            } else if (option == 2) {
                System.out.print("Enter Book ID to issue: ");
                int bookId = scanner.nextInt();
                libraryService.issueBook(bookId, user.getId());
            } else if (option == 3) {
                System.out.print("Enter Book ID to return: ");
                int bookId = scanner.nextInt();
                libraryService.returnBook(bookId);
            } else if (option == 4) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void signup(LibraryService libraryService, Scanner scanner) {
        System.out.println("===================================");
        System.out.println("             Signup                ");
        System.out.println("===================================");
        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Your Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Desired Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Desired Password: ");
        String password = scanner.nextLine();

        int newUserId = libraryService.getAllUsers().size() + 1;
        libraryService.addUser(new User(newUserId, name, email, username, password));
        System.out.println("Signup successful! You can now login with your credentials.");
    }
}
