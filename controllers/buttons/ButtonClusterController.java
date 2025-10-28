package controllers.buttons;

import controllers.ViewManager;
import views.Components.ButtonCluster;

public class ButtonClusterController {

    public ButtonClusterController() {
        ButtonCluster buttonCluster = (ButtonCluster) ViewManager.getInstance().getDuringRegister().getButtonCluster();

        buttonCluster.getButton("artikle").addActionListener(e -> ViewManager.getInstance().showArticles());

        ViewManager.getInstance().getDuringArticles().getUtilityButton("cancel").addActionListener(e -> ViewManager.getInstance().showRegister());
    }
}
