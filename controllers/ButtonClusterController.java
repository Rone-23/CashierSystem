package controllers;

import viewsRework.Components.ButtonCluster;
import viewsRework.panels.DuringArticles;
import viewsRework.panels.DuringRegister;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClusterController {

    public ButtonClusterController(DuringRegister duringRegister, DuringArticles duringArticles) {
        ButtonCluster buttonCluster = (ButtonCluster) duringRegister.getButtonCluster();


        buttonCluster.getButton("artikle").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                articlesPanel.setVisible(true);
//                mainPanel.setVisible(false);
                System.out.println("Articlesss");
            }
        });
    }
}
