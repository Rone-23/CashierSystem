package controllers.buttons;

import assets.ButtonSet;
import assets.Constants;
import assets.ThemeManager;
import controllers.notifications.CheckPauseAction;
import controllers.notifications.NotificationController;
import controllers.transaction.CashBackAction;
import controllers.transaction.ContentController;
import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import services.Customers.ValidateCustomerAction;
import services.Users.CashierSession;
import services.Users.LoginCashierAction;
import utility.tutorial.Tutorial;
import views.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class UtilityController {
    AddItemAction addItemAction = new AddItemAction();
    RemoveItemAction removeItemAction = new RemoveItemAction();
    CodeEnterAction codeEnterAction = new CodeEnterAction();
    ValidateCustomerAction validateCustomerAction = new ValidateCustomerAction();
    CheckPauseAction checkPauseAction;
    LoginCashierAction loginCashierAction = new LoginCashierAction();
    public UtilityController() {
        /*
        #Controlling all the buttons that are on the right side in DuringIdle
         */
        DuringIdle duringIdle = ViewManager.getInstance().getDuringIdle();
        duringIdle.getButton(ButtonSet.ButtonLabel.BEGIN.toString()).addActionListener(_ -> {
            ViewManager.getInstance().showArticles();
            OpenTransactionManager.getInstance().getOpenTransaction();
        });
        duringIdle.getButton(ButtonSet.ButtonLabel.RETURN.toString()).addActionListener(_ -> {
            ViewManager.getInstance().getDuringCodeEnter().getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).setActionCommand(ButtonSet.ButtonLabel.RETURN.toString());
            ViewManager.getInstance().getDuringCodeEnter().setMode(Constants.RECEIPT);
            ViewManager.getInstance().getDuringCodeEnter().setExplanationText(
                    "Vrátenie tovaru",
                    "Pre vrátenie tovaru zadajte číslo transackcie. <br><br>" +
                            "• Číslo nájdete na pôvodnom pokladničnom bloku.<br>"
            );
            ViewManager.getInstance().showCodeEnter();
            DisplayDispatcher.activeDisplayForCode();
        });
        duringIdle.getButton(ButtonSet.ButtonLabel.COPY_RECEIPT.toString()).addActionListener(_ -> {
            ViewManager.getInstance().getDuringCodeEnter().getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).setActionCommand(ButtonSet.ButtonLabel.COPY_RECEIPT.toString());
            ViewManager.getInstance().getDuringCodeEnter().setMode(Constants.RECEIPT);
            ViewManager.getInstance().getDuringCodeEnter().setExplanationText(
                    "Kópia dokladu",
                    "Pre vytlačenie kópie zadajte číslo transakcie. <br><br>" +
                            "• Číslo nájdete na pôvodnom pokladničnom bloku.<br>" +
                            "• Kópia bude vytlačená okamžite po potvrdení."
            );
            ViewManager.getInstance().showCodeEnter();
            DisplayDispatcher.activeDisplayForCode();

        });
        duringIdle.getButton(ButtonSet.ButtonLabel.GENERATE_VOUCHER.toString()).addActionListener(_ -> {
            ViewManager.getInstance().getDuringCodeEnter().getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).setActionCommand(ButtonSet.ButtonLabel.GENERATE_VOUCHER.toString());
            ViewManager.getInstance().getDuringCodeEnter().setMode(Constants.CUSTOMER);
            ViewManager.getInstance().getDuringCodeEnter().setExplanationText(
                    "Generovanie poukážky",
                    "Vytvorenie novej darčekovej poukážky pre zákazníka.<br><br>" +
                            "• Zadajte ID poukážky vytlačené na zadnej strane.<br>" +
                            "• Po potvrdení bude poukážka aktivovaná a pripravená na platbu transakcie."
            );
            ViewManager.getInstance().showCodeEnter();
            DisplayDispatcher.activeDisplayForCode();
        });
        duringIdle.getButton(ButtonSet.ButtonLabel.CREATE_CARD.toString()).addActionListener(_ -> {
            ViewManager.getInstance().getDuringCodeEnter().getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).setActionCommand(ButtonSet.ButtonLabel.CREATE_CARD.toString());
            ViewManager.getInstance().getDuringCodeEnter().setMode(Constants.CUSTOMER);
            ViewManager.getInstance().getDuringCodeEnter().setExplanationText(
                    "Nová zákaznícka karta",
                    "Registrácia novej zákazníckej karty.<br><br>" +
                            "• Naskenujte čiarový kód (alebo ručne zadajte 7 ciferné číslo karty).<br>" +
                            "• Uistite sa, že karta ešte nie je v systéme evidovaná, inak bude táto požiadavka zamietnutá."
            );
            ViewManager.getInstance().showCodeEnter();
            DisplayDispatcher.activeDisplayForCode();
        });
        duringIdle.getButton(ButtonSet.ButtonLabel.PAUSE.toString()).addActionListener(_ -> {
            DuringPause duringPause = ViewManager.getInstance().getDuringPause();
            duringPause.getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).removeActionListener(checkPauseAction);
            checkPauseAction = new CheckPauseAction();
            duringPause.getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).addActionListener(checkPauseAction);
        });
        duringIdle.getButton(ButtonSet.ButtonLabel.THEME_BUTTON.toString()).addActionListener(e -> {
            JButton jButton = (JButton) e.getSource();
            boolean isDarkMode = ThemeManager.getInstance().toggleTheme();
            if(isDarkMode){
                jButton.setText("Light");
            }else{
                jButton.setText("Dark");
            }
        });
        duringIdle.getButton(ButtonSet.ButtonLabel.PADAVAN.toString()).addActionListener(e -> {
            Tutorial tutorial = new Tutorial();
            utility.tutorial.TutorialManager.getInstance().startTutorial(tutorial.getTutorialSteps());
        });


        /*
        #Controlling all the buttons that are on the right side in DuringRegister
         */
        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();
        duringRegister.getButton(ButtonSet.ButtonLabel.ARTICLES.toString()).addActionListener(_ -> ViewManager.getInstance().showArticles());
        duringRegister.getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()).addActionListener(addItemAction);
        duringRegister.getButton(ButtonSet.ButtonLabel.STORNO.toString()).addActionListener(removeItemAction);
        duringRegister.getButton(ButtonSet.ButtonLabel.VALIDATE_CARD.toString()).addActionListener(validateCustomerAction);

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
                duringRegister.getButton(buttonName).setActionCommand(ButtonSet.ButtonLabel.CASH.toString());
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

        duringRegister.getButton(ButtonSet.ButtonLabel.USE_VOUCHER.toString()).addActionListener(e -> {
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
        DuringArticles duringArticles = ViewManager.getInstance().getDuringArticles();
        duringArticles.getButton(ButtonSet.ButtonLabel.ADD.toString()).addActionListener(addItemAction);
        duringArticles.getButton(ButtonSet.ButtonLabel.REMOVE.toString()).addActionListener(removeItemAction);
        duringArticles.getButton(ButtonSet.ButtonLabel.SEARCH.toString()).addActionListener(_ ->ViewManager.getInstance().showKeyboard());
        duringArticles.getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> ViewManager.getInstance().showRegister());


        /*
        #Controlling buttons on bottom side of DuringCodeEnter
         */
        DuringCodeEnter duringCodeEnter = ViewManager.getInstance().getDuringCodeEnter();
        duringCodeEnter.getButton(ButtonSet.ButtonLabel.EXIT.toString()).addActionListener(_ -> {
            ViewManager.getInstance().showIdle();
            ContentController.clearContent();
            DisplayDispatcher.activeDisplayForAmount();
        });
        duringCodeEnter.getButton(ButtonSet.ButtonLabel.CONFIRM.toString()).addActionListener(codeEnterAction);


        /*
        #Controlling buttons on bottom side of DuringCodeEnter
         */
        DuringPause duringPause = ViewManager.getInstance().getDuringPause();
        duringPause.getButton(ButtonSet.ButtonLabel.LOGIN.toString()).addActionListener(loginCashierAction);

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

        applySecurityLock(ViewManager.getInstance().getMainFrame());
    }

    private void applySecurityLock(java.awt.Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton button) {
                String name = button.getName();

                boolean isExempt = name != null && (
                        name.equalsIgnoreCase(assets.ButtonSet.ButtonLabel.LOGIN.toString()) ||
                                name.equalsIgnoreCase(assets.ButtonSet.ButtonLabel.THEME_BUTTON.toString()) ||
                                name.equalsIgnoreCase(assets.ButtonSet.ButtonLabel.PAUSE.toString()) ||
                                name.equalsIgnoreCase(assets.ButtonSet.ButtonLabel.PADAVAN.toString()) ||
                                name.matches("\\d+") ||
                                name.equalsIgnoreCase(assets.ButtonSet.ButtonLabel.DELETE.toString()) ||
                                name.equalsIgnoreCase(assets.ButtonSet.ButtonLabel.BACKSPACE.toString())
                );

                if (!isExempt) {
                    ActionListener[] listeners = button.getActionListeners();
                    for (ActionListener al : listeners) {
                        button.removeActionListener(al);

                        button.addActionListener(e -> {
                            if (CashierSession.getCurrentCashierId() == -1) {
                                NotificationController.notifyObservers("Prosím prihláste používateľa!", 5000);
                            } else {
                                al.actionPerformed(e);
                            }
                        });
                    }
                }
            } else if (c instanceof Container) {
                applySecurityLock((Container) c);
            }
        }
    }
}
