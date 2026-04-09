package controllers.display;

import assets.Constants;
import controllers.transaction.ContentController;
import controllers.transaction.OpenTransactionObserver;
import controllers.transaction.ContentObserver;
import services.Item;
import services.OpenTransaction;
import views.Components.Display;

public class DisplayController implements OpenTransactionObserver, ContentObserver {
    protected final Display display;
    protected OpenTransaction openTransaction;

    public DisplayController(Display display){
        ContentController.addObserver(this);
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
        if (display.getDisplayType() == Constants.TOTAL || display.getDisplayType() == Constants.SPLIT) {
            display.setText(new String[]{"0.00", "1"});
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
        if (display.getDisplayType() == Constants.SPLIT) {
            String currentTop = display.getTextArray()[0];
            display.setText(new String[]{currentTop, content});

        } else if (display.getDisplayType() == Constants.TOTAL) {
            try {
                display.setText(String.format("%.2f", Integer.parseInt(content) * 0.01));
            } catch (NumberFormatException e) {
                display.setText(content);
            }

        } else {
            display.setText(content);
        }
    }

    @Override
    public void onTransactionUpdate() {
        display.setText(String.format("%.2f",openTransaction.getMissing()*0.01));

    }
}