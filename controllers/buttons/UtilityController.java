package controllers.buttons;

import assets.ButtonSet;
import controllers.display.ContentController;
import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import views.panels.DuringRegister;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;


public class UtilityController {
    AddItemAction addItemAction = new AddItemAction();
    public UtilityController() {
        /*
        ##Controlling all the buttons that are on the right side in DuringIdle
         */
        ViewManager.getInstance().getDuringIdle().getButton(ButtonSet.ButtonLabel.BEGIN.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());

        /*
        ##Controlling all the buttons that are on the right side in DuringRegister
         */
        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();
        duringRegister.getButton(ButtonSet.ButtonLabel.ARTICLES.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());
        duringRegister.getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()).addActionListener(addItemAction);

        /*
        #Adding control for handling cash
         */
        duringRegister.getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(e -> {
            duringRegister.switchStateUtility(e);
            DisplayDispatcher.viaDefault();
            ContentController.clearContent();
        });
        duringRegister.getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(e -> {
            OpenTransactionManager.getInstance().addPayment(e);
            ContentController.clearContent();
        });
        duringRegister.getButton(ButtonSet.ButtonLabel.CARD.toString()).addActionListener(OpenTransactionManager.getInstance()::addPayment);
        duringRegister.getButton(ButtonSet.ButtonLabel.CASH.toString()).addActionListener(e -> {
            duringRegister.switchStateCash(e);
            DisplayDispatcher.viaCash();
            ContentController.clearContent();
        });
        for(String buttonName : ButtonSet.CASH_NAMES.getStringLabels()){
            if(buttonName.contains("€")){
                duringRegister.getButton(buttonName).setActionCommand(ButtonSet.ButtonLabel.ADD.toString());
                duringRegister.getButton(buttonName).addActionListener(e -> {
                    ContentController.clearContent();
                    ContentController.appendContent(String.format("%s00",buttonName.split("€")[0]));
                    OpenTransactionManager.getInstance().addPayment(e);
                    ContentController.clearContent();
                });
            }
        }

        /*
        ##Controlling buttons on bottom side of DuringArticles
         */
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> ViewManager.getInstance().showRegister());
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(addItemAction);
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.REMOVE.toString()).addActionListener(new RemoveItemAction());
    }

}
