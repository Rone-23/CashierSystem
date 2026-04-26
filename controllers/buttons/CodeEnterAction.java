package controllers.buttons;

import assets.ButtonSet;
import assets.Colors;
import controllers.display.DisplayDispatcher;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.transaction.OpenTransactionManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;
import services.SQL_Connect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class CodeEnterAction extends AbstractAction implements ContentObserver {
    private String content;
    public CodeEnterAction(){
        ContentController.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        //CREATING CUSTOMER CARDS
        if(ButtonSet.ButtonLabel.CREATE_CARD.toString().equals(e.getActionCommand())){
            if (content == null || !content.matches("\\d{7}")) {
                NotificationController.notifyObservers("Karta musí obsahovať presne 7 čísel!", 4000);
                ContentController.clearContent();
                return;
            }

            try {
                SQL_Connect.getInstance().createNewCustomerCard(Integer.parseInt(content), "Držiteľ karty: " + content);
                NotificationController.notifyObservers("Zákaznícka karta " + content + " úspešne vytvorená!", 4000, Colors.GREEN.getColor());
                ContentController.clearContent();
                ViewManager.getInstance().showIdle();
                ViewManager.getInstance().returnToDefault();
            } catch (SQLException ex) {
                NotificationController.notifyObservers(ex.getMessage(), 5000);
                ContentController.clearContent();
            }
            return;
        }

        //GENERATING VOUCHERS
        if(ButtonSet.ButtonLabel.GENERATE_VOUCHER.toString().equals(e.getActionCommand())){
            if (content == null || !content.matches("\\d{7}")) {
                NotificationController.notifyObservers("Číslo poukážky musí obsahovať presne 7 čísel!", 4000, Colors.YELLOW.getColor());
                ContentController.clearContent();
                return;
            }

            if (!utility.LuhnValidator.isValid(content)) {
                NotificationController.notifyObservers("Neplatný formát karty!", 5000);
                ContentController.clearContent();
                return;
            }

            try {
                int voucherId = Integer.parseInt(content);
                SQL_Connect.getInstance().activateVoucher(voucherId);

                NotificationController.notifyObservers("Poukážka " + content + " úspešne aktivovaná!", 4000, Colors.GREEN.getColor());
                ContentController.clearContent();
                ViewManager.getInstance().showIdle();
                ViewManager.getInstance().returnToDefault();
            } catch (SQLException ex) {
                NotificationController.notifyObservers(ex.getMessage(), 5000);
                ContentController.clearContent();
            }
            return;
        }


        try {
            int insertedTransactionID = Integer.parseInt(content);
            OpenTransactionManager.getInstance().loadHistoricalTransaction(
                    insertedTransactionID,
                    SQL_Connect.getInstance().getDateOfTransaction(insertedTransactionID),
                    SQL_Connect.getInstance().getAllArticlesFromPastTransaction(insertedTransactionID)
            );
            //RETURN TRANSACTION
            if(ButtonSet.ButtonLabel.RETURN.toString().equals(e.getActionCommand())){
                ViewManager.getInstance().showReturnTransaction();
                DisplayDispatcher.activeDisplayForAmount();
            } else{
                //COPY RECEIPT
                OpenTransactionManager.getInstance().addPayment(e);
            }
        } catch (SQLException | IllegalStateException ex) {
            NotificationController.notifyObservers(ex.getMessage(),5000);
            ContentController.clearContent();
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }
}
