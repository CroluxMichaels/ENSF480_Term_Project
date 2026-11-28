package Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        // Format: jdbc:mysql://<host>:<port>/<database_name>
        String url = "jdbc:mysql://localhost:3306/airlinedata";
        String user = "root";
        String password = "MySQL2024";

        String query = "SELECT account_id, given_name FROM person WHERE account_id > ?";

        // try-with-resources automatically closes Connection, PreparedStatement, and ResultSet
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            System.out.println("Connected to the database successfully!");

            // Set parameter and execute query
            pstmt.setInt(1, 10);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String name = rs.getString("given_name");
                    System.out.println("ID: " + id + ", Name: " + name);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}