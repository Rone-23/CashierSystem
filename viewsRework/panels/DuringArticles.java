package viewsRework.panels;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import viewsRework.Components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class DuringArticles  extends JPanel {
     private final DisplayScrollableItems displayScrollableItems = new DisplayScrollableItems();
     private final Display display = new Display(Constants.SPLIT);
     private final Keyboard keyboard = new Keyboard();
     private final DisplayScrollableArticles displayScrollableArticles = new DisplayScrollableArticles();
     private final ArticleFilterButtonCluster articleFilterButtonCluster = new ArticleFilterButtonCluster();
     private final JButton plusButton = ButtonBuilder.buildChonkyButton("Plus", Colors.BUTTON_LIGHT_BLUE.getColor());
     private final JButton minusButton = ButtonBuilder.buildChonkyButton("Minus", Colors.BUTTON_LIGHT_BLUE.getColor());
     private final JButton searchButton = ButtonBuilder.buildChonkyButton("Search", Colors.BUTTON_LIGHT_BLUE.getColor());
     private final JButton cancelButton = ButtonBuilder.buildChonkyButton("Cancel", Colors.BUTTON_LIGHT_BLUE.getColor());

     public DuringArticles(){
         GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
         setLayout(new GridBagLayout());
         setBackground(Colors.BACKGROUND_WHITE.getColor());

         gbc.gridy=0;
         gbc.gridx=0;
         add(createLeftPanel(),gbc);
         gbc.gridx=1;
         add(createRightPanel(),gbc);


     }

     private JPanel createLeftPanel(){
         JPanel main = new JPanel();
         main.setLayout(new GridBagLayout());
         main.setOpaque(false);
         GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0);

         gbc.gridy=0;
         main.add(displayScrollableItems,gbc);

         gbc.weighty = 0;
         gbc.weightx = 0;

         gbc.gridy=1;
         main.add(display,gbc);

         gbc.gridy=2;
         main.add(keyboard,gbc);

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
         GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);

         gbc.weighty=0;
         Border bottomBorder = new MatteBorder(
                 0,
                 0,
                 3,
                 0,
                 Colors.BUTTON_LIGHT_BLUE.getColor()
         );
         articleFilterButtonCluster.setBorder(bottomBorder);
         main.add(articleFilterButtonCluster,gbc);

         gbc.weighty=1;
         gbc.gridy+=1;
         main.add(displayScrollableArticles,gbc);

         gbc.gridy+=1;
         gbc.weighty=0;
         main.add(createClusterBottom(),gbc);

        return main;
     }

     private JPanel createClusterBottom(){
         JPanel main = new JPanel();
         main.setLayout(new GridBagLayout());
         main.setOpaque(false);
         Border topBorder = new MatteBorder(
                 3,
                 0,
                 0,
                 0,
                 Colors.BUTTON_LIGHT_BLUE.getColor()
         );
         main.setBorder(topBorder);
         final Dimension buttonDimensions = new Dimension(183,136);
         GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

         plusButton.setPreferredSize(buttonDimensions);
         minusButton.setPreferredSize(buttonDimensions);
         searchButton.setPreferredSize(buttonDimensions);
         cancelButton.setPreferredSize(buttonDimensions);

         main.add(plusButton,gbc);
         gbc.gridx+=1;
         main.add(minusButton,gbc);
         gbc.gridx+=1;
         main.add(searchButton,gbc);
         gbc.gridx+=1;
         main.add(cancelButton,gbc);

         return main;
     }

     public DisplayScrollableItems getDisplayScrollableItems(){return displayScrollableItems;}

     public DisplayScrollableArticles getDisplayScrollableArticles(){return displayScrollableArticles;}

     public Keyboard getKeyboard(){return keyboard;}
}
