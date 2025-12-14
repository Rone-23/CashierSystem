package controllers.buttons;
import assets.ButtonSet;
import assets.Constants;
import controllers.display.ContentController;
import controllers.display.ContentObserver;
import views.Components.Display;
import views.panels.ButtonFoundable;


public class KeyboardController implements ContentObserver {
    Display display;
    ContentController amountToUse;

    public KeyboardController(ButtonFoundable keyboard , Display display, ContentController amountToUse){
        this.display = display;
        this.amountToUse = amountToUse;

        keyboard.getButton(ButtonSet.ButtonLabel.BACKSPACE.toString()).addActionListener(_ -> {
            if(display.getDisplayType() == Constants.SPLIT){
                amountToUse.removeLast();
                display.setText( display.getTextArray()[0] ,amountToUse.getContent());
            }else{
                amountToUse.removeLast();
                display.setText(amountToUse.getContent());
            }
        });

        keyboard.getButton(ButtonSet.ButtonLabel.DELETE.toString()).addActionListener(_ -> {
            if(display.getDisplayType() == Constants.SPLIT){
                amountToUse.clearContent();
                display.setText(display.getTextArray()[0],amountToUse.getContent());
            }else {
                amountToUse.clearContent();
                display.setText(amountToUse.getContent());
            }
        });

        for (int keyName=0; keyName<10; keyName++) {
            int finalKeyName = keyName;
            if (display.getDisplayType() == Constants.SPLIT) {
                keyboard.getButton(String.valueOf(keyName)).addActionListener(_ -> {
                    amountToUse.appendContent(String.valueOf(finalKeyName));
                    display.setText(display.getTextArray()[0],amountToUse.getContent());
                });
            } else {
                keyboard.getButton(String.valueOf(keyName)).addActionListener(_ -> {
                    amountToUse.appendContent(String.valueOf(finalKeyName));
                    display.setText(amountToUse.getContent());
                });
            }
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        if(display.getDisplayType() == Constants.SPLIT){
            display.setText(display.getTextArray()[0],amountToUse.getContent());
        }else {
            display.setText(amountToUse.getContent());
        }
    }
}
