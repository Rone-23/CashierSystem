package controllers.buttons;

import controllers.display.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class AddItemAction extends AbstractAction implements ContentObserver, OpenTransactionObserver {
    private Item item;

    public AddItemAction(){
        ArticleAction.addObserver(this);
        OpenTransaction.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(item.getAmount()!=0.0){
            OpenTransactionManager.getInstance().addItem(item);
        }
    }

    //Observer
    @Override
    public void notifyItemSelect(Item item) {
        this.item = item;
    }

}
