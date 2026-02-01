package controllers.buttons;

import controllers.display.ContentController;
import controllers.display.ContentObserver;
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
        notifyItemSelect(containsItem.getItem());
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
