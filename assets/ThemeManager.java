package assets;

import java.util.ArrayList;
import java.util.List;

public class ThemeManager {
    private static final ThemeManager instance = new ThemeManager();
    private final List<ThemeObserver> observers = new ArrayList<>();

    private ThemeManager() {}

    public static ThemeManager getInstance() {
        return instance;
    }

    public void addObserver(ThemeObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    public void removeObserver(ThemeObserver observer) {
        observers.remove(observer);
    }

    public boolean toggleTheme() {
        Colors.switchState();
        for (ThemeObserver observer : observers) {
            observer.onThemeChange();
        }
        return Colors.isDarkMode();
    }
}