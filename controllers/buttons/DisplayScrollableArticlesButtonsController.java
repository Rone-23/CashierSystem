package controllers.buttons;

import controllers.ViewManager;
import controllers.openTransaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class DisplayScrollableArticlesButtonsController implements OpenTransactionObserver {
    private final Map<String, JButton> buttons = ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().getButtons();
    private OpenTransaction openTransaction;
    private Item[] articles;

    public DisplayScrollableArticlesButtonsController(){

        try {
            createArticles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(Item item : articles){
            buttons.get(item.getName().toLowerCase()).addActionListener(e -> openTransaction.addItem(item));
        }

    }

    private void createArticles() throws SQLException {
        String[] types= SQL_Connect.getInstance().getTypes();
        articles =  SQL_Connect.getInstance().getItems(types[1]);

        for(Item article : articles){
            ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles().addArticle(article);
        }
    }


    @Override
    public void onCreate(OpenTransaction openTransaction) {
        this.openTransaction = openTransaction;
    }
}
