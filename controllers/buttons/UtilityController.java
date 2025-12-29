package controllers.buttons;

import assets.ButtonSet;
import controllers.display.ContentController;
import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import views.panels.DuringRegister;


public class UtilityController {
    AddItemAction addItemAction = new AddItemAction();
    public UtilityController() {
        /*
        Controlling all the buttons that are on the right side in DuringIdle
         */
        ViewManager.getInstance().getDuringIdle().getButton(ButtonSet.ButtonLabel.BEGIN.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());

        /*
        Controlling all the buttons that are on the right side in DuringRegister
         */
        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.ARTICLES.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()).addActionListener(addItemAction);
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(duringRegister::switchStateUtility);
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> DisplayDispatcher.viaDefault());
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> ContentController.clearContent());
//        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.CARD.toString()).addActionListener(_ -> new CardPayment());
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.CARD.toString()).addActionListener(OpenTransactionManager.getInstance()::addPayment);
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.CASH.toString()).addActionListener(duringRegister::switchStateCash);
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.CASH.toString()).addActionListener(_ -> DisplayDispatcher.viaCash());
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.CASH.toString()).addActionListener(_ -> ContentController.clearContent());
        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(OpenTransactionManager.getInstance()::addPayment);
//        ViewManager.getInstance().getDuringRegister().getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(cashPayment::addCash);

        /*
        Controlling buttons on bottom side of DuringArticles
         */
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> ViewManager.getInstance().showRegister());
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(addItemAction);
        ViewManager.getInstance().getDuringArticles().getButton("ubrať").addActionListener(new RemoveItemAction());
    }
}
