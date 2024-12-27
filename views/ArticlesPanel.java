package views;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ArticlesPanel {
    JPanel mainPanel;
    JPanel topPanel;
    JPanel articlePanel;
    JPanel bottomPanel;
    JButton[] articleButtons = new JButton[25];
    JButton arrowLeft;
    JButton arrowRight;
    JButton[] sectionButtons = new JButton[5];
    JButton plusButton;
    JButton minusButton;
    JButton saerchButton;
    JButton cancelButton;
    JLabel amountLabel;
    Map<String, JButton> buttons = new HashMap<>();
    public ArticlesPanel(){
        this.mainPanel = PanelBuilder.createPanel(new GridBagLayout());
        this.topPanel = PanelBuilder.createPanel(new GridBagLayout(),  new EmptyBorder(10,30,0,30));
        this.articlePanel = PanelBuilder.createPanel(new GridBagLayout(), new EmptyBorder(10,30,0,30));
        this.bottomPanel = PanelBuilder.createPanel(new GridBagLayout(), new EmptyBorder(10,30,0,30));


        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        gbc.weighty = Constants.ARTICLE_SECTION_BUTTONS_HEIGHT.getValue();
        makeSectionButtons();
        this.mainPanel.add(this.topPanel, gbc);

        gbc.gridy=1;
        gbc.weighty = Constants.ARTICLE_BUTTONS_HEIGHT.getValue();
        makeArticleButtons();
        this.mainPanel.add(this.articlePanel, gbc);

        gbc.gridy=2;
        gbc.weighty = Constants.ARTICLE_UTILITY_BUTTONS_HEIGHT.getValue();
        makeUtilityButtons();
        this.mainPanel.add(this.bottomPanel, gbc);
        this.mainPanel.setVisible(false);
    }

    private void makeArticleButtons(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        gbc.gridwidth = 1;

        gbc.weighty = Constants.BUTTONS_FOR_USE_HEIGHT.getValue();
        gbc.weightx = Constants.BUTTONS_FOR_USE_WIDTH.getValue();
        for (int numberIndex = 0; numberIndex < 25; numberIndex++) {
            this.articleButtons[numberIndex] = ButtonBuilder.buildButton();
            this.buttons.put("articleButton_"+numberIndex, this.articleButtons[numberIndex]);
            this.articlePanel.add(this.articleButtons[numberIndex],gbc);
            gbc.gridx++;
            if ((numberIndex + 1) % 5 == 0) {
                gbc.gridx=0;
                gbc.gridy++;
            }
        }
    }

    private void makeSectionButtons(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        gbc.weightx = Constants.ARROW_WIDTH.getValue();
        this.arrowLeft = ButtonBuilder.buildButton();
        this.buttons.put("arrowLeft",this.arrowLeft);
        this.topPanel.add(this.arrowLeft, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        for(int numberIndex = 0; numberIndex<5; numberIndex++){
            this.sectionButtons[numberIndex] = ButtonBuilder.buildButton();
            this.buttons.put("sectionButton_"+numberIndex,this.sectionButtons[numberIndex]);
            this.topPanel.add(this.sectionButtons[numberIndex], gbc);
            gbc.gridx++;
        }

        gbc.weightx = Constants.ARROW_WIDTH.getValue();
        gbc.gridx = 6;
        this.arrowRight = ButtonBuilder.buildButton();
        this.buttons.put("arrowRight",this.arrowRight);
        this.topPanel.add(this.arrowRight, gbc);
    }

    private void makeUtilityButtons(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        gbc.weightx = Constants.MATH_OPERATION_BUTTON_HEIGHT.getValue();
        this.plusButton = ButtonBuilder.buildButton();
        this.buttons.put("plusButton", this.plusButton);
        this.bottomPanel.add(plusButton,gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        this.amountLabel = new JLabel("mnozstvo", SwingConstants.CENTER);
        this.bottomPanel.add(this.amountLabel,gbc);

        gbc.weightx = Constants.MATH_OPERATION_BUTTON_HEIGHT.getValue();
        gbc.gridx = 2;
        this.minusButton = ButtonBuilder.buildButton();
        this.buttons.put("minusButton", this.minusButton);
        this.bottomPanel.add(this.minusButton,gbc);

        gbc.weightx = 1;
        gbc.gridx = 3;
        this.saerchButton = ButtonBuilder.buildButton();
        this.buttons.put("saerchButton", this.saerchButton);
        this.bottomPanel.add(this.saerchButton,gbc);

        gbc.gridx = 4;
        this.cancelButton = ButtonBuilder.buildButton();
        this.buttons.put("cancelButton", this.cancelButton);
        this.bottomPanel.add(this.cancelButton,gbc);
    }

    public JPanel getMainPanel(){ return  this.mainPanel;}

    public void setVisible(Boolean condition){this.mainPanel.setVisible(condition);}

    public Map<String, JButton> getButtons(){ return this.buttons;}
}
