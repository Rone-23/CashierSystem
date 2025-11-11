package controllers.buttons;

import controllers.panels.ViewManager;
import controllers.transaction.CardPayment;
import views.Components.ButtonCluster;

public class UtilityController {

    public UtilityController() {
        /*
        Controlling all the buttons that are on the right side in DuringIdle
         */
        ButtonCluster idleButtonCluster = ViewManager.getInstance().getDuringIdle().getButtonCluster();
        idleButtonCluster.getButton("zacat (artikle)").addActionListener(e -> ViewManager.getInstance().showArticles());

        /*
        Controlling all the buttons that are on the right side in DuringRegister
         */
        ButtonCluster registerButtonCluster = ViewManager.getInstance().getDuringRegister().getButtonCluster();
        registerButtonCluster.getButton("artikle").addActionListener(e -> ViewManager.getInstance().showArticles());
        registerButtonCluster.getButton("karta").addActionListener(e -> new CardPayment());

        /*
        Controlling buttons on bottom side of DuringArticles
         */
        ViewManager.getInstance().getDuringArticles().getUtilityButton("cancel").addActionListener(e -> ViewManager.getInstance().showRegister());
    }
}
