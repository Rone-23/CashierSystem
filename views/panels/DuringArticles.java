package views.panels;

import assets.ButtonSet;
import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import views.Components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class DuringArticles extends JPanel implements ButtonFoundable{
     private final DisplayItems displayItems = new DisplayItems();
     private final Display display = new Display(Constants.SPLIT);
     private final Keyboard keyboard = new Keyboard();
     private final DisplayArticles displayArticles = new DisplayArticles();
     private final ArticleFilterButtonCluster articleFilterButtonCluster = new ArticleFilterButtonCluster();
     private final JButton plusButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.ADD.toString(), Colors.DEFAULT_BLUE.getColor());
     private final JButton minusButton = ButtonBuilder.buildChonkyButton("Ubrať", Colors.DEFAULT_BLUE.getColor());
     private final JButton searchButton = ButtonBuilder.buildChonkyButton("Vyhladať", Colors.DEFAULT_BLUE.getColor());
     private final JButton cancelButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.EXIT.toString(), Colors.DEFAULT_BLUE.getColor());
     private final JPanel clusterBottom = createBottomCluster();

     public DuringArticles(){
         GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
         setLayout(new GridBagLayout());
         setBackground(Colors.BACKGROUND_WHITE.getColor());

         gbcMain.gridy=0;
         gbcMain.gridx=0;
         add(createLeftPanel(),gbcMain);
         gbcMain.gridx=1;
         add(createRightPanel(),gbcMain);

     }

     private JPanel createLeftPanel(){
         JPanel main = new JPanel();
         main.setLayout(new GridBagLayout());
         main.setOpaque(false);
         GridBagConstraints gbcLeftPanel = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);

         main.add(displayItems,gbcLeftPanel);

         gbcLeftPanel.weighty = 0;
         gbcLeftPanel.weightx = 0;

         gbcLeftPanel.gridy++;
         main.add(display,gbcLeftPanel);

         gbcLeftPanel.gridy++;
         main.add(keyboard,gbcLeftPanel);

         Border rightBorder = new MatteBorder(
                 0,
                 0,
                 0,
                 3,
                 Colors.BUTTON_LIGHT_BLUE.getColor()
         );
         main.setBorder(rightBorder);

         return main;
     }

     private JPanel createRightPanel(){
         JPanel main = new JPanel();
         main.setLayout(new GridBagLayout());
         main.setOpaque(false);
         GridBagConstraints gbcRightPanel = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);

         gbcRightPanel.weighty=0;
         Border bottomBorder = new MatteBorder(
                 0,
                 0,
                 3,
                 0,
                 Colors.BUTTON_LIGHT_BLUE.getColor()
         );
         articleFilterButtonCluster.setBorder(bottomBorder);
         main.add(articleFilterButtonCluster,gbcRightPanel);

         gbcRightPanel.weighty=1;
         gbcRightPanel.gridy++;
         main.add(displayArticles,gbcRightPanel);

         gbcRightPanel.weighty=0;
         gbcRightPanel.gridy++;
         main.add(clusterBottom,gbcRightPanel);

        return main;
     }

     private JPanel createBottomCluster(){
         JPanel main = new JPanel();
         main.setLayout(new GridBagLayout());
         main.setOpaque(false);
         
         JPanel subsection = new JPanel();
         subsection.setLayout(new GridBagLayout());
         subsection.setBorder(new EmptyBorder(5,5,5,5));
         subsection.setOpaque(false);

         Border topBorder = new MatteBorder(
                 3,
                 0,
                 0,
                 0,
                 Colors.BUTTON_LIGHT_BLUE.getColor()
         );
         main.setBorder(topBorder);
         final Dimension buttonDimensions = new Dimension(183,136);
         GridBagConstraints gbcBottomCluster = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
         GridBagConstraints gbcSubsection = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);

         plusButton.setPreferredSize(buttonDimensions);
         minusButton.setPreferredSize(buttonDimensions);
         searchButton.setPreferredSize(buttonDimensions);
         cancelButton.setPreferredSize(buttonDimensions);

         JLabel subsectionLabel = new JLabel("Množstvo tovaru", SwingConstants.CENTER);
         subsectionLabel.setForeground(Colors.BLACK_TEXT.getColor());
         subsectionLabel.setFont(new Font("Roboto", Font.BOLD, 21));


         gbcSubsection.gridwidth=2;

         subsection.add(subsectionLabel,gbcSubsection);

         gbcSubsection.gridwidth=1;

         gbcSubsection.gridy++;
         subsection.add(plusButton,gbcSubsection);

         gbcSubsection.gridx++;
         subsection.add(minusButton,gbcSubsection);


         gbcBottomCluster.gridwidth=2;

         main.add(subsection,gbcBottomCluster);

         gbcBottomCluster.gridwidth=1;

         gbcBottomCluster.gridx+=2;
         main.add(searchButton,gbcBottomCluster);

         gbcBottomCluster.gridx++;
         main.add(cancelButton,gbcBottomCluster);

         return main;
     }

     public DisplayItems getDisplayScrollableItems(){return displayItems;}

     public DisplayArticles getDisplayScrollableArticles(){return displayArticles;}

     public Display getDisplay(){return display;}

     public ArticleFilterButtonCluster getArticleFilterButtonCluster(){return articleFilterButtonCluster;}

    @Override
     public JButton getButton(String key){
        for(Component c : keyboard.getComponentsInside()){
            if(c instanceof JButton && c.getName().equals(key.toLowerCase())){
                return (JButton) c;
            }
        }
        for(Component c : clusterBottom.getComponents()){
            if(c instanceof JButton && c.getName().equals(key.toLowerCase())){
                return (JButton) c;
            }
        }
        if(plusButton.getName().equals(key.toLowerCase())){
            return plusButton;

        }
        if(minusButton.getName().equals(key.toLowerCase())){
            return minusButton;
        }
        if(searchButton.getName().equals(key.toLowerCase())){
            return searchButton;
        }
        if(cancelButton.getName().equals(key.toLowerCase())){
            return cancelButton;
        }
        throw new ArrayIndexOutOfBoundsException();
     }

     @Override
     public JButton[] getButtons(String key){
         ArrayList<JButton> jButtons = new ArrayList<>();
         for(Component c : keyboard.getComponentsInside()){
             if(c instanceof JButton && c.getName().equals(key.toLowerCase())){
                 jButtons.add((JButton) c);
             }
         }
         for(Component c : clusterBottom.getComponents()){
             if(c instanceof JButton && c.getName().equals(key.toLowerCase())){
                 jButtons.add((JButton) c);
             }
         }
         if(plusButton.getName().equals(key.toLowerCase())){
             jButtons.add(plusButton);
         }
         if(minusButton.getName().equals(key.toLowerCase())){
             jButtons.add(minusButton);
         }
         if(searchButton.getName().equals(key.toLowerCase())){
             jButtons.add(searchButton);
         }
         if(cancelButton.getName().equals(key.toLowerCase())){
             jButtons.add(cancelButton);
         }
         if(!jButtons.isEmpty()){
             return jButtons.toArray(new JButton[0]);
         }
         throw new ArrayIndexOutOfBoundsException();

     }
}
