import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (title, author, isbn, available_copies, total_copies) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getAvailableCopies());
            stmt.setInt(5, book.getTotalCopies());
            stmt.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setTotalCopies(rs.getInt("total_copies"));
                books.add(book);
            }
        }
        return books;
    }

    public Book getBookByIsbn(String isbn) throws SQLException {
        String query = "SELECT * FROM Books WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setAvailableCopies(rs.getInt("available_copies"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    return book;
                }
            }
        }
        return null;
    }

    public static Book getBookByTitle(String title) throws SQLException {
        String query = "SELECT * FROM Books WHERE title = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setAvailableCopies(rs.getInt("available_copies"));
                    book.setTotalCopies(rs.getInt("total_copies"));
                    return book;
                }
            }
        }
        return null;
    }

    public void borrowBook(User user, Book book) throws SQLException {
        String borrowQuery = "INSERT INTO BorrowedBooks (user_Id, book_Id, borrow_Date) VALUES (?, ?, ?)";
        String updateBookQuery = "UPDATE Books SET available_copies = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement borrowStmt = conn.prepareStatement(borrowQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateBookQuery)) {

            if(book.getAvailableCopies() == 0){
                System.out.println("No more copies available");
                return;
            }

            // Set parameters for BorrowedBooks table
            borrowStmt.setInt(1, user.getId());
            borrowStmt.setInt(2, book.getId());
            borrowStmt.setDate(3, new Date(System.currentTimeMillis())); // Assuming current date

            borrowStmt.executeUpdate();

            // Update available copies in Books table
            int newAvailableCopies = book.getAvailableCopies() - 1;
            updateStmt.setInt(1, newAvailableCopies);
            updateStmt.setInt(2, book.getId());

            updateStmt.executeUpdate();

            System.out.println("Book borrowed successfully!");
        } catch (SQLException e) {
            throw new SQLException("Error borrowing book: " + e.getMessage());
        }
    }
}
