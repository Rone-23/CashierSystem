import Services.Item;
import Services.ItemCountable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Choice_Panel {
    private JPanel mainPanel;
    private JButton ovocieButton;
    private JButton zeleninaButton;
    private JButton pecivoButton;
    private JPanel navPanel;
    private JPanel articleButtonsPanel;
    private JButton backButton;
    private final Main_Window mainWindow;
    private final ArrayList<Item> listOfItems = new ArrayList<>();
    private Double amountToRemove= 1.0;


    public Choice_Panel(Main_Window mainWindow){
        this.mainWindow = mainWindow;
        ovocieButton.setPreferredSize(Main_Window.getAs().navButtonD);
        zeleninaButton.setPreferredSize(Main_Window.getAs().navButtonD);
        pecivoButton.setPreferredSize(Main_Window.getAs().navButtonD);
        backButton.setPreferredSize(Main_Window.getAs().navButtonD);
        backButton.addActionListener(e -> mainPanel.setVisible(false));

        navPanel.setMaximumSize(new Dimension(Main_Window.getAs().width,100));
        mainPanel.setVisible(false);
        mainPanel.setBounds(0,0, Main_Window.getAs().width, Main_Window.getAs().height);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        articleButtonsPanel.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,5,5,5);

        for (int numberOfButtons = 0; numberOfButtons< Main_Window.getConn().getRows("Articles"); numberOfButtons++){

            int articleNumber=numberOfButtons+1;
            JButton[] buttons = new JButton[25];
            buttons[numberOfButtons] = new JButton(Main_Window.getConn().getString(numberOfButtons+1));
            buttons[numberOfButtons].setPreferredSize(Main_Window.getAs().buttonD);
            articleButtonsPanel.add(buttons[numberOfButtons],gbc);

            buttons[numberOfButtons].addActionListener(e -> removeFromStock(articleNumber));

            gbc.gridx++;
            if ((numberOfButtons+1) % 5 == 0) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
    }
    private void removeFromStock(int article_id){
        Main_Window.getConn().removeFromStock(amountToRemove,article_id);
        String name = Main_Window.getConn().getString(article_id);
        double stock = Main_Window.getConn().getStock(article_id);
        String toDisplay = String.format("%s x -%s ( %s )\n",name, amountToRemove, stock);
        mainWindow.getDisplayMain().append(toDisplay);
        listOfItems.add(new ItemCountable(Main_Window.getConn().getString(article_id),
                Main_Window.getConn().getPrice(article_id),
                amountToRemove));
        amountToRemove=1.0;

//        System.out.println(Arrays.toString(listOfItems.toArray()));
    }
    private void addToStock(int article_id){
        Main_Window mainWindow = new Main_Window();
        Main_Window.getConn().removeFromStock(1.,article_id);
        String name = Main_Window.getConn().getString(article_id);
        double stock = Main_Window.getConn().getStock(article_id);
        String toDisplay = name + " x 1 ( " + stock + ")\n";
        mainWindow.getDisplayMain().append(toDisplay);
    }
    public ArrayList getListOfItems(){
        return listOfItems;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setAmountToRemove(Double amountToRemove) {
        this.amountToRemove = amountToRemove;
    }
}
