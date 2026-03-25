package views.Components;

import assets.Colors;
import controllers.notifications.NotificationController;
import controllers.notifications.NotificationObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatusBar extends JPanel implements NotificationObserver {
    private final JLabel dateTimeLabel = new JLabel();
    private final JLabel notificationLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel infoLabel = new JLabel("", SwingConstants.RIGHT); // New label for the right side

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy  |  HH:mm:ss");
    private Timer notificationTimer;

    // State variables for the right-side info
    private String cashierId = "---";
    private String status = "---";
    private String transactionId = "---";

    public StatusBar() {
        NotificationController.addObserver(this);
        setLayout(new BorderLayout());
        setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Colors.BUTTON_LIGHT_BLUE.getColor()),
                new EmptyBorder(10, 20, 10, 20)
        ));

        // Left side: Date & Time
        dateTimeLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        dateTimeLabel.setForeground(Colors.BLACK_TEXT.getColor());
        updateTime();

        // Center: Notifications
        notificationLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        notificationLabel.setForeground(new Color(211, 47, 47));

        // Right side: System Info (Cashier, Status, Transaction)
        infoLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        infoLabel.setForeground(Colors.BLACK_TEXT.getColor());
        updateInfoLabel(); // Initialize text

        add(dateTimeLabel, BorderLayout.WEST);
        add(notificationLabel, BorderLayout.CENTER);
        add(infoLabel, BorderLayout.EAST);

        Timer clockTimer = new Timer(1000, e -> updateTime());
        clockTimer.start();
    }

    private void updateTime() {
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));
    }


    private void updateInfoLabel() {
        infoLabel.setText(String.format("Pokladník: %s  |  Kmeňový zákazník: %s  |  Číslo transakcie: %s", cashierId, status, transactionId));
    }

    public void setCashierId(String cashierId) {
        this.cashierId = (cashierId != null && !cashierId.isEmpty()) ? cashierId : "---";
        updateInfoLabel();
    }

    public void setStatus(String status) {
        this.status = (status != null && !status.isEmpty()) ? status : "---";
        updateInfoLabel();
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = (transactionId != null && !transactionId.isEmpty()) ? transactionId : "---";
        updateInfoLabel();
    }

    public void setSystemInfo(String cashierId, String status, String transactionId) {
        this.cashierId = (cashierId != null && !cashierId.isEmpty()) ? cashierId : "---";
        this.status = (status != null && !status.isEmpty()) ? status : "---";
        this.transactionId = (transactionId != null && !transactionId.isEmpty()) ? transactionId : "---";
        updateInfoLabel();
    }

    private void showNotification(String message, int durationMs) {
        if (notificationTimer != null && notificationTimer.isRunning()) {
            notificationTimer.stop();
        }

        notificationLabel.setText(message.toUpperCase());

        notificationTimer = new Timer(durationMs, _ -> {
            notificationLabel.setText("");
        });
        notificationTimer.setRepeats(false);
        notificationTimer.start();
    }

    @Override
    public void updateNotification(String notification, int timeMs) {
        showNotification(notification, timeMs);
    }
}