package controllers.buttons;

import controllers.display.ContentController;
import controllers.display.ContentObserver;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.ItemCountable;
import services.OpenTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class AddItemAction extends AbstractAction implements ContentObserver, OpenTransactionObserver {
    private Item item;
    private String content;

    public AddItemAction(){
        ArticleSelectAction.addObserver(this);
        OpenTransaction.addObserver(this);
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OpenTransactionManager.getInstance().addItem(new ItemCountable(item.getName(),item.getPrice(),Integer.parseInt(content)));
    }

    //Observer
    @Override
    public void notifyItemSelect(Item item) {
        this.item = item;
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }
}
