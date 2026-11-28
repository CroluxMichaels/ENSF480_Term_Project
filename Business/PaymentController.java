package Business;

import Data.DatabaseConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import Business.Payment;

public class PaymentController {  
    public static int savePayment(Payment payment) throws SQLException{
        DatabaseConnection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO payment (reservation_id, outcome, amount, tried_at) VALUES (?, ?, ?, ?)";
        connection.executeUpdate(query, payment.getReservationID(), payment.getStatus(), payment.getAmount(), payment.getPaymentDate());
        query = "SELECT paid_reservation FROM payment WHERE reservation_id = ? ORDER BY paid_reservation DESC LIMIT 1";
        return connection.executeQuery(query, rs -> {
            if (rs.next()) {
                return rs.getInt("paid_reservation");
            }
            return -1;
        }, payment.getReservationID());
    }

    public static String retrieveOutcome(int payID) throws SQLException{
        DatabaseConnection connection = DatabaseConnection.getInstance();
        String query = "SELECT outcome FROM payment WHERE paid_reservation = ?";
        return connection.executeQuery(query, rs -> {
            if (rs.next()) {
                return rs.getString("outcome");
            }
            return "N/A";
        }, payID);
    }

    public static double retrieveAmount(int payID) throws SQLException{
        DatabaseConnection connection = DatabaseConnection.getInstance();
        String query = "SELECT amount FROM payment WHERE paid_reservation = ?";
        return connection.executeQuery(query, rs -> {
            if (rs.next()) {
                return rs.getDouble("amount");
            }
            return -1.0;
        }, payID);
    }

    public static LocalDateTime retrieveDate(int payID) throws SQLException{
        DatabaseConnection connection = DatabaseConnection.getInstance();
        String query = "SELECT tried_at FROM payment WHERE paid_reservation = ?";
        return connection.executeQuery(query, rs -> {
            if (rs.next()) {
                return rs.getObject("tried_at", LocalDateTime.class);
            }
            return null;
        }, payID);
    }

}
