package controllers.display;

import controllers.transaction.OpenTransactionObserver;
import services.Item;

import java.util.ArrayList;
import java.util.List;

public class ContentController implements  ContentObserver, OpenTransactionObserver {
    private static final List<ContentObserver> observerList = new ArrayList<>();
    private final StringBuilder content = new StringBuilder();

    public String getContent(){
        return content.toString();
    }

    public void appendContent(String text) {

        if(content.length()>6){
            throw new ArithmeticException("Maximal allowed digit is 7.");
        }
        content.append(text);
        notifyObservers();
    }

    public void clearContent() {
        content.setLength(0);
        content.append("1");
        notifyObservers();
    }

    public void removeLast(){
        if(!content.isEmpty()){
            content.setLength(content.length()-1);
        }
        notifyObservers();
    }

    //Observer
    public static void addObserver(ContentObserver contentObserver){observerList.add(contentObserver);}

    public static void removeObserver(ContentObserver contentObserver){observerList.remove(contentObserver);}

    private void notifyObservers(){
        for (ContentObserver observer: observerList){
            observer.notifyContentUpdate(getContent());
        }
    }

    @Override
    public void onItemAdd(Item item) {
        clearContent();
    }
}
