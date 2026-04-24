package services.Customers;

import assets.Colors;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ValidateCustomerAction extends AbstractAction implements ContentObserver {
    private String content;
    private static final List<CustomerCardObserver> observerList = new ArrayList<>();
    public ValidateCustomerAction(){
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(content.isEmpty()) return;

        if(OpenTransactionManager.getInstance().getOpenTransaction().getCustomerID()!=-1){
            NotificationController.notifyObservers("Karta už bola načítaná.", 4000, Colors.YELLOW.getColor());
            ContentController.clearContent();
            return;
        }
        try {
            int customerID = Integer.parseInt(content);
            if(SQL_Connect.getInstance().getCustomerIdByCard(customerID)!=-1){
                ViewManager.getInstance().getStatusBar().setStatus("Áno");
                NotificationController.notifyObservers("Karta úspešne načítaná!", 4000, Colors.GREEN.getColor());
                for(CustomerCardObserver o : observerList){
                    o.onCardValidation(customerID);
                }
            }else{
                NotificationController.notifyObservers("Karta nebola rozpoznaná!", 4000);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }finally {
            ContentController.clearContent();
        }
    }

    //Observer
    public static void addObserver(CustomerCardObserver o){
        observerList.add(o);
    }

    public static void removeObserver(CustomerCardObserver o){
        observerList.remove(o);
    }

    @Override
    public void notifyContentUpdate(String content){
        this.content = content;
    }
}
