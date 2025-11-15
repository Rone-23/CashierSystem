package controllers.buttons;

import services.SQL_Connect;
import views.Components.ArticleFilterButtonCluster;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterController implements FilterObserver{

    private static final List<FilterObserver> observerList = new ArrayList<>();
    public FilterController(ArticleFilterButtonCluster articleFilterButtonCluster) {

        //Adding functionality to secondary filters
        for(JButton button : articleFilterButtonCluster.getMainFilterButtons()){
            button.addActionListener(e -> {
                try {
                    articleFilterButtonCluster.setButtonNamesSecondaryFilter(SQL_Connect.getInstance().getSubTypes(button.getName().toUpperCase()));

                    for(JButton buttonSecondaryFilter: articleFilterButtonCluster.getSecondaryFilterButtons()){
                        buttonSecondaryFilter.addActionListener(e1 -> {
                            for(FilterObserver observer: observerList){
                                //Returns in upper case for because of database
                                observer.updateSecondaryFilter(buttonSecondaryFilter.getName().toUpperCase());
                            }
                        });
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                for(FilterObserver observer: observerList){
                    //Returns in upper case for because of database
                    observer.updateMainFilter(button.getName().toUpperCase());
                }
            });
        }
    }


    //Observer
    public static void addObserver(FilterObserver observer) {
        observerList.add(observer);
    }
    public static void removeObserver(FilterObserver observer) {
        observerList.remove(observer);
    }

}
