package controllers.display;

import controllers.buttons.ArticleSelectAction;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;

import javax.swing.*;

public class DisplayItemController implements OpenTransactionObserver {
    ArticleSelectAction articleSelectAction;

    public DisplayItemController(){
        articleSelectAction = new ArticleSelectAction();
    }

    @Override
    public void onItemAdd(Item item) {
        JToggleButton jToggleButtonArticles = ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().addItem(item);
        JToggleButton jToggleButtonRegister = ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().addItem(item);
        if(jToggleButtonArticles != null && jToggleButtonRegister != null) {
            jToggleButtonArticles.addActionListener(articleSelectAction);
            jToggleButtonRegister.addActionListener(articleSelectAction);
        }
    }

    @Override
    public void onItemRemove(Item item) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().removeItem(item);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().removeItem(item);
    }

    @Override
    public void onDestroy() {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().clear();
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().clear();

    }

    @Override
    public void onAddedPayment(int toPayLeft, String typeOfPayment, int addedAmount) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().addPayment(typeOfPayment, addedAmount);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().addPayment(typeOfPayment, addedAmount);
    }
}
