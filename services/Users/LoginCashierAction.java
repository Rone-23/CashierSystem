package services.Users;

import assets.Colors;
import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import services.SQL_Connect;
import controllers.notifications.NotificationController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginCashierAction implements ActionListener, ContentObserver {
    private String loginCredentials;

    public LoginCashierAction() {
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (loginCredentials == null || loginCredentials.isEmpty() || loginCredentials.equals(String.valueOf(0))) {
            NotificationController.notifyObservers("Zadajte ID používateľa.", 3000, Colors.YELLOW.getColor());
            return;
        }

        try {
            int cashierId = Integer.parseInt(loginCredentials);
            boolean isValid = SQL_Connect.getInstance().validateCashier(cashierId);

            if (isValid) {
                CashierSession.login(cashierId);
                loginCredentials=null;
                ViewManager.getInstance().getStatusBar().setLocked(false);
                ViewManager.getInstance().showIdle();
                ViewManager.getInstance().returnToDefault();
                DisplayDispatcher.activeDisplayForAmount();
            } else {
                NotificationController.notifyObservers("Nesprávne ID. Používateľ neexistuje.", 3000);
                loginCredentials=null;
            }
        } catch (SQLException ex) {
            NotificationController.notifyObservers("Chyba databázy: " + ex.getMessage(), 5000);
        }
        ContentController.clearContent();
    }

    @Override
    public void notifyContentUpdate(String content) {
        loginCredentials = content;
    }
}