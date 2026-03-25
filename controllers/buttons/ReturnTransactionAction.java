package controllers.buttons;

import controllers.display.DisplayDispatcher;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ReturnTransactionAction extends AbstractAction implements ContentObserver {
    private int insertedTransactionID;
    public ReturnTransactionAction(){
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
            OpenTransactionManager.getInstance().loadHistoricalTransaction(
                    insertedTransactionID,
                    SQL_Connect.getInstance().getDateOfTransaction(insertedTransactionID),
                    SQL_Connect.getInstance().getAllArticlesFromPastTransaction(insertedTransactionID)
            );
            ViewManager.getInstance().showReturnTransaction();
            DisplayDispatcher.activeDisplayForAmount();
        } catch (SQLException | IllegalStateException ex) {
            NotificationController.notifyObservers(ex.getMessage(),5000);
            ContentController.clearContent();
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        insertedTransactionID = Integer.parseInt(content);
    }
}
