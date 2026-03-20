package controllers.buttons;

import assets.ButtonSet;
import controllers.notifications.NotificationController;
import controllers.transaction.CashBackAction;
import controllers.transaction.ContentController;
import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import views.panels.DuringRegister;
import views.panels.DuringReturnTransaction;

import javax.swing.*;


public class UtilityController {
    AddItemAction addItemAction = new AddItemAction();
    RemoveItemAction removeItemAction = new RemoveItemAction();
    ReturnTransactionAction returnTransactionAction = new ReturnTransactionAction();
    public UtilityController() {
        /*
        #Controlling all the buttons that are on the right side in DuringIdle
         */
        ViewManager.getInstance().getDuringIdle().getButton(ButtonSet.ButtonLabel.BEGIN.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());
        ViewManager.getInstance().getDuringIdle().getButton(ButtonSet.ButtonLabel.RETURN.toString()).addActionListener(e -> {
            ViewManager.getInstance().showCodeEnter(e);
            DisplayDispatcher.activeDisplayForCode();
        });
        ViewManager.getInstance().getDuringIdle().getButton(ButtonSet.ButtonLabel.COPY_RECEIPT.toString()).addActionListener(e -> {
            ViewManager.getInstance().showCodeEnter(e);
            DisplayDispatcher.activeDisplayForCode();
        });


        /*
        #Controlling all the buttons that are on the right side in DuringRegister
         */
        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();
        duringRegister.getButton(ButtonSet.ButtonLabel.ARTICLES.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());
        duringRegister.getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()).addActionListener(addItemAction);

        /*
        ##Adding control for handling cash
         */

        for(JButton b : duringRegister.getButtons(ButtonSet.ButtonLabel.EXIT.toString())){
            b.addActionListener(e -> {
                duringRegister.switchState(e);
                DisplayDispatcher.activeDisplayForAmount();
                ContentController.clearContent();
            });
        }

        for(JButton b : duringRegister.getButtons(ButtonSet.ButtonLabel.ADD.toString())){
            b.addActionListener(e -> {
                OpenTransactionManager.getInstance().addPayment(e);
                ContentController.clearContent();
            });
        }

        duringRegister.getButton(ButtonSet.ButtonLabel.CASH.toString()).addActionListener(e -> {
            duringRegister.switchState(e);
            DisplayDispatcher.activeDisplayForPayment();
            ContentController.clearContent();
            for (JButton b : duringRegister.getButtons(ButtonSet.ButtonLabel.ADD.toString())){
                b.setActionCommand(e.getActionCommand());
            }
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

        duringRegister.getButton(ButtonSet.ButtonLabel.CARD.toString()).addActionListener(e -> {
            duringRegister.switchState(e);
            DisplayDispatcher.activeDisplayForPayment();
            ContentController.clearContent();
            for (JButton b : duringRegister.getButtons(ButtonSet.ButtonLabel.ADD.toString())){
                b.setActionCommand(e.getActionCommand());
            }
        });

        duringRegister.getButton(ButtonSet.ButtonLabel.FOOD_TICKETS.toString()).addActionListener(e -> {
            duringRegister.switchState(e);
            DisplayDispatcher.activeDisplayForPayment();
            ContentController.clearContent();
            for (JButton b : duringRegister.getButtons(ButtonSet.ButtonLabel.ADD.toString())){
                b.setActionCommand(e.getActionCommand());
            }
        });

        duringRegister.getButton(ButtonSet.ButtonLabel.VOUCHER.toString()).addActionListener(e -> {
            duringRegister.switchState(e);
            DisplayDispatcher.activeDisplayForPayment();
            ContentController.clearContent();
            for (JButton b : duringRegister.getButtons(ButtonSet.ButtonLabel.ADD.toString())){
                b.setActionCommand(e.getActionCommand());
            }
        });


        /*
        #Controlling buttons on bottom side of DuringArticles
         */
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> ViewManager.getInstance().showRegister());
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(addItemAction);
        ViewManager.getInstance().getDuringArticles().getButton(ButtonSet.ButtonLabel.REMOVE.toString()).addActionListener(removeItemAction);


        /*
        #Controlling buttons on bottom side of DuringCodeEnter
         */
        ViewManager.getInstance().getDuringCodeEnter().getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> {
            ViewManager.getInstance().showIdle();
            DisplayDispatcher.activeDisplayForAmount();
        });
        ViewManager.getInstance().getDuringCodeEnter().getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(e -> {
            try {
                returnTransactionAction.actionPerformed(e);
                ViewManager.getInstance().showReturnTransaction();
                DisplayDispatcher.activeDisplayForAmount();
            }catch (Exception exception){
                NotificationController.notifyObservers(exception.toString(),5000);
            }
        });

        /*
        #Controlling buttons on bottom side of DuringReturn
         */
        DuringReturnTransaction duringReturnTransaction = ViewManager.getInstance().getDuringReturn();
        duringReturnTransaction.getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(addItemAction);
        duringReturnTransaction.getButton(ButtonSet.ButtonLabel.REMOVE.toString()).addActionListener(removeItemAction);
        duringReturnTransaction.getButton(ButtonSet.ButtonLabel.CASH.toString()).addActionListener(duringReturnTransaction::switchState);
        duringReturnTransaction.getButton(ButtonSet.ButtonLabel.CARD.toString()).addActionListener(duringReturnTransaction::switchState);
        duringReturnTransaction.getButton(ButtonSet.ButtonLabel.CASH_BACK.toString()).addActionListener(new CashBackAction());
        duringReturnTransaction.getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(duringReturnTransaction::switchState);
    }

}
