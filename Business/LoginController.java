package Business;

import Data.DatabaseConnection;
import java.sql.SQLException;
public class LoginController {

    public String logIn(String username, String password)
            throws SQLException {

        String query = """
            SELECT role_name
            FROM person
            WHERE login_name = ?
              AND login_password = ?
              AND account_status = 'ACTIVE'
            """;

        DatabaseConnection connection =
                DatabaseConnection.getInstance();

        return connection.executeQuery(query, rs -> {
            if (rs.next()) {
                return rs.getString("role_name").toLowerCase();
            }
            return "N/A";
        }, username, password);
    }
}