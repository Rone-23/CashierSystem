package controllers.buttons;

import controllers.display.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveItemAction extends AbstractAction implements ContentObserver, OpenTransactionObserver {
    private OpenTransaction openTransaction;
    private Item item;

    RemoveItemAction(){
        ArticleSelectAction.addObserver(this);
        OpenTransaction.addObserver(this);
        openTransaction  = OpenTransactionManager.getInstance().getOpenTransaction();
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

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        this.openTransaction = openTransaction;
    }

}