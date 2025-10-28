package controllers.display;

import controllers.ViewManager;
import controllers.openTransaction.OpenTransactionObserver;
import services.Item;

public class DisplayItemsController implements OpenTransactionObserver {

    @Override
    public void onItemAdd(Item item) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().addItem(item);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().addItem(item);
    }

    @Override
    public void onDestroy() {
        //TODO: create destroy method in DisplayScrollableItems
        //        displayPanel.clearArticleDisplay();
    }
}
