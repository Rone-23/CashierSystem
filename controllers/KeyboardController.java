package controllers;
import controllers.display.ContentAmountController;
import viewsRework.Components.Display;
import viewsRework.Components.Keyboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyboardController  {

    public KeyboardController(Keyboard keyboard , Display display, ContentAmountController amountToUse){
        keyboard.getButton("backspace").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountToUse.removeLast();
                display.setText(amountToUse.getContent());
            }
        });

        keyboard.getButton("delete").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountToUse.clearContent();
                display.setText(amountToUse.getContent());
            }
        });

        for (int keyName=0; keyName<10; keyName++){
            int finalKeyName = keyName;
            keyboard.getButton(String.valueOf(keyName)).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                amountToUse.appendContent(String.valueOf(finalKeyName));
                display.setText(amountToUse.getContent());
                System.out.println(amountToUse.getContent());
            }
        });}
    }

}
