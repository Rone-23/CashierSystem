package controllers.buttons;
import assets.Constants;
import controllers.display.ContentController;
import controllers.display.ContentObserver;
import views.Components.Display;
import views.Components.Keyboard;


public class KeyboardController implements ContentObserver {
    Display display;
    ContentController amountToUse;

    public KeyboardController(Keyboard keyboard , Display display, ContentController amountToUse){
        this.display = display;
        this.amountToUse = amountToUse;
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

    @Override
    public void notifyContentUpdate(String content) {
        if(display.getDisplayType() == Constants.SPLIT){
            display.setText(display.getTextArray()[0],amountToUse.getContent());
        }else {
            display.setText(amountToUse.getContent());
        }
    }
}
