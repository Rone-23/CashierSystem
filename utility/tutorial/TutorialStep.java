package utility.tutorial;

import javax.swing.JComponent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TutorialStep {

    public enum DialogPosition {
        AUTO,TOP_LEFT, TOP_RIGHT, CENTER, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    private final String title;
    private final String message;
    private final Supplier<List<JComponent>> targetComponentsSupplier;
    private final Runnable setupAction;
    private final DialogPosition position;
    private boolean isSilentTask = false;
    private Consumer<Runnable> asyncTask = null;


    public TutorialStep(Consumer<Runnable> asyncTask) {
        this.title = "";
        this.message = "";
        this.setupAction = null;
        this.targetComponentsSupplier = () -> null;
        this.position = DialogPosition.CENTER;

        this.isSilentTask = true;
        this.asyncTask = asyncTask;
    }

    public TutorialStep(String title, String message, Supplier<JComponent> singleTargetComponent, Runnable setupAction, DialogPosition position) {
        this.title = title;
        this.message = message;
        this.setupAction = setupAction;
        this.position = position;

        if (singleTargetComponent != null) {
            this.targetComponentsSupplier = () -> Collections.singletonList(singleTargetComponent.get());
        } else {
            this.targetComponentsSupplier = () -> null;
        }
    }

    public TutorialStep(String title, String message, Runnable setupAction, DialogPosition position, Supplier<List<JComponent>> multipleTargets) {
        this.title = title;
        this.message = message;
        this.setupAction = setupAction;
        this.position = position;
        this.targetComponentsSupplier = multipleTargets;
    }

    public static String mention(assets.ButtonSet.ButtonLabel label) {
        java.awt.Color bg = label.getColor().getColor();

        String hexBg = String.format("#%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue());

        double brightness = (bg.getRed() * 0.299) + (bg.getGreen() * 0.587) + (bg.getBlue() * 0.114);
        String hexText = brightness > 160 ? "#000000" : "#FFFFFF";

        return "&nbsp;<b><span style='background-color: " + hexBg + "; color: " + hexText + ";'>&nbsp;"
                + label + "&nbsp;</span></b>&nbsp;";
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public DialogPosition getPosition() { return position; }


    public List<JComponent> getTargetComponents() {
        return targetComponentsSupplier != null ? targetComponentsSupplier.get() : null;
    }

    public void executeSetup() {
        if (setupAction != null) {
            setupAction.run();
        }
    }

    public boolean isSilentTask() { return isSilentTask; }
    public Consumer<Runnable> getAsyncTask() { return asyncTask; }
}