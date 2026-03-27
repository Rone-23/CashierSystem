package controllers.display;

import controllers.buttons.ArticleSelectAction;
import controllers.buttons.FavoriteArticleAction;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.buttons.FilterObserver;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.SQL_Connect;
import views.Components.ArticleButton;
import views.Components.DisplayArticles;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class DisplayArticleController implements OpenTransactionObserver, FilterObserver {
    private final DisplayArticles displayArticles = ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles();
    private Item[] articles;
    private String filterKeywordMain;
    private String filterKeywordSecondary;

    private final Map<String, JToggleButton> buttons = ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().getButtons();
    private final ArticleSelectAction articleSelectAction = new ArticleSelectAction();
    private final FavoriteArticleAction favoriteArticleAction = new FavoriteArticleAction();
    public DisplayArticleController(){
        createArticles();
    }

    public void createArticles(){
        articleSelectAction.deselectArticle();
        try {
            articles =  SQL_Connect.getInstance().getAllItems();
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
            buttons.get(article.getName().toLowerCase()).addActionListener(articleSelectAction);
            try{
                ArticleButton articleButton = (ArticleButton) buttons.get(article.getName().toLowerCase());
                articleButton.addStarActionListener(favoriteArticleAction);
                articleButton.setStarred(article.getIsFavorite());
                articleButton.setItemImage("/Images/OIP.png");
            }catch (ClassCastException e){
                NotificationController.notifyObservers(e.toString(),5000);
            }
        }
    }

    public void createArticles(String type){
        if(type.equals("OBLUBENE")){
            articleSelectAction.deselectArticle();
            try {
                articles = SQL_Connect.getInstance().getAllItems();
            } catch (SQLException e) {
                articles = new Item[0];
            }

            for (Item article : articles) {
                if(article.getIsFavorite()){
                    ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
                }
                if(article.getIsFavorite()) {
                    buttons.get(article.getName().toLowerCase()).addActionListener(articleSelectAction);
                    try {
                        ArticleButton articleButton = (ArticleButton) buttons.get(article.getName().toLowerCase());
                        articleButton.addStarActionListener(favoriteArticleAction);
                        articleButton.setStarred(article.getIsFavorite());
                    } catch (ClassCastException e) {
                        NotificationController.notifyObservers(e.toString(), 5000);
                    }
                }
            }
        }else {
            articleSelectAction.deselectArticle();
            try {
                articles = SQL_Connect.getInstance().getItems(type);
            } catch (SQLException e) {
                articles = new Item[0];
            }

            for (Item article : articles) {
                ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
                buttons.get(article.getName().toLowerCase()).addActionListener(articleSelectAction);
                try {
                    ArticleButton articleButton = (ArticleButton) buttons.get(article.getName().toLowerCase());
                    articleButton.addStarActionListener(favoriteArticleAction);
                    articleButton.setStarred(article.getIsFavorite());
                } catch (ClassCastException e) {
                    NotificationController.notifyObservers(e.toString(), 5000);
                }
            }
        }
    }

    public void createArticles(String type, String subtype){
        articleSelectAction.deselectArticle();
        try {
            articles =  SQL_Connect.getInstance().getItems(type, subtype);
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
            buttons.get(article.getName().toLowerCase()).addActionListener(articleSelectAction);
            try{
                ArticleButton articleButton = (ArticleButton) buttons.get(article.getName().toLowerCase());
                articleButton.addStarActionListener(favoriteArticleAction);
                articleButton.setStarred(article.getIsFavorite());
            }catch (ClassCastException e){
                NotificationController.notifyObservers(e.toString(),5000);
            }
        }
    }

    //Observer
    @Override
    public void updateMainFilter(String filterKeyword) {
        filterKeywordMain = filterKeyword;
        displayArticles.clear();
        createArticles(filterKeywordMain);
    }

    @Override
    public void updateSecondaryFilter(String filterKeyword) {
        filterKeywordSecondary = filterKeyword;
        displayArticles.clear();
        createArticles(filterKeywordMain,filterKeywordSecondary);
    }

    @Override
    public void onItemAdd(Item item) {
        ArticleButton b = (ArticleButton) buttons.get(item.getName().toLowerCase());
        b.setItemAmount(item.getAmount());
        b.repaint();
    }

    @Override
    public void onDestroy() {
        articleSelectAction.deselectArticle();
        for(ArticleButton b : buttons.values().toArray(new ArticleButton[0])){
            b.resetItemAmount();
        }
    }
}
