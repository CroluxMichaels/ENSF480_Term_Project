package UI;

import javax.swing.*;
import java.awt.*;

public class LoginInterface {
    private JFrame frame;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userField;
    private JPasswordField passwordField;

    public void SetupLoginUI(){
        JFrame frame = new JFrame("Login Window");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        userLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        userField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());
        userPanel.add(userLabel);
        userPanel.add(userField);
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        frame.add(userPanel, BorderLayout.NORTH);
        frame.add(passwordPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        LoginInterface loginInterface = new LoginInterface();
        loginInterface.SetupLoginUI();
    }
}
