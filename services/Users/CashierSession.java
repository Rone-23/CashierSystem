package services.Users;


import java.util.ArrayList;
import java.util.List;

public class CashierSession {
    private static int currentCashierId = -1;
    private static final List<CashierObserver> observers = new ArrayList<>();

    public static void addObserver(CashierObserver obs) {
        observers.add(obs);
    }

    public static void removeObserver(CashierObserver obs) {
        observers.remove(obs);
    }

    public static void login(int cashierId) {
        currentCashierId = cashierId;
        for (CashierObserver obs : observers) {
            obs.onCashierLogin(cashierId);
        }

    }

    public static int getCurrentCashierId() {
        return currentCashierId;
    }
}