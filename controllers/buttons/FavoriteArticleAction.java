package controllers.buttons;

import controllers.notifications.NotificationController;
import services.SQL_Connect;
import services.Users.CashierObserver;
import services.Users.CashierSession;
import views.Components.ArticleButton;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FavoriteArticleAction extends AbstractAction implements CashierObserver {

    private final Runnable onFavoriteIsChanged;
    private int cashierId = -1;
    public FavoriteArticleAction(Runnable onFavoriteIsChanged){
        CashierSession.addObserver(this);
        this.onFavoriteIsChanged = onFavoriteIsChanged;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
            ArticleButton articleButton = (ArticleButton) e.getSource();
            int articleID = SQL_Connect.getInstance().getArticleID(articleButton.getItem().getName());
            if(articleButton.isStarred()){
                SQL_Connect.getInstance().connectFavoriteArticle(cashierId, articleID);
            }else {
                SQL_Connect.getInstance().disconnectFavoriteArticle(cashierId, articleID);
            }
            if(onFavoriteIsChanged != null){
                onFavoriteIsChanged.run();
            }
        } catch (Exception er) {
            NotificationController.notifyObservers(er.toString(),5000);
            System.out.print(er);
        }
    }


    @Override
    public void onCashierLogin(int cashierId) {
        this.cashierId = cashierId;
    }
}
