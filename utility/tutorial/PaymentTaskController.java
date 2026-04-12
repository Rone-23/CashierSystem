package utility.tutorial;

import controllers.notifications.NotificationController;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.OpenTransaction;

public class PaymentTaskController implements OpenTransactionObserver {
    private final Runnable onCompletionCallback;
    private final String requiredPaymentType;
    private boolean isReverting = false;

    public PaymentTaskController(String requiredPaymentType, Runnable onCompletionCallback) {
        this.requiredPaymentType = requiredPaymentType;
        this.onCompletionCallback = onCompletionCallback;

        OpenTransaction.addObserver(this);
    }

    @Override
    public void onAddedPayment(int toPayLeft, String typeOfPayment, int addedAmount) {
        if (addedAmount == 0) {
            return;
        }
        if (isReverting) {
            isReverting = false;
            return;
        }

        if (!typeOfPayment.equalsIgnoreCase(requiredPaymentType) && !requiredPaymentType.equalsIgnoreCase("ANY")) {
            isReverting = true;

            OpenTransactionManager.getInstance().getOpenTransaction().pay(typeOfPayment, String.valueOf(-addedAmount));
            NotificationController.notifyObservers("Vykonajte platbu cez: " + requiredPaymentType + "!", 3000);
            return;
        }

        if (toPayLeft <= 0) {
            OpenTransaction.removeObserver(this);
            if (onCompletionCallback != null) {
                onCompletionCallback.run();
            }
        }
    }

    @Override
    public void paymentDone(){
        OpenTransaction.removeObserver(this);
        if (onCompletionCallback != null) {
            onCompletionCallback.run();
        }
    }

    @Override
    public void onDestroy() {
        OpenTransaction.removeObserver(this);
    }
}