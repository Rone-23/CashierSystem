package controllers.buttons;

import controllers.panels.ViewManager;
import views.Components.ButtonCluster;

public class UtilityController {

    public UtilityController() {
        /*
        Controlling all the buttons that are on the right side in DuringRegister
         */
        ButtonCluster buttonCluster = (ButtonCluster) ViewManager.getInstance().getDuringRegister().getButtonCluster();
        buttonCluster.getButton("artikle").addActionListener(e -> ViewManager.getInstance().showArticles());

        /*
        Controlling buttons on bottom side of DuringArticles
         */
        ViewManager.getInstance().getDuringArticles().getUtilityButton("cancel").addActionListener(e -> ViewManager.getInstance().showRegister());
    }
}
