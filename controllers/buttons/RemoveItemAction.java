package controllers.buttons;

import controllers.display.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import services.Item;
import services.OpenTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveItemAction extends AbstractAction implements ContentObserver {
    private final OpenTransaction openTransaction = OpenTransactionManager.getInstance().getOpenTransaction();
    private Item item;

    RemoveItemAction(){
        ArticleAction.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openTransaction.removeItem(item);
    }

    //Observer
    @Override
    public void notifyItemSelect(Item item) {
        this.item = item;
    }

}