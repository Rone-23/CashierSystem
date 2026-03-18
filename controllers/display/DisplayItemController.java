package controllers.display;

import controllers.buttons.ArticleSelectAction;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;

import javax.swing.*;
import java.sql.SQLException;

public class DisplayItemController implements OpenTransactionObserver {
    private ArticleSelectAction articleSelectAction;
    private int openTransactionId;

    public DisplayItemController(){
        articleSelectAction = new ArticleSelectAction();
    }

    @Override
    public void onItemAdd(Item item) {
        JToggleButton jToggleButtonArticles;
        JToggleButton jToggleButtonRegister;
        JToggleButton jToggleButtonReturn;
            jToggleButtonArticles = ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().addItem(item);
            jToggleButtonRegister = ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().addItem(item);
        try {
            jToggleButtonReturn = ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().addItem(
                    item,
                    SQL_Connect.getInstance().getReturnedAmount(SQL_Connect.getInstance().getArticleID(item.getName()), openTransactionId)
                    );
            if(jToggleButtonReturn != null){
                jToggleButtonReturn.addActionListener(articleSelectAction);
            }
        } catch (SQLException e) {
            NotificationController.notifyObservers(e.toString(),5000);
        }
        if(jToggleButtonArticles != null && jToggleButtonRegister != null) {
            jToggleButtonArticles.addActionListener(articleSelectAction);
            jToggleButtonRegister.addActionListener(articleSelectAction);
        }
    }

    @Override
    public void onItemRemove(Item item) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().removeItem(item);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().removeItem(item);
        ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().removeItem(item);
    }

    @Override
    public void onDestroy() {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().clear();
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().clear();
        ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().clear();

    }

    @Override
    public void onAddedPayment(int toPayLeft, String typeOfPayment, int addedAmount) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().addPayment(typeOfPayment, addedAmount);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().addPayment(typeOfPayment, addedAmount);
        ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().addPayment(typeOfPayment, addedAmount);
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        openTransactionId = openTransaction.getTransactionID();
    }
}
