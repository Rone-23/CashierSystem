package controllers.notifications;

import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import controllers.transaction.ContentController;
import controllers.transaction.ContentObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CheckPauseAction extends AbstractAction implements ContentObserver {

    private final String pauseCode;
    private String content;

    public CheckPauseAction(){
        ContentController.addObserver(this);
        pauseCode = String.format("%04d", (int)(Math.random() * 9000)+1000);
        System.out.println("UNLOCK CODE: " + pauseCode);
        String path = "./Receipts/SECRET_CODE.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(String.format("Unlock code: %s",pauseCode));
        } catch (IOException e) {
            System.err.println("Error writing receipt file: " + e.getMessage());
        }
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
            NotificationController.notifyObservers("Nesprávny kód.",1500);
        }
        ContentController.clearContent();
    }

    @Override
    public void notifyContentUpdate(String content) {
        this.content = content;
    }
}
