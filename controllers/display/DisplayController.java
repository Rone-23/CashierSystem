package controllers.display;

import controllers.openTransaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import views.Components.Display;

public class DisplayController implements OpenTransactionObserver {
    Display display;
    OpenTransaction openTransaction;
    public DisplayController(Display display){
        this.display = display;
    }

    @Override
    public void onCreate(OpenTransaction openTransaction){
        this.openTransaction = openTransaction;
    }

    @Override
    public void onItemAdd(Item item) {
        display.setText(String.format("%.2f",openTransaction.getTotal()));
    }
}
