package views.Components;

import assets.Colors;
import assets.ThemeManager;
import assets.ThemeObserver;
import controllers.notifications.NotificationController;
import controllers.notifications.NotificationObserver;
import controllers.transaction.OpenTransactionObserver;
import services.OpenTransaction;
import services.Users.CashierObserver;
import services.Users.CashierSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatusBar extends JPanel implements NotificationObserver, ThemeObserver, OpenTransactionObserver, CashierObserver {
    private final JLabel dateTimeLabel = new JLabel();
    private final JLabel notificationLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel infoLabel = new JLabel("", SwingConstants.RIGHT);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy  |  HH:mm:ss");
    private Timer notificationTimer;

    private String cashierId = "---";
    private String status = "---";
    private String transactionId = "---";

    private Boolean isLocked=false;

    public StatusBar() {
        CashierSession.addObserver(this);
        OpenTransaction.addObserver(this);
        NotificationController.addObserver(this);
        setLayout(new BorderLayout());
        setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());
        Font statusFont = assets.Scaler.getFont(0.015, Font.BOLD);
        dateTimeLabel.setFont(statusFont);
        notificationLabel.setFont(statusFont);
        infoLabel.setFont(statusFont);

        updateTime();
        updateInfoLabel();

        add(dateTimeLabel, BorderLayout.WEST);
        add(notificationLabel, BorderLayout.CENTER);
        add(infoLabel, BorderLayout.EAST);

        Timer clockTimer = new Timer(1000, e -> updateTime());
        clockTimer.start();

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);
    }

    private void updateTime() {
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));
    }


    private void updateInfoLabel() {
        infoLabel.setText(String.format("Pokladník: %s  |  Kmeňový zákazník: %s  |  Číslo transakcie: %s", cashierId, status, transactionId));
    }

    public void setCashierId(String cashierId) {
        this.cashierId = (cashierId != null && !cashierId.isEmpty() && !cashierId.equals("-1"))  ? cashierId : "---";
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

    public void setLocked(Boolean status){
        isLocked=status;
        if(isLocked){
                if (notificationTimer != null) notificationTimer.stop();
                notificationLabel.setText("SYSTÉM JE POZASTAVENÝ, ZADAJTE 4 MIESTNY KÓD PRE ODBLOKOVANIE.");
                notificationLabel.setForeground(Color.RED);
            } else {
                notificationLabel.setText("");
                notificationLabel.setForeground(Color.RED);
        }
    }

    private void showNotification(String message, int durationMs, Color color) {
        if (notificationTimer != null && notificationTimer.isRunning()) {
            notificationTimer.stop();
        }

        if (message == null) {
            message = "Neznama chyba";
        }

        notificationLabel.setText(message.toUpperCase());
        notificationLabel.setForeground(color);

        notificationTimer = new Timer(durationMs, _ -> {
            if(!isLocked){
                notificationLabel.setText("");
            }else {
                notificationLabel.setText("SYSTÉM JE POZASTAVENÝ, ZADAJTE 4 MIESTNY KÓD PRE ODBLOKOVANIE.");
            }
        });
        notificationTimer.setRepeats(false);
        notificationTimer.start();
    }

    @Override
    public void updateNotification(String notification, int timeMs, Color color) {
        showNotification(notification, timeMs, color);
    }

    @Override
    public void onThemeChange() {
        setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());

        int borderThickness = Math.max(1, assets.Scaler.getPadding(0.002));
        int vPad = assets.Scaler.getPadding(0.01);
        int hPad = assets.Scaler.getPadding(0.02);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(borderThickness, 0, 0, 0, Colors.BUTTON_LIGHT_BLUE.getColor()),
                new EmptyBorder(vPad, hPad, vPad, hPad)
        ));

        Color standardText = Colors.BLACK_TEXT.getColor();
        dateTimeLabel.setForeground(standardText);
        infoLabel.setForeground(standardText);
        notificationLabel.setForeground(Color.RED);

        repaint();
    }

    public JComponent getNotificationLabel(){return notificationLabel;}

    @Override
    public void onDestroy() {
        setStatus("---");
        setTransactionId("---");
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        setTransactionId(String.valueOf(openTransaction.getTransactionID()));
        setStatus("Nie");
    }

    @Override
    public void onCashierLogin(int cashierId) {
        setCashierId(String.valueOf(cashierId));
    }

}