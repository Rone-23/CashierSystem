package controllers.display;

public interface ContentObserver {
    default void notifyContentUpdate(String content){}
}
