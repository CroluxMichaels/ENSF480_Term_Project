package Business;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.sql.SQLException;

import Business.PaymentController;
import Data.DatabaseConnection;

public class Payment {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private int reservationID;
    private double amount;

    private String status;
    private LocalDateTime paymentDate;
    private int payID;

    public Payment(String cardNumber, String expiryDate, 
        String cvv, int reservationID, double amount){
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.reservationID = reservationID;
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
    }

    public boolean checkCardNumber(){
        if (cardNumber.matches("\\d{16}")) return true;
        else return false;
    }

    public boolean checkCVV(){
        if (cvv.matches("\\d{3}")) return true;
        else return false;
    }

    public boolean checkExpiryDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/uu")
        .withResolverStyle(ResolverStyle.STRICT);

        try{
            YearMonth exp = YearMonth.parse(expiryDate, formatter);
            YearMonth cur = YearMonth.now();
            if (!exp.isBefore(cur)) return true;
            else return false;
        } catch (DateTimeParseException e){
            return false;
        }
    }

    public boolean checkAmount(){
        if (amount >= 0) return true;
        else return false;
    }

    public boolean mainChecker(){
        return (checkCardNumber() && checkCVV() 
        && checkExpiryDate() && checkAmount());
    }

    public void initStatus(){
        if (mainChecker()) status = "APPROVED";
        else status = "DECLINED";
        return;
    }

    public int processPayment() throws SQLException{
        payID = PaymentController.savePayment(this);
        return payID;
    }

    public static String retrieveOutcome(int payID) throws SQLException{
        return PaymentController.retrieveOutcome(payID);
    }

    public static double retrieveAmount(int payID) throws SQLException{
        return PaymentController.retrieveAmount(payID);
    }

    public static LocalDateTime retrieveDate(int payID) throws SQLException{
        return PaymentController.retrieveDate(payID);
    }

    // Getters and setters

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getPayID() {
        return payID;
    }

    public void setpayID(int payID) {
        this.payID = payID;
    }

}
