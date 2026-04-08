package controllers.buttons;

import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import services.Item;
import views.Components.ContainsItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ArticleSelectAction extends AbstractAction implements ContentObserver {
    private static final List<ContentObserver> observerList = new ArrayList<>();
    private AbstractButton sourceButton;
    public ArticleSelectAction(){
        ContentController.addObserver(this);
    }

    public void deselectArticle(){
        notifyItemSelect(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JToggleButton clickedButton = (JToggleButton) e.getSource();
        ContainsItem containsItem = (ContainsItem) clickedButton;

        if (clickedButton.isSelected()) {
            notifyItemSelect(containsItem.getItem());
        } else {
            notifyItemSelect(null);
        }
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
