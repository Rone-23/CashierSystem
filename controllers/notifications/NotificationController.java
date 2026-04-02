package controllers.notifications;

import java.awt.*;
import java.util.ArrayList;

public class NotificationController {
    private static final ArrayList<NotificationObserver> observerList = new ArrayList<>();

    public static void notifyObservers(String notification, int timeMs){
        for(NotificationObserver o : observerList.toArray(new NotificationObserver[0])){
            o.updateNotification(notification,timeMs, Color.RED);
        }
        System.out.printf(notification+"\n");
    }

    public static void notifyObservers(String notification, int timeMs, Color color){
        for(NotificationObserver o : observerList.toArray(new NotificationObserver[0])){
            o.updateNotification(notification,timeMs, color);
        }
        System.out.printf(notification+"\n");
    }

    public static void addObserver(NotificationObserver observer){
        observerList.add(observer);
    }
    public static void removeObserver(NotificationObserver observer){
        observerList.remove(observer);
    }
}
