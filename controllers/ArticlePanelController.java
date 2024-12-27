package controllers;

import views.ArticlesPanel;
import views.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticlePanelController {
    public ArticlePanelController(MainPanel mainPanel, ArticlesPanel articlesPanel){
        articlesPanel.getButtons().get("cancelButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(true);
                articlesPanel.setVisible(false);
            }
        });
    }
}
