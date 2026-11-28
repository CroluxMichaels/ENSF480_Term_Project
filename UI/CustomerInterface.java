package UI;

import Data.DatabaseConnection;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
        JPanel menu = new JPanel(new BorderLayout());
        menu.add(panel, BorderLayout.NORTH);

        JTextArea promotions = new JTextArea("Loading promotions…", 6, 32);
        promotions.setEditable(false);
        promotions.setLineWrap(true);
        promotions.setWrapStyleWord(true);
        promotions.setFont(statusLabel.getFont());
        promotions.setMargin(new Insets(6, 10, 6, 10));
        JScrollPane offersBox = new JScrollPane(promotions);
        promotions.setBackground(searchButton.getBackground());
        offersBox.getViewport().setBackground(searchButton.getBackground());
        offersBox.setBorder(BorderFactory.createLineBorder(UIManager.getColor("controlShadow")));

        JLabel heading = new JLabel("Current promotions");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 16f));
        JPanel promotionsPanel = new JPanel(new BorderLayout(0, 6));
        promotionsPanel.add(heading, BorderLayout.NORTH);
        promotionsPanel.add(offersBox, BorderLayout.CENTER);
        menu.add(promotionsPanel, BorderLayout.SOUTH);
        background.add(menu);
        frame.add(background);
        frame.setMinimumSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        loadPromotions(promotions);
    }

    private void loadPromotions(JTextArea promotions) {
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws java.sql.SQLException {
                return DatabaseConnection.getInstance().getActivePromotions();
            }

            @Override
            protected void done() {
                try {
                    List<String> offers = get();
                    promotions.setText(offers.isEmpty()
                            ? "No active promotions right now."
                            : String.join("\n\n", offers));
                    promotions.setCaretPosition(0);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    promotions.setText("Loading promotions was interrupted.");
                } catch (ExecutionException e) {
                    promotions.setText("Unable to load promotions.");
                    e.getCause().printStackTrace();
                }
            }
        }.execute();
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
