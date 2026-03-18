package controllers.transaction;

import services.Item;

public interface ContentObserver {
    default void notifyContentUpdate(String content){}
    default void notifyItemSelect(Item item){}
}
