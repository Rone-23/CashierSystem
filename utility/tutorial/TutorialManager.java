package utility.tutorial;

import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TutorialManager {
    private static TutorialManager instance;
    private final TutorialOverlay overlay;
    private final List<TutorialStep> steps = new ArrayList<>();
    private int currentStepIndex = 0;

    private TutorialManager() {
        overlay = new TutorialOverlay();
    }

    public static TutorialManager getInstance() {
        if (instance == null) instance = new TutorialManager();
        return instance;
    }

    public void startTutorial(List<TutorialStep> tutorialSequence) {
        this.steps.clear();
        this.steps.addAll(tutorialSequence);
        this.currentStepIndex = 0;

        ViewManager.getInstance().getMainFrame().setGlassPane(overlay);
        overlay.setVisible(true);

        showCurrentStep();
    }

    private void showCurrentStep() {
        if (currentStepIndex >= steps.size()) {
            endTutorial();
            return;
        }

        TutorialStep step = steps.get(currentStepIndex);

        if (step.isSilentTask()) {
            overlay.setVisible(false);

            step.getAsyncTask().accept(this::nextStep);

        } else {
            overlay.setVisible(true);
            step.executeSetup();

            SwingUtilities.invokeLater(() -> overlay.displayStep(step, this::nextStep, this::endTutorial));
        }
    }

    private void nextStep() {
        currentStepIndex++;
        showCurrentStep();
    }

    public void endTutorial() {
        overlay.setVisible(false);
        OpenTransactionManager.getInstance().getOpenTransaction().openTransactionDestroy();
        ViewManager.getInstance().showIdle();
    }
}