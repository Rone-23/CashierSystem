package controllers.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import views.panels.*;

public class ViewManager {
    private static ViewManager instance;

    private final JFrame mainFrame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final DuringIdle duringIdle;
    private final DuringRegister duringRegister;
    private final DuringArticles duringArticles;
    private final DuringCodeEnter duringCode;
    private final DuringReturnTransaction duringReturn;

    private ViewManager() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new JFrame("Cashier System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(screenSize.width, screenSize.height);
        //For development purposes
//        mainFrame.setUndecorated(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        duringIdle = new DuringIdle();
        duringRegister = new DuringRegister();
        duringArticles = new DuringArticles();
        duringCode = new DuringCodeEnter();
        duringReturn = new DuringReturnTransaction();

        cardPanel.add(duringIdle, "IDLE");
        cardPanel.add(duringRegister, "REGISTER");
        cardPanel.add(duringArticles, "ARTICLES");
        cardPanel.add(duringCode, "CODE-ENTER");
        cardPanel.add(duringReturn, "RETURN-TRANSACTION");

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

    public void showIdle() {
        cardLayout.show(cardPanel, "IDLE");
    }

    public void showCodeEnter(ActionEvent e) {
        cardLayout.show(cardPanel, "CODE-ENTER");
    }

    public void showReturnTransaction() {
        cardLayout.show(cardPanel, "RETURN-TRANSACTION");
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public DuringIdle getDuringIdle() {
        return duringIdle;
    }

    public DuringArticles getDuringArticles() {
        return duringArticles;
    }

    public DuringRegister getDuringRegister() {
        return duringRegister;
    }

    public DuringCodeEnter getDuringCodeEnter() {
        return duringCode;
    }

    public DuringReturnTransaction getDuringReturn() {
        return duringReturn;
    }

    public void returnToDefault(){
        duringRegister.returnToDefault();
        duringReturn.returnToDefault();
    }

}