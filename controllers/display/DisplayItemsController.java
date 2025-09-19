package controllers.display;

import services.Item;
import views.leftPanel.DisplayPanel;

public class DisplayItemsController implements OpenTransactionObserver {
    DisplayPanel displayPanel;
    public DisplayItemsController(DisplayPanel displayPanel){
        this.displayPanel = displayPanel;
    }

    @Override
    public void onItemAdd(Item item) {
        this.displayPanel.updateArticleDisplay(item.getName());
    }

    @Override
    public void onDestroy() {
        this.displayPanel.clearArticleDisplay();
    }
}
