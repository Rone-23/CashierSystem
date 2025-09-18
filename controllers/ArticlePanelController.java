package controllers;

import controllers.display.ItemsToSee;
import services.ItemCountable;
import services.OpenTransaction;
import services.SQL_Connect;
import views.ArticlesPanel;
import views.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class ArticlePanelController {
    public ArticlePanelController(MainPanel mainPanel, ArticlesPanel articlesPanel){
        for(JButton button : articlesPanel.getButtons().values().toArray(new JButton[0])){
            if(button.getName()!=null && button.getName().contains("sectionButton")){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            System.out.println(Arrays.toString(SQL_Connect.getInstance().getNames(e.getActionCommand())));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        //TODO: Change buttons name and change the way it creates items
                        try {
                            articlesPanel.updateArticleButtons(SQL_Connect.getInstance().getNames(e.getActionCommand()));

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        }

        for(JButton button : articlesPanel.getButtons().values().toArray(new JButton[0])){
            if(button.getName()!=null && button.getName().contains("articleButton")){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ItemCountable item = new ItemCountable(
                                e.getActionCommand(),
                                SQL_Connect.getInstance().getPriceByName(e.getActionCommand()),
                                1.0);
                        MainController.addItem(item);
                        ItemsToSee.updateDisplay(item);
                    }
                });
            }
        }

        articlesPanel.getButtons().get("cancelButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(true);
                articlesPanel.setVisible(false);
            }
        });
    }
}
