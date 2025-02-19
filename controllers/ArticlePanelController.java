package controllers;

import services.ItemCountable;
import services.OpenTransaction;
import services.SQL_Connect;
import views.ArticlesPanel;
import views.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ArticlePanelController {
    public ArticlePanelController(MainPanel mainPanel, ArticlesPanel articlesPanel, OpenTransaction openTransaction){


        articlesPanel.getButtons().get("cancelButton").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(true);
                articlesPanel.setVisible(false);
            }
        });
        articlesPanel.getButtons().get("articleButton_0").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                           openTransaction.addItem(new ItemCountable(
                                   SQL_Connect.getInstance().getString(1),
                                   SQL_Connect.getInstance().getPrice(1),
                                                           1.0)
                           );
                       } catch (SQLException ex) {
                           throw new RuntimeException(ex);
                       }
            }
        });
        //TODO: Add to every button that has article attaches to it way to .addItem
//        int index = 0;
//        for(JButton button : articlesPanel.getButtons().values().toArray(new JButton[0])){
//            if(button.getName().contains("articleButton_")){
//                index++;
//                int finalIndex = index;
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        try {
//                            openTransaction.addItem(new ItemCountable(
//                                                            sqlConnect.getString(finalIndex),
//                                                            sqlConnect.getPrice(finalIndex),
//                                                            1.0)
//                            );
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//                });
//            }
//        }
    }
}
