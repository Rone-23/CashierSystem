package controllers.display;

import controllers.buttons.ArticleSelectAction;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;

import javax.swing.*;

public class DisplayItemController implements OpenTransactionObserver {
    private final ArticleSelectAction articleSelectAction;

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
            int returnedAmount = OpenTransactionManager.getInstance().getOpenTransaction().getReturnedItems().get(item.getName()).getAmount();
            jToggleButtonReturn = ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().addItem(
                    item,
                    returnedAmount
            );
        } catch (Exception e) {
            jToggleButtonReturn = ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().addItem(item);
        }
        if(jToggleButtonReturn != null){
            jToggleButtonReturn.addActionListener(articleSelectAction);
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
    public void onTransactionUpdate() {
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().repaint();
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().repaint();
        ViewManager.getInstance().getDuringReturn().getDisplayScrollableItems().repaint();
    }
}
