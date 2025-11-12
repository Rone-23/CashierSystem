package utility;

import assets.Constants;
import services.Item;
import views.Components.ArticleButton;
import views.Components.ChonkyArrowButton;
import views.Components.ChonkyButton;

import javax.swing.*;
import java.awt.*;

public class ButtonBuilder {

    public static JButton buildChonkyButton(String text, Color color){
        JButton jButton = new ChonkyButton(text, color);
        jButton.setName(text.toLowerCase());
        return jButton;
    }

    public static JButton buildChonkyButtonDisabled(Color color) {
        JButton jButton = new ChonkyButton(color);
        jButton.setEnabled(false);
        return jButton;
    }

    public static JButton buildChonkyArrowButton(Color color, Constants direction){
        return new ChonkyArrowButton(color, direction);
    }

    public static JButton buildArticleButton(Color color , Item item){
        JButton jButton = new ArticleButton(color, item);
        jButton.setName(item.getName());
        return jButton;
    }
}
