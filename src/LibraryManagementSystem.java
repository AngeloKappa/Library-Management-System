import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static UserService userService = new UserService();
    private static BookService bookService = new BookService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (librarian/member): ");
        String role = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        try {
            userService.registerUser(user);
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            User user = userService.login(username, password);
            if (user != null) {
                System.out.println("Login successful! Welcome " + user.getUsername());
                if ("librarian".equals(user.getRole())) {
                    librarianMenu();
                } else {
                    memberMenu(user);
                }
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void librarianMenu() {
        while (true) {
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void memberMenu(User user) {
        while (true) {
            System.out.println("1. View All Books");
            System.out.println("2. Borrow Book");
            System.out.println("3: Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    borrowBook(user);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter total copies: ");
        int totalCopies = Integer.parseInt(scanner.nextLine());

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setTotalCopies(totalCopies);
        book.setAvailableCopies(totalCopies);

        try {
            bookService.addBook(book);
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            for (Book book : books) {
                System.out.println("ID: " + book.getId());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Available Copies: " + book.getAvailableCopies());
                System.out.println("Total Copies: " + book.getTotalCopies());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void borrowBook(User user) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        try {
            Book book = BookDAO.getBookByTitle(title);
            if (book != null) {
                // Book found, proceed with further actions
                System.out.println("Book found: " + book.getTitle() + " by " + book.getAuthor());
                bookService.borrowBook(user, book);
            } else {
                // Book not found, handle accordingly
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving book from the database.");
        }

    }

}


