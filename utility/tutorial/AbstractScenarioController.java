package utility.tutorial;

import controllers.transaction.OpenTransactionManager;
import controllers.transaction.OpenTransactionObserver;
import services.OpenTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractScenarioController implements OpenTransactionObserver {

    protected final TrainingScenarioOverlay view;
    protected final Map<String, TrainingObjective> objectives = new HashMap<>();
    protected final Runnable onCompletionCallback;
    protected final OpenTransaction openTransaction;

    public AbstractScenarioController(TrainingScenarioOverlay view, List<TrainingObjective> targets, Runnable onCompletionCallback) {
        this.openTransaction = OpenTransactionManager.getInstance().getOpenTransaction();
        this.view = view;
        this.onCompletionCallback = onCompletionCallback;

        OpenTransaction.addObserver(this);
        for (TrainingObjective obj : targets) {
            objectives.put(obj.getItemName(), obj);
        }
    }

    protected void checkPhaseProgression() {
        boolean allExact = true;

        for (TrainingObjective obj : objectives.values()) {
            if (obj.getState() != TrainingObjective.State.EXACT) {
                allExact = false;
                break;
            }
        }

        if (allExact) {
            handleSuccess();
        } else {
            handlePending();
        }
    }

    protected void finishScenario() {
        view.dispose();
        OpenTransaction.removeObserver(this);
        if (onCompletionCallback != null) {
            onCompletionCallback.run();
        }
    }

    protected abstract void handleSuccess();
    protected abstract void handlePending();

    @Override
    public void onDestroy() {
        for (TrainingObjective obj : objectives.values()) {
            obj.setCurrentAmount(0);
        }
        checkPhaseProgression();
    }
}