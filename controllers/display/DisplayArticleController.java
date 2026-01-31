package controllers.display;

import controllers.buttons.ArticleAction;
import controllers.panels.ViewManager;
import controllers.buttons.FilterObserver;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
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
    private final ArticleAction articleAction = new ArticleAction();
    public DisplayArticleController(){
        createArticles();
    }

    public void createArticles(){
        articleAction.deselectArticle();
        try {
            articles =  SQL_Connect.getInstance().getAllItems();
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(articleAction);
        }
    }

    public void createArticles(String type){
        articleAction.deselectArticle();
        try {
            articles =  SQL_Connect.getInstance().getItems(type);
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(articleAction);
        }
    }

    public void createArticles(String type, String subtype){
        articleAction.deselectArticle();
        try {
            articles =  SQL_Connect.getInstance().getItems(type, subtype);
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(articleAction);
        }
    }

    //Observer

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        articleAction.deselectArticle();
    }

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
}
