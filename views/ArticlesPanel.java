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

import static org.junit.Assert.assertTrue;

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
    JButton searchButton;
    JButton cancelButton;
    JLabel amountLabel;
    String[] articleButtonNames;
    String[] sectionButtonNames;
    Map<String, JButton> buttons = new HashMap<>();
    public ArticlesPanel(String[] articleButtonNames, String[] sectionButtonNames){
        this.articleButtonNames = articleButtonNames;
        this.sectionButtonNames = sectionButtonNames;
        this.mainPanel = PanelBuilder.createPanel(new GridBagLayout(),Color.white,null);
        this.topPanel = PanelBuilder.createPanel(new GridBagLayout(),  new EmptyBorder(10,30,0,30));
        this.articlePanel = PanelBuilder.createPanel(new GridLayout(0,5,5,5), new EmptyBorder(10,30,0,30));
        this.bottomPanel = PanelBuilder.createPanel(new GridBagLayout(), new EmptyBorder(10,30,0,30));


        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
        gbc.weighty = Constants.ARTICLE_SECTION_BUTTONS_HEIGHT.getValue();
        makeSectionButtons(this.sectionButtonNames);
        this.mainPanel.add(this.topPanel, gbc);

        gbc.gridy=1;
        gbc.weighty = Constants.ARTICLE_BUTTONS_HEIGHT.getValue();
        makeArticleButtons(this.articleButtonNames);
        this.mainPanel.add(this.articlePanel, gbc);

        gbc.gridy=2;
        gbc.weighty = Constants.ARTICLE_UTILITY_BUTTONS_HEIGHT.getValue();
        makeUtilityButtons();
        this.mainPanel.add(this.bottomPanel, gbc);
        this.mainPanel.setVisible(false);
    }

    private void makeSectionButtons(String[] sectionButtonNames){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        gbc.weightx = Constants.ARROW_WIDTH.getValue();
        this.arrowLeft = ButtonBuilder.buildButton();
        this.buttons.put("arrowLeft",this.arrowLeft);
        this.topPanel.add(this.arrowLeft, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        for(int numberIndex = 0; numberIndex<5; numberIndex++){
            if(numberIndex<sectionButtonNames.length){
                this.sectionButtons[numberIndex] = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),sectionButtonNames[numberIndex]);
                this.sectionButtons[numberIndex].setName("sectionButton_"+numberIndex);
            }
            else {
                this.sectionButtons[numberIndex] = ButtonBuilder.buildButton();
            }
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

    public void makeArticleButtons(String[] articleButtonNames){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        for (int numberIndex = 0; numberIndex < 25; numberIndex++) {
//            if(numberIndex<articleButtonNames.length){
//                this.articleButtons[numberIndex] = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),articleButtonNames[numberIndex]);
//                this.articleButtons[numberIndex].setName("articleButton_"+numberIndex);
//                this.buttons.put("articleButton_"+numberIndex, this.articleButtons[numberIndex]);
//                this.articlePanel.add(this.articleButtons[numberIndex]);
//            }
            if (articleButtonNames.length>numberIndex) {
                this.articleButtons[numberIndex] = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),articleButtonNames[numberIndex]);
            }else {
                this.articleButtons[numberIndex] = ButtonBuilder.buildButtonBlank();
            }
            this.articleButtons[numberIndex].setName("articleButton_"+numberIndex);
            this.buttons.put("articleButton_"+numberIndex, this.articleButtons[numberIndex]);
            this.articlePanel.add(this.articleButtons[numberIndex]);

        }
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
        this.searchButton = ButtonBuilder.buildButton();
        this.buttons.put("saerchButton", this.searchButton);
        this.bottomPanel.add(this.searchButton,gbc);

        gbc.gridx = 4;
        this.cancelButton = ButtonBuilder.buildButton(Colors.RED_BUTTON.getColor());
        this.buttons.put("cancelButton", this.cancelButton);
        this.bottomPanel.add(this.cancelButton,gbc);
    }

    public JPanel getMainPanel(){ return  this.mainPanel;}

    public void setVisible(Boolean condition){this.mainPanel.setVisible(condition);}

    public void updateArticleButtons(String[] buttonNames){
        //TODO: update the Map buttons acroding to button names
        int index = 0;
        for(JButton button : this.articleButtons) {
            if (button.getName() != null && buttonNames.length > index)
            {
                button.setText(buttonNames[index]);
                button.setEnabled(true);
                button.setBackground(Colors.DEFAULT_BUTTON.getColor());
                index++;
            }else{
                button.setText("");
                button.setEnabled(false);
                button.setBackground(Colors.GRAY.getColor());
                button.setName("articleButton" + index);
            }
            //            if (button.getName() != null && button.getName().contains("articleButton") && buttonNames.length > index) {
//                button.setText(buttonNames[index]);
//                button.setEnabled(true);
//                button.setBackground(Colors.DEFAULT_BUTTON.getColor());
//                index++;
//            } else if(button.getName() != null && button.getName().contains("articleButton")) {
//                button.setText("");
//                button.setEnabled(false);
//                button.setBackground(Colors.GRAY.getColor());
////                button.setName("articleButton" + index);
//            }
        }
//            try{
//                if (button.getName() != null && button.getName().contains("articleButton")) {
//                    button.setText(buttonNames[index]);
//                    button.setName("articleButton" + index);
//                    index++;
//                }else{
//
//                }
//
////                else if (){
////                    button = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),buttonNames[index]);
////                    button.setName("articleButton" + index);
////                    this.buttons.put(buttonNames[index],button);
////                    this.articlePanel.add(button);
////                    this.articlePanel.revalidate();
////                    index++;
////                }
//            }catch (ArrayIndexOutOfBoundsException e){
//                this.articlePanel.remove(button);
//                this.buttons.remove(button.getName());
//                this.articlePanel.revalidate();
//            }
//        }
    }

    public Map<String, JButton> getButtons(){ return this.buttons;}

}
