package controllers.buttons;

import controllers.notifications.NotificationController;
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
        } catch (SQLException ex) {
            NotificationController.notifyObservers(ex.toString(),5000);
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        insertedTransactionID = Integer.parseInt(content);
    }
}
