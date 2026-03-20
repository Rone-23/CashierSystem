package controllers.transaction;

import controllers.panels.ViewManager;
import services.Item;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class CashBackAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        Item[] returnedItems = OpenTransactionManager.getInstance().getOpenTransaction().getReturnedItems().values().toArray(new Item[0]);
        int openTransactionID = OpenTransactionManager.getInstance().getOpenTransaction().getTransactionID();
        for(Item item : returnedItems){
            try {
                SQL_Connect.getInstance().returnItem(
                        openTransactionID,
                        SQL_Connect.getInstance().getArticleID(item.getName()),
                        item.getAmount()
                        );
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            OpenTransactionManager.getInstance().addPayment(e);
        }

        ViewManager.getInstance().showIdle();
    }
}
