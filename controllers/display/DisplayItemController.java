package controllers.display;

import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;

public class DisplayItemController implements OpenTransactionObserver {

    @Override
    public void onItemAdd(Item item) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().addItem(item);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().addItem(item);
    }

    @Override
    public void onItemRemove(Item item) {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().removeItem(item);
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().removeItem(item);
    }

    @Override
    public void onDestroy() {
        ViewManager.getInstance().getDuringArticles().getDisplayScrollableItems().clear();
        ViewManager.getInstance().getDuringRegister().getDisplayScrollableItems().clear();

    }
}
