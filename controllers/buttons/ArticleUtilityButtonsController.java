package controllers.buttons;

import controllers.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticleUtilityButtonsController {

    public ArticleUtilityButtonsController(){
        ViewManager.getInstance().getDuringArticles().getUtilityButton("cancel").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewManager.getInstance().showRegister();
            }
        });
    }


}
