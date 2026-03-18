package controllers.buttons;

import controllers.transaction.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveItemAction extends AbstractAction implements ContentObserver, OpenTransactionObserver {
    private Item item;

    RemoveItemAction(){
        ArticleSelectAction.addObserver(this);
        OpenTransaction.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OpenTransactionManager.getInstance().removeItem(item);
    }

    //Observer
    @Override
    public void notifyItemSelect(Item item) {
        this.item = item;
    }
}