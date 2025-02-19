package controllers;

import controllers.display.AmountToUse;
import services.OpenTransaction;
import services.SQL_Connect;
import views.leftPanel.Keyboard;
import views.leftPanel.Utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtilityController {
    public UtilityController(Keyboard keyboard, Utility utility, AmountToUse amountToUse,  OpenTransaction openTransaction){
        MakeTransaction makeTransaction = new MakeTransaction();

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
        //TODO:temporary
        utility.getButton("Tlač Dane").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeTransaction.makeTransaction(openTransaction);

            }
        });
    }
}
