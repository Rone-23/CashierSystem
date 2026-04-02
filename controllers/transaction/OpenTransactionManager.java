package controllers.transaction;

import assets.ButtonSet;
import controllers.display.DisplayDispatcher;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import services.Item;
import services.OpenTransaction;
import services.Users.CashierObserver;
import services.Users.CashierSession;
import services.VoucherService;

import java.awt.event.ActionEvent;

public class OpenTransactionManager implements OpenTransactionObserver, ContentObserver, CashierObserver {
    private static OpenTransactionManager instance;
    private OpenTransaction openTransaction;
    private int cashierId = -1;
    private String content;

    private OpenTransactionManager(){
        OpenTransaction.addObserver(this);
        CashierSession.addObserver(this);
        ContentController.addObserver(this);
    }

    public static OpenTransactionManager getInstance(){
        if (instance == null) {
            instance = new OpenTransactionManager();
        }
        return instance;
    }

    private OpenTransaction createOpenTransaction(){
        try {
            openTransaction = new OpenTransaction(cashierId);
            ViewManager.getInstance().getStatusBar().setStatus("Nie");
        } catch (NumberFormatException ignored) {
        }

        return openTransaction;
    }

    public OpenTransaction getOpenTransaction(){
        if(openTransaction == null){
            return createOpenTransaction();
        }
        return openTransaction;
    }

    public void addPayment(ActionEvent paymentType){
        System.out.printf(ButtonSet.ButtonLabel.USE_VOUCHER.toString());
        try {
            if (ButtonSet.ButtonLabel.USE_VOUCHER.toString().equals(paymentType.getActionCommand())) {
                if (!VoucherService.getInstance().isVoucherStaged()) {
                    String msg = VoucherService.getInstance().stageVoucher(content);
                    NotificationController.notifyObservers(msg, 5000);
                    ContentController.clearContent();
                    return;
                } else {
                    int amountToPay = Integer.parseInt(content);
                    int remaining = VoucherService.getInstance().chargeVoucher(amountToPay);
                    NotificationController.notifyObservers("Zaplatené poukážkou. Zostatok: " + String.format("%.2f", remaining / 100.0) + " EUR", 5000);

                    openTransaction.pay(paymentType,content);
                    ContentController.clearContent();
                    return;
                }
            }

            openTransaction.pay(paymentType, content);
            ContentController.clearContent();

        } catch (NumberFormatException ex) {
            NotificationController.notifyObservers("Neplatná hodnota!", 3000);
            ContentController.clearContent();
        } catch (Exception ex) {
            NotificationController.notifyObservers(ex.getMessage(), 4000);
            ContentController.clearContent();
        }
    }

    public void addItem(Item item){
        getOpenTransaction().addItem(item, content);
        System.out.printf("Name %s Amount %d Price %d\n",item.getName(), item.getAmount(), item.getPrice());
    }

    public void removeItem(Item item){
        getOpenTransaction().removeItem(item, content);
    }

    public void loadHistoricalTransaction(int transactionID, String date, Item[] items){
        if(items.length==0){
            throw new IllegalStateException("Dopytovaná transakcia neobsahuje žiaden tovar určený na vrátenie.");
        }

        this.openTransaction = new OpenTransaction(transactionID, date);


        this.openTransaction.loadItemsIntoTransaction(items);
    }

    @Override
    public void paymentDone() {
        MakeTransaction makeTransaction = new MakeTransaction();
        makeTransaction.makeTransaction(openTransaction);
        ViewManager.getInstance().showIdle();
        ViewManager.getInstance().returnToDefault();
        DisplayDispatcher.activeDisplayForAmount();
    }

    @Override
    public void onDestroy() {
        openTransaction = null;
        ViewManager.getInstance().getStatusBar().setStatus("---");
        ViewManager.getInstance().getStatusBar().setTransactionId("---");
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        ViewManager.getInstance().getStatusBar().setTransactionId(String.valueOf(openTransaction.getTransactionID()));
    }

    @Override
    public void onCashierLogin(int cashierId) {
        this.cashierId = cashierId;
        ViewManager.getInstance().getStatusBar().setCashierId(String.valueOf(cashierId));
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }
}
