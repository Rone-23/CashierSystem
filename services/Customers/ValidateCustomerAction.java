package services.Customers;

import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ValidateCustomerAction extends AbstractAction implements ContentObserver {
    private String content;
    public ValidateCustomerAction(){
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(OpenTransactionManager.getInstance().getOpenTransaction().getCustomerID()!=-1){
            NotificationController.notifyObservers("Karta je už načítaná.", 4000);
            ContentController.clearContent();
            return;
        }
        try {
            int customerID = Integer.parseInt(content);
            if(SQL_Connect.getInstance().getCustomerIdByCard(customerID)!=-1){
                OpenTransactionManager.getInstance().getOpenTransaction().setCustomerID(customerID);
                ViewManager.getInstance().getStatusBar().setStatus("Áno");
                NotificationController.notifyObservers("Karta úspešne načítaná!", 4000);
            }else{
                NotificationController.notifyObservers("Karta nebola rozpoznaná!", 4000);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }finally {
            ContentController.clearContent();
        }
    }

    @Override
    public void notifyContentUpdate(String content){
        this.content = content;
    }
}
