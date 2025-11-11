package controllers.display;

import assets.Constants;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import views.Components.Display;

public class DisplayController implements OpenTransactionObserver {
    Display display;
    OpenTransaction openTransaction = OpenTransactionManager.getInstance().getOpenTransaction();
    public DisplayController(Display display){
        this.display = display;
    }


    //Observer

    @Override
    public void onItemAdd(Item item) {
        display.setText(String.format("%.2f",openTransaction.getTotal()));
    }

    @Override
    public void onDestroy() {
        if(display.getDisplayType().equals(Constants.TOTAL) || display.getDisplayType().equals(Constants.SPLIT)){
            display.getTextArray()[0]="0.00";
        }
    }
}
