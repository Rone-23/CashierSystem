package utility.tutorial;

import assets.Colors;
import controllers.notifications.NotificationController;
import services.Item;

import java.util.List;

public class ReturnScenarioController extends AbstractScenarioController {

    private enum Phase { ADJUSTING, COMPLETED }
    private Phase currentPhase = Phase.ADJUSTING;

    public ReturnScenarioController(TrainingScenarioOverlay view, List<TrainingObjective> targets, Runnable onCompletionCallback) {
        super(view, targets, onCompletionCallback);
        view.setObjectiveText("Odstráňte tovar tak, aby vám ostal daný počet.", Colors.MILD_YELLOW.getColor());
    }

    @Override
    public void onItemAdd(Item item) {
        if (currentPhase != Phase.ADJUSTING) return;

        TrainingObjective target = objectives.get(item.getName());
        Item scannedItem = openTransaction.getItemsInTransaction().get(item.getName());
        int amount = (scannedItem != null) ? scannedItem.getAmount() : 0;

        target.setCurrentAmount(amount);
        view.updateItemRow(target.getItemName(), amount, target.getTargetAmount(), target.getState());

        checkPhaseProgression();
    }

    @Override
    protected void handleSuccess() {
        currentPhase = Phase.COMPLETED;
        view.setObjectiveText("POLOŽKY ÚSPEŠNE UPRAVENÉ!", Colors.SUCCESS_GREEN.getColor());

        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            NotificationController.notifyObservers("Zaučenie bude hneď pokračovať.", 3000);
            finishScenario();
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void handlePending() {
        currentPhase = Phase.ADJUSTING;
        view.setObjectiveText("Odstráňte tovar tak, aby vám ostal daný počet.", Colors.MILD_YELLOW.getColor());
    }
}