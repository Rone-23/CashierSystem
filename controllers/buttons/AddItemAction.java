package controllers.buttons;

import controllers.transaction.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.ItemCountable;
import services.OpenTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class AddItemAction extends AbstractAction implements ContentObserver, OpenTransactionObserver {
    private Item item;

    public AddItemAction(){
        ArticleSelectAction.addObserver(this);
        OpenTransaction.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(item!=null){
            ItemCountable itemToAdd = (ItemCountable) item.clone();
            OpenTransactionManager.getInstance().addItem(itemToAdd);
        }
    }

    //Observer
    @Override
    public void notifyItemSelect(Item item) {
        this.item = item;
    }
}
