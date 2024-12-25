package controllers;

import services.display.AmountToUse;
import views.leftPanel.Keyboard;
import views.leftPanel.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtilityController {
    public UtilityController(Keyboard keyboard, Utility utility, AmountToUse amountToUse){

        utility.getButton("<-BKSP").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountToUse.removeLast();
                keyboard.updateTopCountDisplay(amountToUse.getContent());
            }
        });

        utility.getButton("C/ Zmaza").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 amountToUse.clearContent();
                keyboard.updateTopCountDisplay(amountToUse.getContent());
            }
        });
    }
}
