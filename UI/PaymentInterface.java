package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import Business.Payment;

public class PaymentInterface implements ActionListener, MouseListener {
    private JFrame frame;
    private JLabel cardNumberLabel;
    private JLabel expiryLabel;
    private JLabel cvvLabel;
    private JLabel reservationIDLabel;
    private JLabel amountLabel;
    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;
    private JTextField reservationIDField;
    private JTextField amountField;
    private JButton payButton;
    private JLabel statusLabel;

    private boolean firstClickNumber;
    private boolean firstClickExpiry;
    private boolean firstClickCVV;

    public void setupPayUI(){
        frame = new JFrame("Pay Window");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reservationIDLabel = new JLabel("Reservation Number");
        amountLabel = new JLabel("Payment Amount (CAD$)");
        cardNumberLabel = new JLabel("Card Number");
        expiryLabel = new JLabel("Expiry Date");
        cvvLabel = new JLabel("CVV");

        reservationIDField = new JTextField(5);
        amountField = new JTextField(10);
        cardNumberField = new JTextField("Enter 16-digit card number", 20);
        cardNumberField.addMouseListener(this);
        expiryField = new JTextField("MM/YY", 5);
        expiryField.addMouseListener(this);
        cvvField = new JTextField("Enter 3-digit CVV", 10);
        cvvField.addMouseListener(this);
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel reservationIDPanel = new JPanel();
        reservationIDPanel.add(reservationIDLabel);
        reservationIDPanel.add(reservationIDField);
        reservationIDPanel.setLayout(new FlowLayout());

        JPanel amountPanel = new JPanel();
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        amountPanel.setLayout(new FlowLayout());

        JPanel cardNumberPanel = new JPanel();
        cardNumberPanel.add(cardNumberLabel);
        cardNumberPanel.add(cardNumberField);
        cardNumberPanel.setLayout(new FlowLayout());

        JPanel expiryPanel = new JPanel();
        expiryPanel.add(expiryLabel);
        expiryPanel.add(expiryField);
        expiryPanel.setLayout(new FlowLayout());

        JPanel cvvPanel = new JPanel();
        cvvPanel.add(cvvLabel);
        cvvPanel.add(cvvField);
        cvvPanel.setLayout(new FlowLayout());

        payButton = new JButton("PAY");
        payButton.addActionListener(this);
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        reservationIDPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, reservationIDPanel.getPreferredSize().height));
        amountPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, amountPanel.getPreferredSize().height));
        cardNumberPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, cardNumberPanel.getPreferredSize().height));
        expiryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, expiryPanel.getPreferredSize().height));
        cvvPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, cvvPanel.getPreferredSize().height));
        
        fieldPanel.add(Box.createVerticalGlue());
        fieldPanel.add(reservationIDPanel);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        fieldPanel.add(amountPanel);
        fieldPanel.add(Box.createVerticalGlue());
        fieldPanel.add(cardNumberPanel);
        fieldPanel.add(Box.createVerticalGlue());
        fieldPanel.add(expiryPanel);
        fieldPanel.add(Box.createVerticalGlue());
        fieldPanel.add(cvvPanel);
        fieldPanel.add(Box.createVerticalGlue());
        fieldPanel.add(statusLabel);
        fieldPanel.add(Box.createVerticalGlue());
        fieldPanel.add(payButton);
        fieldPanel.add(Box.createVerticalGlue());

        frame.add(fieldPanel);
        frame.setVisible(true);

    firstClickNumber = false;
    firstClickExpiry = false;
    firstClickCVV = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryField.getText();
        String cvv = cvvField.getText();
        int reservationID = Integer.parseInt(reservationIDField.getText());
        double amount = Double.parseDouble(amountField.getText());
        statusLabel.setText("Processing Payment...");

        try {
            Payment payment = new Payment(cardNumber, expiryDate, cvv, reservationID, amount);
            String outcome = Payment.retrieveOutcome(payment.processPayment());

            if (outcome.equals("APPROVED")){
                String payID = String.valueOf(payment.getPayID());
                statusLabel.setText("Payment successfully processed - Payment ID: " + payID);
                // Go to next page
            }
            else{
                statusLabel.setText("Payment rejected");
            }
        } catch (SQLException exception) {
            statusLabel.setText("Unable to connect to the database.");
            exception.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(cardNumberField) && (!firstClickNumber)){
            cardNumberField.setText("");
            firstClickNumber = true;
        }
        if (e.getSource().equals(expiryField) && (!firstClickExpiry)){
            expiryField.setText("");
            firstClickExpiry = true;
        }
        if (e.getSource().equals(cvvField) && (!firstClickCVV)){
            cvvField.setText("");
            firstClickCVV = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not used
    }

    public static void main(String[] args) {
        PaymentInterface inter = new PaymentInterface();
        inter.setupPayUI();
    }
}
