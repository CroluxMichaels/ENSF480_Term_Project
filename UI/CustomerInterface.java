package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerInterface implements ActionListener {
    private JFrame frame;
    private JButton logoutButton;
    private JLabel statusLabel;

    public void setupCustomerUI(String username) {
        frame = new JFrame("Customer Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 30f));
        JButton searchButton = new JButton("Search Flights");
        JButton bookingsButton = new JButton("My Bookings");
        logoutButton = new JButton("Log Out");
        statusLabel = new JLabel(" ", SwingConstants.CENTER);

        searchButton.addActionListener(this);
        bookingsButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(5, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 36, 24, 36));
        panel.add(welcomeLabel);
        panel.add(centerButton(searchButton));
        panel.add(centerButton(bookingsButton));
        panel.add(centerButton(logoutButton));
        panel.add(statusLabel);

        // Center the menu at its preferred size when the window grows.
        JPanel background = new JPanel(new GridBagLayout());
        background.add(panel);
        frame.add(background);
        frame.setMinimumSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel centerButton(JButton button) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        row.add(button);
        return row;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == logoutButton) {
            LoginInterface login = new LoginInterface();
            login.SetupLoginUI();
            frame.dispose();
        } else {
            statusLabel.setText(event.getActionCommand() + " selected.");
        }
    }
}
