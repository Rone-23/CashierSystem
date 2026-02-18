package controllers.notifications;

public interface NotificationObserver {
    void updateNotification(String notification, int timeMs);
}
