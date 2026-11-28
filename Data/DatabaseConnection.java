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

    public java.util.List<String> getActivePromotions() throws SQLException {
        String sql = """
                SELECT offer_code, percent_off, news_message, ends_on
                FROM promotion
                WHERE CURRENT_DATE BETWEEN starts_on AND ends_on
                ORDER BY ends_on, offer_code
                """;

        return executeQuery(sql, rows -> {
            java.util.List<String> offers = new java.util.ArrayList<>();
            while (rows.next()) {
                String discount = rows.getBigDecimal("percent_off").stripTrailingZeros().toPlainString();
                offers.add(discount + "% off   |   " + rows.getString("offer_code")
                        + "\n" + rows.getString("news_message")
                        + "\nValid through " + rows.getDate("ends_on"));
            }
            return offers;
        });
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