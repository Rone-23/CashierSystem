package controllers;

import views.ArticlesPanel;
import views.MainPanel;
import views.rightPanel.RightTopPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopRightController {

    public TopRightController(MainPanel mainPanel, ArticlesPanel articlesPanel) {
        mainPanel.getRightPanel().getRightTopPanel().getArticleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                articlesPanel.setVisible(true);
                mainPanel.setVisible(false);
            }
        });
    }
}
