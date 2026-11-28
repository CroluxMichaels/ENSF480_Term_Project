package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Business.Customer;
import Business.LoginController;

public class LoginInterface implements ActionListener, MouseListener {
    private JFrame frame;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public void SetupLoginUI(){
        frame = new JFrame("Login Window");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        userField = new JTextField("Enter username here", 15);
        userField.addMouseListener(this);
        passwordField = new JPasswordField("Sample Password", 15);
        passwordField.addMouseListener(this);
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(userField);
        userPanel.setLayout(new FlowLayout());

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, userPanel.getPreferredSize().height));
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordPanel.getPreferredSize().height));
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(userPanel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topPanel.add(passwordPanel);
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(statusLabel);
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(loginButton);
        topPanel.add(Box.createVerticalGlue());

        frame.add(topPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        char[] passwordChars = passwordField.getPassword();
        statusLabel.setText("Authenticating...");

        try {
            LoginController loginController = new LoginController();
            String password = new String(passwordChars);
            String role = loginController.logIn(username, password);

            if (role.equals("N/A")){
                statusLabel.setText("Incorrect username or password, or inactive account.");
            }
            else if (role.equals("customer")) {
                statusLabel.setText("Customer login successful!");
                CustomerInterface customerInterface = new CustomerInterface();
                customerInterface.setupCustomerUI(username);
                frame.dispose();
            }
            else if (role.equals("agent")) {
                statusLabel.setText("Agent login successful!");
                //AgentInterface agentInterface = new AgentInterface();
            }
            else if (role.equals("admin")) {
                statusLabel.setText("Admin login successful!");
                //AdminInterface adminInterface = new AdminInterface();
            }
        } catch (SQLException exception) {
            statusLabel.setText("Unable to connect to the database.");

            exception.printStackTrace();
        } finally {
            Arrays.fill(passwordChars, '\0');
            passwordField.setText("");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(userField)){
            userField.setText("");
        }
        if (e.getSource().equals(passwordField)){
            passwordField.setText("");
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

    public static void main(String[] args){
        LoginInterface loginInterface = new LoginInterface();
        loginInterface.SetupLoginUI();
    }
}
