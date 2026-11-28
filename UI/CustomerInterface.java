package UI;

import Data.DatabaseConnection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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
        bookingsButton.addActionListener(event -> showBookings(username));
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

        JTextPane promotions = new JTextPane();
        showPromotionText(promotions, "Loading promotions…");
        promotions.setEditable(false);
        promotions.setFont(statusLabel.getFont());
        promotions.setMargin(new Insets(6, 10, 6, 10));
        JScrollPane offersBox = new JScrollPane(promotions);
        offersBox.setPreferredSize(new Dimension(380, 120));
        Color promotionBackground = new Color(220, 238, 252);
        promotions.setBackground(promotionBackground);
        offersBox.getViewport().setBackground(promotionBackground);
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

    private void showPromotionText(JTextPane promotions, String text) {
        promotions.setText(text);
        SimpleAttributeSet alignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignment, StyleConstants.ALIGN_CENTER);
        promotions.getStyledDocument().setParagraphAttributes(0, text.length(), alignment, false);
        promotions.setCaretPosition(0);
    }

    private void loadPromotions(JTextPane promotions) {
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws java.sql.SQLException {
                return DatabaseConnection.getInstance().getActivePromotions();
            }

            @Override
            protected void done() {
                try {
                    List<String> offers = get();
                    showPromotionText(promotions, offers.isEmpty()
                            ? "No active promotions right now."
                            : String.join("\n\n", offers));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    showPromotionText(promotions, "Loading promotions was interrupted.");
                } catch (ExecutionException e) {
                    showPromotionText(promotions, "Unable to load promotions.");
                    e.getCause().printStackTrace();
                }
            }
        }.execute();
    }

    private void showBookings(String username) {
        JDialog dialog = new JDialog(frame, "My Bookings", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("My Bookings");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        JTextArea details = new JTextArea("Loading your bookings…", 16, 44);
        details.setEditable(false);
        details.setFont(statusLabel.getFont());
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setMargin(new Insets(12, 14, 12, 14));

        JButton back = new JButton("Back to Customer Menu");
        back.addActionListener(event -> dialog.dispose());
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 12, 20));
        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(details), BorderLayout.CENTER);
        panel.add(centerButton(back), BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws java.sql.SQLException {
                return DatabaseConnection.getInstance().getCustomerBookings(username);
            }

            @Override
            protected void done() {
                try {
                    List<String> bookings = get();
                    details.setText(bookings.isEmpty() ? "You have no bookings yet."
                            : String.join("\n\n", bookings));
                    details.setCaretPosition(0);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    details.setText("Loading bookings was interrupted.");
                } catch (ExecutionException e) {
                    details.setText("Unable to load your bookings. Close this window and try again.");
                    e.getCause().printStackTrace();
                }
            }
        }.execute();
        dialog.setVisible(true);
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



