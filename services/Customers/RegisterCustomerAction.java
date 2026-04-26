package services.Customers;

import assets.Colors;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegisterCustomerAction extends AbstractAction implements ContentObserver {
    private String content;

    public RegisterCustomerAction() {
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (content == null || !content.matches("\\d{7}")) {
            NotificationController.notifyObservers("Karta musí obsahovať presne 7 čísel!", 4000, Colors.YELLOW.getColor());
            ContentController.clearContent();
            return;
        }

        try {
            SQL_Connect.getInstance().createNewCustomerCard(Integer.parseInt(content), "Držiteľ karty: " + content);
            NotificationController.notifyObservers("Zákaznícka karta " + content + " úspešne vytvorená!", 4000);

            ContentController.clearContent();
            ContentController.removeObserver(this);

            ViewManager.getInstance().returnToDefault();

        } catch (SQLException ex) {
            NotificationController.notifyObservers(ex.getMessage(), 5000);
            ContentController.clearContent();
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }
}