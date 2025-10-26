package controllers;

import viewsRework.Components.ArticleFilterButtonCluster;
import viewsRework.panels.DuringArticles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticleController {
    public ArticleController(DuringArticles duringArticles){
        ArticleFilterButtonCluster articleFilterButtonCluster = duringArticles.getArticleFilterButtonCluster();

//        for(JButton button : articleFilterButtonCluster.values().toArray(new JButton[0])){
//            if(button.getName()!=null && button.getName().contains("sectionButton")){
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        try {
//                            System.out.println(Arrays.toString(SQL_Connect.getInstance().getNames(e.getActionCommand())));
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                        //TODO: Change buttons name and change the way it creates items
//                        try {
//                            articlesPanel.updateArticleButtons(SQL_Connect.getInstance().getNames(e.getActionCommand()));
//
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//                });
//            }
//        }
//
//        for(JButton button : articlesPanel.getButtons().values().toArray(new JButton[0])){
//            if(button.getName()!=null && button.getName().contains("articleButton")){
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        ItemCountable item = new ItemCountable(
//                                e.getActionCommand(),
//                                SQL_Connect.getInstance().getPriceByName(e.getActionCommand()),
//                                1.0);
//                        MainController.addItem(item);
//                    }
//                });
//            }
//        }

        duringArticles.getUtilityButton("cancel").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Switching between views
//                mainPanel.setVisible(true);
//                articlesPanel.setVisible(false);
            }
        });
    }
}
