package views.leftPanel;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.DisplayBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel {
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final JScrollPane topDisplay = DisplayBuilder.buildDisplay(Colors.GRAY.getColor());
    private final JScrollPane articleDisplay = DisplayBuilder.buildDisplay(Colors.YELLOW_DISPLAY.getColor());
//    private final JScrollPane scrollArticleDisplay = new JScrollPane(articleDisplay);
    private final JButton buttonUp = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),"UP");
    private final JButton buttonDown = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),"DO");
    private final JButton buttonMinimize = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),"MI");
    private final JScrollPane toPayDisplay = DisplayBuilder.buildDisplay(Colors.YELLOW_DISPLAY.getColor());

    public DisplayPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //adding top display
        gbc.gridwidth = 5;
        gbc.weighty = Constants.TOP_DISPLAY_HEIGHT.getValue();
        gbc.weightx = Constants.TOP_DISPLAY_WIDTH.getValue();
        mainPanel.add(topDisplay,gbc);

        articleDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //Adding main article display
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        gbc.weighty = Constants.ARTICLE_DISPLAY_HEIGHT.getValue();
        gbc.weightx = Constants.ARTICLE_DISPLAY_WIDTH.getValue();
        mainPanel.add(articleDisplay,gbc);

        //Adding navigation buttons
        //Up
        gbc.gridwidth=1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = Constants.NAVIGATION_BUTTONS_HEIGHT.getValue();
        gbc.weightx = Constants.NAVIGATION_BUTTONS_WIDTH.getValue();
        mainPanel.add(buttonUp,gbc);
        //Down
        gbc.gridx = 1;
        gbc.weighty = Constants.NAVIGATION_BUTTONS_HEIGHT.getValue();
        gbc.weightx = Constants.NAVIGATION_BUTTONS_WIDTH.getValue();
        mainPanel.add(buttonDown,gbc);
        //Minimize
        gbc.gridx = 2;
        gbc.weighty = Constants.NAVIGATION_BUTTONS_HEIGHT.getValue();
        gbc.weightx = Constants.NAVIGATION_BUTTONS_WIDTH.getValue();
        mainPanel.add(buttonMinimize,gbc);

        //Adding display to show amount to pay
        gbc.gridwidth = 2;
        gbc.gridx = 3;
        gbc.weighty = Constants.TO_PAY_DISPLAY_HEIGHT.getValue();
        gbc.weightx = Constants.TO_PAY_DISPLAY_WIDTH.getValue();
        mainPanel.add(toPayDisplay,gbc);
    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

    public void updateArticleDisplay(String textToUpdate){
        JTextArea display = (JTextArea) this.articleDisplay.getViewport().getView();
        display.setText(textToUpdate);
    }

    public void updateToPay(String textToUpdate){
        JTextArea display = (JTextArea) this.toPayDisplay.getViewport().getView();
        display.setText(textToUpdate);
    }
}
