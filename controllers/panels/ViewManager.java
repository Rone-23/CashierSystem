package controllers.panels;

import javax.swing.*;
import java.awt.*;

import views.panels.DuringRegister;
import views.panels.DuringArticles;

public class ViewManager {
    private static ViewManager instance;

    private final JFrame mainFrame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final DuringRegister duringRegister;
    private final DuringArticles duringArticles;

    private ViewManager() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new JFrame("Cashier System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(screenSize.width, screenSize.height);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        duringRegister = new DuringRegister();
        duringArticles = new DuringArticles();

        cardPanel.add(duringRegister, "REGISTER");
        cardPanel.add(duringArticles, "ARTICLES");

        mainFrame.add(cardPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public void showRegister() {
        cardLayout.show(cardPanel, "REGISTER");
    }

    public void showArticles() {
        cardLayout.show(cardPanel, "ARTICLES");
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public DuringArticles getDuringArticles() {
        return duringArticles;
    }

    public DuringRegister getDuringRegister() {
        return duringRegister;
    }
}