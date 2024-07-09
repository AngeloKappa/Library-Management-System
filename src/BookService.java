import java.sql.SQLException;
import java.util.List;

public class BookService {
    private BookDAO bookDAO = new BookDAO();

    public void addBook(Book book) throws SQLException {
        bookDAO.addBook(book);
    }

    public List<Book> getAllBooks() throws SQLException {
        return bookDAO.getAllBooks();
    }

    public void borrowBook(User user, Book book) throws SQLException {
        bookDAO.borrowBook(user, book);
    }

}
