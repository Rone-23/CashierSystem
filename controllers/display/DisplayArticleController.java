package controllers.display;

import controllers.buttons.ArticleSelectAction;
import controllers.buttons.FavoriteArticleAction;
import controllers.notifications.NotificationController;
import controllers.panels.ViewManager;
import controllers.buttons.FilterObserver;
import controllers.transaction.OpenTransactionObserver;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
import utility.ButtonBuilder;
import views.Components.ArticleButton;
import views.Components.DisplayArticles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

public class DisplayArticleController implements OpenTransactionObserver, FilterObserver {

    private final DisplayArticles displayArticles = ViewManager.getInstance().getDuringArticles().getDisplayScrollableArticles();

    private final Map<String, List<ArticleButton>> categoryIndex = new HashMap<>();
    private final Map<String, List<ArticleButton>> subCategoryIndex = new HashMap<>();
    private final List<ArticleButton> favoritesIndex = new ArrayList<>();
    private final List<ArticleButton> allArticlesIndex = new ArrayList<>();

    private final Map<String, ArticleButton> buttonMasterMap = new HashMap<>();

    private String filterKeywordMain = "ALL";
    private String filterKeywordSecondary = "ALL";

    private final ArticleSelectAction articleSelectAction = new ArticleSelectAction();
    private final FavoriteArticleAction favoriteArticleAction = new FavoriteArticleAction(this::toggleFavoriteInCache);

    public DisplayArticleController() {
        initializeCache();
        refreshDisplay();
    }

    private void initializeCache() {
        try {
            Item[] allItems = SQL_Connect.getInstance().getAllItems();

            for (Item article : allItems) {
                ArticleButton btn = (ArticleButton) ButtonBuilder.buildArticleButton(assets.Colors.ARTICLE_BUTTON.getColor(), article);
                btn.setName(article.getName().toLowerCase());

                btn.addActionListener(articleSelectAction);
                btn.addStarActionListener(favoriteArticleAction);
                btn.setStarred(article.getIsFavorite());

                String imagePath = SQL_Connect.getInstance().getPathToImage(SQL_Connect.getInstance().getArticleID(article.getName()));
                if (imagePath != null) {
                    btn.setItemImage(imagePath);
                }

                buttonMasterMap.put(article.getName().toLowerCase(), btn);
                allArticlesIndex.add(btn);

                if (article.getIsFavorite()) {
                    favoritesIndex.add(btn);
                }

                String cat = article.getCategory();
                if (cat != null) {
                    categoryIndex.computeIfAbsent(cat, k -> new ArrayList<>()).add(btn);
                }

                String subCat = article.getSubcategory();
                if (subCat != null) {
                    subCategoryIndex.computeIfAbsent(subCat, k -> new ArrayList<>()).add(btn);
                }
            }
        } catch (SQLException e) {
            NotificationController.notifyObservers("Cache Error: " + e.getMessage(), 5000);
        }
    }

    private void refreshDisplay() {
        displayArticles.clear();
        articleSelectAction.deselectArticle();

        List<ArticleButton> buttonsToShow;

        if ("OBLUBENE".equals(filterKeywordMain)) {
            buttonsToShow = favoritesIndex;
        } else if ("ALL".equals(filterKeywordMain)) {
            buttonsToShow = allArticlesIndex;
        } else if (!"ALL".equals(filterKeywordSecondary)) {
            buttonsToShow = subCategoryIndex.getOrDefault(filterKeywordSecondary, new ArrayList<>());
        } else {
            buttonsToShow = categoryIndex.getOrDefault(filterKeywordMain, new ArrayList<>());
        }

        for (ArticleButton btn : buttonsToShow) {
            displayArticles.addArticle(btn);
        }

        displayArticles.revalidate();
        displayArticles.repaint();
    }

    // Observer

    @Override
    public void updateMainFilter(String filterKeyword) {
        this.filterKeywordMain = filterKeyword;
        this.filterKeywordSecondary = "ALL";
        refreshDisplay();
    }

    @Override
    public void updateSecondaryFilter(String filterKeyword) {
        this.filterKeywordSecondary = filterKeyword;
        refreshDisplay();
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        refreshDisplay();
    }

    @Override
    public void onItemAdd(Item item) {
        ArticleButton b = buttonMasterMap.get(item.getName().toLowerCase());
        if (b != null) {
            b.setItemAmount(item.getAmount());
            b.repaint();
        }
    }

    @Override
    public void onDestroy() {
        articleSelectAction.deselectArticle();
        for (ArticleButton b : buttonMasterMap.values()) {
            b.resetItemAmount();
        }
    }

    public void toggleFavoriteInCache() {
        for(ArticleButton btn : allArticlesIndex){
            if (btn.isStarred()) {
                if (!favoritesIndex.contains(btn)) favoritesIndex.add(btn);
            } else {
                favoritesIndex.remove(btn);
            }
        }
    }
}
