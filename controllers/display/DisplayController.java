package controllers.display;

import assets.Constants;
import controllers.transaction.OpenTransactionObserver;
import controllers.transaction.ContentObserver;
import services.Item;
import services.OpenTransaction;
import views.Components.Display;

public class DisplayController implements OpenTransactionObserver, ContentObserver {
    protected final Display display;
    protected OpenTransaction openTransaction;

    public DisplayController(Display display){
        this.display = display;
    }

    @Override
    public void onItemAdd(Item item) {
        updateTotalDisplay();
    }

    @Override
    public void onItemRemove(Item item) {
        updateTotalDisplay();
    }

    private void updateTotalDisplay() {
        if (openTransaction != null && (display.getDisplayType() == Constants.TOTAL || display.getDisplayType() == Constants.SPLIT)) {
            display.setText(String.format("%.2f", openTransaction.getMissing() * 0.01));
        }
    }

    @Override
    public void onDestroy() {
        if (display.getDisplayType() == Constants.TOTAL) {
            display.setText("0.00");
        } else if (display.getDisplayType() == Constants.SPLIT) {
            display.setText(new String[]{"0.00", "1"});
        } else if (display.getDisplayType() == Constants.CODE || display.getDisplayType() == Constants.CUSTOMER) {
            display.setText("");
        } else {
            display.setText("0");
        }
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        this.openTransaction = openTransaction;
    }

    @Override
    public void onAddedPayment(int toPayLeft, String typeOfPayment, int addedAmount) {
        if (display.getDisplayType() == Constants.TOTAL || display.getDisplayType() == Constants.SPLIT) {
            display.setText(String.format("%.2f", toPayLeft * 0.01));
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        switch(display.getDisplayType()){
            case Constants.SPLIT -> {
                String currentTop = display.getTextArray()[0];
                display.setText(new String[]{currentTop, content.isEmpty() ? "1" : content});
            }
            case Constants.TOTAL ->  display.setText(String.format("%.2f", Integer.parseInt(content.isEmpty() ? "0" : content) * 0.01));
            case Constants.WEIGHT -> display.setText(String.format("%d", Integer.parseInt(content.isEmpty() ? "1" : content)));
            case Constants.CODE, Constants.RECEIPT, Constants.CUSTOMER -> display.setText(content.isEmpty() ? "" : content);
            default -> display.setText(content.isEmpty() ? "" : content);
        }
    }

    @Override
    public void onTransactionUpdate() {
        display.setText(String.format("%.2f",openTransaction.getMissing()*0.01));

    }
}