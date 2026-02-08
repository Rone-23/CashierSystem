package controllers.display;

import assets.ButtonSet;
import controllers.buttons.KeyboardListener;
import controllers.transaction.OpenTransactionObserver;
import services.Item;

import java.util.ArrayList;
import java.util.List;

public class ContentController implements OpenTransactionObserver, KeyboardListener {
    private static final List<ContentObserver> observerList = new ArrayList<>();
    private static final StringBuilder content = new StringBuilder("default");

    public static String getContent(){
        if(content.toString().equals("default")){
            return "1";
        }else {
            return content.toString();
        }
    }

    public static void appendContent(String text) {
        if(content.toString().equals("default")){
            content.setLength(0);
        }
        if(content.length()>6){
            throw new ArithmeticException("Maximal allowed digit is 7.");
        }
        if(!(content.isEmpty() && text.equals("0"))){
            content.append(text);
            notifyContentUpdate();
        }
    }

    public static void clearContent() {
        content.setLength(0);
        content.append("default");
        notifyContentUpdate();
    }

    public static void removeLast(){
        if(!content.toString().equals("default") &&  content.length()!=1){
            content.setLength(content.length()-1);
        }else{clearContent();}
        notifyContentUpdate();
    }


    //Observer
    public static void addObserver(ContentObserver contentObserver){observerList.add(contentObserver);}

    public static void removeObserver(ContentObserver contentObserver){observerList.remove(contentObserver);}

    private static void notifyContentUpdate(){
        for (ContentObserver observer: observerList){
            observer.notifyContentUpdate(getContent());
        }
    }

    @Override
    public void onDestroy() {
        clearContent();
    }

    @Override
    public void onItemAdd(Item item) {
        clearContent();
    }

    @Override
    public void keyboardPress(String buttonPressed) {
        if (buttonPressed.equals(ButtonSet.ButtonLabel.DELETE.toString())){
            clearContent();
        } else if (buttonPressed.equals(ButtonSet.ButtonLabel.BACKSPACE.toString())) {
            removeLast();
        }else {
            appendContent(buttonPressed);
        }
    }
}
