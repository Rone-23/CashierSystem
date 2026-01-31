package controllers.buttons;

import controllers.display.ContentController;
import controllers.display.ContentObserver;
import services.Item;
import services.ItemCountable;
import views.Components.ContainsItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ArticleAction extends AbstractAction implements ContentObserver {
    private static final List<ContentObserver> observerList = new ArrayList<>();
    private String content = "1";
    private AbstractButton sourceButton;
    public ArticleAction(){
        ContentController.addObserver(this);
    }

    public void deselectArticle(){
        if(sourceButton !=null){
            sourceButton.setSelected(false);
        }
        sourceButton = null;
        notifyItemSelect(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deselectArticle();
        sourceButton = (JToggleButton) e.getSource();
        sourceButton.setSelected(true);
        ContainsItem containsItem = (ContainsItem) sourceButton;
        String itemName = containsItem.getItemName();
        int itemPrice = containsItem.getItemPrice();
        notifyItemSelect(new ItemCountable(itemName,itemPrice,Integer.parseInt(content)));
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }

    //Observer
    public static void addObserver(ContentObserver contentObserver){observerList.add(contentObserver);}

    public static void removeObserver(ContentObserver contentObserver){observerList.remove(contentObserver);}

    public void notifyItemSelect(Item item) {
        for (ContentObserver observer: observerList){
            observer.notifyItemSelect(item);
        }
    }
}
