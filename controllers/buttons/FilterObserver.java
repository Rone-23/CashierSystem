package controllers.buttons;

public interface FilterObserver {
    default void updateMainFilter(String filterKeyword){}
    default void updateSecondaryFilter(String filterKeyword){}
}
