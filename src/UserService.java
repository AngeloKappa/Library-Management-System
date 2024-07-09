import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void registerUser(User user) throws SQLException {
        userDAO.addUser(user);
    }
}
