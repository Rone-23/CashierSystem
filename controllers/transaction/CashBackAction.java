package controllers.transaction;

import controllers.panels.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CashBackAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        OpenTransactionManager.getInstance().addPayment(e);
        ViewManager.getInstance().showIdle();
    }
}
