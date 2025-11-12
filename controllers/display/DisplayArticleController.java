package controllers.display;

import controllers.panels.ViewManager;
import controllers.buttons.FilterObserver;
import controllers.transaction.OpenTransactionManager;
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
    private OpenTransaction openTransaction = OpenTransactionManager.getInstance().getOpenTransaction();
    private Item[] articles;
    private String filterKeywordMain;
    private String filterKeywordSecondary;

    private final Map<String, JButton> buttons = ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().getButtons();

    public DisplayArticleController(){
        createArticles();
    }

    public void createArticles(){
        try {
            articles =  SQL_Connect.getInstance().getAllItems();
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(e -> openTransaction.addItem(item));
        }
    }

    public void createArticles(String type){
        try {
            articles =  SQL_Connect.getInstance().getItems(type);
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(e -> openTransaction.addItem(item));
        }
    }

    public void createArticles(String type, String subtype){
        try {
            articles =  SQL_Connect.getInstance().getItems(type, subtype);
        } catch (SQLException e) {
            articles = new Item[0];
        }

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(e -> openTransaction.addItem(item));
        }
    }

    //Observer

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        this.openTransaction = openTransaction;
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
