package controllers.buttons;
import assets.Constants;
import controllers.display.ContentAmountController;
import views.Components.Display;
import views.Components.Keyboard;


public class KeyboardController  {

    public KeyboardController(Keyboard keyboard , Display display, ContentAmountController amountToUse){
        keyboard.getButton("backspace").addActionListener(e -> {
            if(display.getDisplayType() == Constants.SPLIT){
                amountToUse.removeLast();
                display.setText( display.getTextArray()[0] ,amountToUse.getContent());
            }else{
                amountToUse.removeLast();
                display.setText(amountToUse.getContent());
            }
        });

        keyboard.getButton("delete").addActionListener(e -> {
            if(display.getDisplayType() == Constants.SPLIT){
                amountToUse.clearContent();
                display.setText(display.getTextArray()[0],amountToUse.getContent());
            }else {
                amountToUse.clearContent();
                display.setText(amountToUse.getContent());
            }
        });

        for (int keyName=0; keyName<10; keyName++) {
            if (display.getDisplayType() == Constants.SPLIT) {
                int finalKeyName = keyName;
                keyboard.getButton(String.valueOf(keyName)).addActionListener(e -> {
                    amountToUse.appendContent(String.valueOf(finalKeyName));
                    display.setText(display.getTextArray()[0],amountToUse.getContent());
                });
            } else {
                int finalKeyName = keyName;
                keyboard.getButton(String.valueOf(keyName)).addActionListener(e -> {
                    amountToUse.appendContent(String.valueOf(finalKeyName));
                    display.setText(amountToUse.getContent());
                });
            }
        }
    }

}
