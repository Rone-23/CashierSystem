package controllers;
import services.display.AmountToUse;
import views.leftPanel.DisplayPanel;
import views.leftPanel.Keyboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyboardController  {

    public KeyboardController(Keyboard keyboard ,DisplayPanel displayPanel, AmountToUse amountToUse){

        for (int keyName=0; keyName<10; keyName++){
            int finalKeyName = keyName;
            keyboard.getButton(String.valueOf(keyName)).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                amountToUse.appendContent(String.valueOf(finalKeyName));
                keyboard.updateTopCountDisplay(amountToUse.getContent());

            }
        });}
    }

}
