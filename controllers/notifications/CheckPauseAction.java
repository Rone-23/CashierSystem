package controllers.notifications;

import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CheckPauseAction extends AbstractAction implements ContentObserver {

    private final String pauseCode;
    private String content;

    public CheckPauseAction(){
        ContentController.addObserver(this);
        pauseCode = String.format("%04d", (int)(Math.random() * 10000));
        System.out.println("UNLOCK CODE: " + pauseCode);
        ViewManager.getInstance().showPause();
        DisplayDispatcher.activeDisplayForCode();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pauseCode.equals(content)){
            ViewManager.getInstance().showIdle();
            ViewManager.getInstance().returnToDefault();
            ContentController.removeObserver(this);
            ViewManager.getInstance().getStatusBar().setLocked(false);
        }else{
            NotificationController.notifyObservers("Nesprávny kod.",1500);
        }
        ContentController.clearContent();
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }
}
