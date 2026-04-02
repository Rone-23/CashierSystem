package controllers.notifications;

import java.awt.*;

public interface NotificationObserver {
    void updateNotification(String notification, int timeMs, Color color);
}
