package DAO;

import domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SuppressWarnings("Duplicates")
public class H2User  implements AutoCloseable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(H2User.class);

    public static final String MEMORY = "jdbc:h2:mem:shop";
    public static final String FILE = "jdbc:h2:~/shop";

    private Connection connection;

    static Connection getConnection(String db) throws SQLException, ClassNotFoundException {
            Class.forName("org.h2.Driver");  // ensure the driver class is loaded when the DriverManager looks for an installed class. Idiom.
            return DriverManager.getConnection(db, "sa", "");  // default password, ok for embedded.
    }

    public H2User() {
        this(MEMORY);
    }

    public H2User(String db) {
        try {
            connection = getConnection(db);
            loadResource("/user.sql");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) {
        final String ADD_USER_QUERY = "INSERT INTO user (username, hash) VALUES (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(ADD_USER_QUERY)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getHash());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findUsers() {
        final String LIST_USERS_QUERY = "SELECT username FROM user";
        List<User> out = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(LIST_USERS_QUERY)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(new User(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)));
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    private void loadResource(String name) {
        try {
            String cmd = new Scanner(getClass().getResource(name).openStream()).useDelimiter("\\Z").next();
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
