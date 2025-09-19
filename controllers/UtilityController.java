package controllers;

import controllers.display.DisplayAmountController;
import views.leftPanel.Keyboard;
import views.leftPanel.Utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtilityController {
    public UtilityController(Keyboard keyboard, Utility utility, DisplayAmountController displayAmountController){
        MakeTransaction makeTransaction = new MakeTransaction();

        utility.getButton("<-BKSP").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAmountController.removeLast();
                keyboard.updateTopCountDisplay(displayAmountController.getContent());
            }
        });

        utility.getButton("C/ Zmaza").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 displayAmountController.clearContent();
                keyboard.updateTopCountDisplay(displayAmountController.getContent());
            }
        });
        //TODO:temporary
        utility.getButton("Tlač Dane").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainController.makeTransaction();
            }
        });
    }
}
