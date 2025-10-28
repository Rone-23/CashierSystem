package controllers.buttons;
import controllers.display.ContentAmountController;
import views.Components.Display;
import views.Components.Keyboard;


public class KeyboardController  {

    public KeyboardController(Keyboard keyboard , Display display, ContentAmountController amountToUse){
        keyboard.getButton("backspace").addActionListener(e -> {
            amountToUse.removeLast();
            display.setText(amountToUse.getContent());
        });

        keyboard.getButton("delete").addActionListener(e -> {
            amountToUse.clearContent();
            display.setText(amountToUse.getContent());
        });

        for (int keyName=0; keyName<10; keyName++){
            int finalKeyName = keyName;
            keyboard.getButton(String.valueOf(keyName)).addActionListener(e -> {
                amountToUse.appendContent(String.valueOf(finalKeyName));
                display.setText(amountToUse.getContent());
            });}
    }

}
