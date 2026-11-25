package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
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
        JFrame frame = new JFrame("Login Window");
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
        char[] password = passwordField.getPassword();
        LoginController loginController = new LoginController();
        statusLabel.setText("The Entered Username and Password combination is incorrect");
        loginController.logIn(username, Arrays.toString(password));
        // Call login controller here
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
