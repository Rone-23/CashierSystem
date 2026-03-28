package controllers.buttons;

import controllers.notifications.NotificationController;
import services.SQL_Connect;
import views.Components.ArticleButton;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FavoriteArticleAction extends AbstractAction{

    private final Runnable onFavoriteIsChanged;
    public FavoriteArticleAction(Runnable onFavoriteIsChanged){
        this.onFavoriteIsChanged = onFavoriteIsChanged;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
            ArticleButton articleButton = (ArticleButton) e.getSource();
            int articleID = SQL_Connect.getInstance().getArticleID(articleButton.getItem().getName());
            if(articleButton.isStarred()){
                SQL_Connect.getInstance().connectFavoriteArticle(1, articleID);
            }else {
                SQL_Connect.getInstance().disconnectFavoriteArticle(1, articleID);
            }
            if(onFavoriteIsChanged != null){
                onFavoriteIsChanged.run();
            }
        } catch (Exception er) {
            NotificationController.notifyObservers(er.toString(),5000);
            System.out.print(er);
        }
    }
}
