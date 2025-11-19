package controllers.buttons;

import controllers.display.ContentController;
import controllers.display.ContentObserver;
import services.Item;
import services.ItemCountable;
import views.Components.ArticleButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ArticleAction extends AbstractAction implements ContentObserver {
    private static final List<ContentObserver> observerList = new ArrayList<>();
    private String content = "1";
    private ArticleButton articleButton;
    public ArticleAction(){
        ContentController.addObserver(this);
    }

    public void deselectArticle(){
        try {
            articleButton.setSelected(false);
        } catch (NullPointerException ignored) {
        }
        articleButton = null;
        notifyItemSelect(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            articleButton.setSelected(false);
        } catch (NullPointerException ignored) {
        }
        articleButton = (ArticleButton) e.getSource();
        articleButton.setSelected(true);
        String itemName = articleButton.getItemName();
        double itemPrice = articleButton.getItemPrice();
        notifyItemSelect(new ItemCountable(itemName,itemPrice,Double.parseDouble(content)));
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
