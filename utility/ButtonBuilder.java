package utility;

import assets.Colors;
import assets.Constants;
import services.Item;
import views.Components.ArticleButton;
import views.Components.ChonkyArrowButton;
import views.Components.ChonkyButton;

import javax.swing.*;

public class ButtonBuilder {

    public static JButton buildChonkyButton(String text, Colors colorEnum){
        JButton jButton = new ChonkyButton(text, colorEnum);
        jButton.setName(text.toLowerCase());
        return jButton;
    }

    public static JButton buildChonkyButtonDisabled(Colors colorEnum) {
        JButton jButton = new ChonkyButton(colorEnum);
        jButton.setEnabled(false);
        return jButton;
    }

    public static JButton buildChonkyArrowButton(Colors colorEnum, Constants direction){
        return new ChonkyArrowButton(colorEnum, direction);
    }

    public static JToggleButton buildArticleButton(Colors colorEnum , Item item){
        JToggleButton jButton = new ArticleButton(colorEnum, item);
        jButton.setName(item.getName());
        return jButton;
    }
}
