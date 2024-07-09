# Library Management System

A Java-based Library Management System that allows users to register, login, add books (librarian role), view all books, and borrow books (member role). The system uses JDBC for database operations.

## Features

- **User Registration and Login**: Users can register with a username, password, and role (librarian or member) and login to the system.
- **Librarian Functions**:
  - Add new books to the library.
  - View all books available in the library.
- **Member Functions**:
  - View all books available in the library.
  - Borrow books from the library.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- JDBC driver for your database
- A database (e.g., MySQL, PostgreSQL, etc.)

### Setting Up the Database

1. Create a database for the library system.
2. Create the necessary tables:
   ```sql
   CREATE TABLE Users (
       user_id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       role VARCHAR(50) NOT NULL
   );

   CREATE TABLE Books (
       book_id INT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       author VARCHAR(255) NOT NULL,
       isbn VARCHAR(50) NOT NULL,
       available_copies INT NOT NULL,
       total_copies INT NOT NULL
   );

   CREATE TABLE BorrowedBooks (
       id INT AUTO_INCREMENT PRIMARY KEY,
       user_Id INT NOT NULL,
       book_Id INT NOT NULL,
       borrow_Date DATE NOT NULL,
       FOREIGN KEY (user_Id) REFERENCES Users(user_id),
       FOREIGN KEY (book_Id) REFERENCES Books(book_id)
   );

### Configuration

1. Update the `DatabaseConnection` class with your database connection details.

   ```java
   public class DatabaseConnection {
       private static final String URL = "jdbc:your_database_url";
       private static final String USERNAME = "your_database_username";
       private static final String PASSWORD = "your_database_password";

       public static Connection getConnection() throws SQLException {
           return DriverManager.getConnection(URL, USERNAME, PASSWORD);
       }
   }
   ```

### Running the Application

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
   ```

2. Compile and run the application:
   ```sh
   javac LibraryManagementSystem.java
   java LibraryManagementSystem
   ```

## Usage

### User Registration

1. Run the application.
2. Select "Register" from the main menu.
3. Enter a username, password, and role (librarian or member).

### User Login

1. Run the application.
2. Select "Login" from the main menu.
3. Enter your username and password.

### Librarian Functions

- After logging in as a librarian, you can:
  - Add new books to the library.
  - View all books in the library.

### Member Functions

- After logging in as a member, you can:
  - View all books in the library.
  - Borrow books from the library.


### Additional Notes
- Ensure you have your `DatabaseConnection` class properly set up for your specific database.
- Modify the database schema creation SQL to match your database's syntax if necessary.
- Room for Improvement: There is potential to expand functionality by adding more CRUD operations (update and delete books, manage users, etc.).
- Input Sanitization: Input validation and sanitization can be enhanced to ensure robustness and security of the application.

