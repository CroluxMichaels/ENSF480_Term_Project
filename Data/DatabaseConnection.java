
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConnection() {
        url = "jdbc:mysql://localhost:3306/airlinedata";
        user = "root";
        password = "admin";
    }

    // Thread-safe Singleton initialization
    private static class Holder {
        private static final DatabaseConnection INSTANCE =
                new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }

    public <T> T executeQuery(
            String query,
            ResultSetMapper<T> mapper,
            Object... parameters) throws SQLException {

        try (Connection conn =
                     DriverManager.getConnection(url, user, password);

             PreparedStatement pstmt =
                     conn.prepareStatement(query)) {

            // Bind all parameters
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                return mapper.map(rs);
            }
        }
    }

    public int executeUpdate(
            String query,
            Object... parameters) throws SQLException {

        try (Connection conn =
                     DriverManager.getConnection(url, user, password);

             PreparedStatement pstmt =
                     conn.prepareStatement(query)) {

            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }

            return pstmt.executeUpdate();
        }
    }
}
