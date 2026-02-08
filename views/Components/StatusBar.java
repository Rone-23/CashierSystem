package views.Components;

import assets.Colors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatusBar extends JPanel {
    private final JLabel dateTimeLabel = new JLabel();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy  |  HH:mm:ss");

    public StatusBar() {
        setLayout(new BorderLayout());
        setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Colors.BUTTON_LIGHT_BLUE.getColor()),
                new EmptyBorder(10, 20, 10, 20)
        ));

        dateTimeLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        dateTimeLabel.setForeground(Colors.BLACK_TEXT.getColor());

        updateTime();
        add(dateTimeLabel, BorderLayout.WEST);

        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }

    private void updateTime() {
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));
    }
}