package controllers.display;

import services.Item;
import viewsRework.Components.DisplayScrollableItems;

public class DisplayItemsController implements OpenTransactionObserver {
    DisplayScrollableItems displayPanel;
    public DisplayItemsController(DisplayScrollableItems display){
        this.displayPanel = display;
    }

    @Override
    public void onItemAdd(Item item) {
        displayPanel.addItem(item);
    }

    @Override
    public void onDestroy() {
        //TODO: create destroy method in DisplayScrollableItems
        //        displayPanel.clearArticleDisplay();
    }
}
