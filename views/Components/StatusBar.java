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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy  |  HH:mm:ss");
    private Timer notificationTimer;

    public StatusBar() {
        NotificationController.addObserver(this);
        setLayout(new BorderLayout());
        setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Colors.BUTTON_LIGHT_BLUE.getColor()),
                new EmptyBorder(10, 20, 10, 20)
        ));

        dateTimeLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        dateTimeLabel.setForeground(Colors.BLACK_TEXT.getColor());
        updateTime();

        notificationLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        notificationLabel.setForeground(new Color(211, 47, 47));

        add(dateTimeLabel, BorderLayout.WEST);
        add(notificationLabel, BorderLayout.CENTER);

        Timer clockTimer = new Timer(1000, e -> updateTime());
        clockTimer.start();
    }

    private void updateTime() {
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));
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
        showNotification(notification,timeMs);
    }
}