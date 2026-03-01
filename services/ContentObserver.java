package services;

public interface ContentObserver {
    default void notifyContentUpdate(String content){}
    default void notifyItemSelect(Item item){}
}
