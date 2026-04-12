package utility.tutorial;

import assets.Colors;
import controllers.notifications.NotificationController;
import controllers.transaction.OpenTransactionManager;
import services.Item;

import java.util.List;

public class TrainingScenarioController extends AbstractScenarioController {

    public TrainingScenarioController(TrainingScenarioOverlay view, List<TrainingObjective> targets, Runnable onCompletionCallback) {
        super(view, targets, onCompletionCallback);
    }

    @Override
    public void onItemAdd(Item item) {
        if (!objectives.containsKey(item.getName())) {
            NotificationController.notifyObservers("Položka '" + item.getName() + "' nie je súčasťou tréningu!", 3000);
            Item itemToRemove = openTransaction.getItemsInTransaction().get(item.getName());
            OpenTransactionManager.getInstance().removeItem(itemToRemove);
            return;
        }

        TrainingObjective target = objectives.get(item.getName());
        Item scannedItem = openTransaction.getItemsInTransaction().get(item.getName());
        int amount = (scannedItem != null) ? scannedItem.getAmount() : 0;

        if (target.getTargetAmount() < amount) {
            NotificationController.notifyObservers("Neblokujte zákazníkovi viac ako má v košíku!", 3000);
            OpenTransactionManager.getInstance().removeItem(item);
            return;
        }

        target.setCurrentAmount(amount);
        view.updateItemRow(target.getItemName(), amount, target.getTargetAmount(), target.getState());

        checkPhaseProgression();
    }

    @Override
    protected void handleSuccess() {
        NotificationController.notifyObservers("Zaučenie bude hneď pokračovať.", 3000, Colors.GREEN.getColor());

        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> finishScenario());
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void handlePending() {
        view.setObjectiveText("Nablokuj položky na zozname.", Colors.MILD_YELLOW.getColor());
    }
}