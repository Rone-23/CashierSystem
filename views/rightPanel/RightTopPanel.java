package views.rightPanel;

import assets.Colors;
import utility.ButtonBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class RightTopPanel {
    private final JPanel mainPanel = PanelBuilder.createPanel(new FlowLayout(FlowLayout.RIGHT), Colors.GRAY.getColor(),null);
    private final JButton articleButton = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(), "Articles");
    public RightTopPanel(){
        this.articleButton.setPreferredSize(new Dimension(200,100));
        this.mainPanel.add(articleButton);
    }

    public JPanel getMainPanel(){
        return this.mainPanel;
    }

    public JButton getArticleButton(){
        return this.articleButton;
    }
}



