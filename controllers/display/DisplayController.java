package controllers.display;

import assets.Constants;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import views.Components.Display;

public class DisplayController implements OpenTransactionObserver, ContentObserver {
    Display display;
    OpenTransaction openTransaction;
    public DisplayController(Display display){
        this.display = display;
    }


    //Observer

    @Override
    public void onItemAdd(Item item) {
        display.setText(String.format("%.2f",openTransaction.getMissing()));
    }

    @Override
    public void onItemRemove(Item item) {
        display.setText(String.format("%.2f",openTransaction.getMissing()));
    }

    @Override
    public void onDestroy() {
        if(display.getDisplayType().equals(Constants.TOTAL) || display.getDisplayType().equals(Constants.SPLIT)){
            display.getTextArray()[0]="0.00";
        }else {
            display.getTextArray()[0]="0";
        }
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        this.openTransaction = openTransaction;
    }

    @Override
    public void onAddedPayment(Double toPayLeft, String typeOfPayment, Double addedAmount) {
        if(display.getDisplayType().equals(Constants.TOTAL)){
            display.setText(String.format("%.2f",toPayLeft));
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        if(display.getDisplayType() == Constants.SPLIT){
            display.setText(display.getTextArray()[0],String.format("%s",content));
        }else if (display.getDisplayType() == Constants.TOTAL){
            display.setText(String.format("%.2f",Double.parseDouble(content) * 0.01));
        }else{
            display.setText(String.format("%s",content));
        }
    }
}
