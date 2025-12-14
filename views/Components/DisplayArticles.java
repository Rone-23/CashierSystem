package views.Components;

import assets.Colors;
import services.Item;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class DisplayArticles extends JScrollPane {
    JPanel container = new JPanel(new GridBagLayout());
    JPanel mainArticlePanel = new JPanel(new GridBagLayout());
    Dimension preferedDimension;
    GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
    JPanel spacerVertical = new JPanel();
    JPanel spacerHorizontal = new JPanel();
    GridBagConstraints gbcSpacer = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
    int componentCount = 0;
    private final Map<String, JButton> buttons = new HashMap<>();

    public DisplayArticles(){

        //Setting up component
        getViewport().setOpaque(false);
        setBorder(null);
        setOpaque(false);
        setPreferredSize(new Dimension(0,0));
        setOpaque(false);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(container);
        getVerticalScrollBar().setUnitIncrement(20);

        //Setting up spacers
        spacerVertical.setName("SPACER_VERTICAL");
        spacerVertical.setOpaque(false);

        spacerHorizontal.setName("SPACER_HORIZONTAL");
        spacerHorizontal.setOpaque(false);

        mainArticlePanel.setOpaque(false);
        container.setBackground(Colors.BACKGROUND_GRAY.getColor());

        //Adding functionality to display
        touchControls();
        recalculate();

        //Adding main components to the main container
        gbcSpacer.weighty=0;
        container.add(mainArticlePanel,gbcSpacer);
        gbcSpacer.fill = GridBagConstraints.HORIZONTAL;
        gbcSpacer.weighty=1;
        gbcSpacer.gridy++;
        container.add(spacerVertical,gbcSpacer);

    }

    public void addArticle(Item item){
        //Setting up GridBagConstraints for each article button
        gbc.weighty=0;
        gbc.weightx=1;

        //Each row should have 4 Article buttons
        gbc.gridy = (int) Math.floor( (double) componentCount /4);
        gbc.gridx = componentCount % 4;

        //Creating button
        JButton button = ButtonBuilder.buildArticleButton(Colors.ARTICLE_BUTTON.getColor(),item);
        button.setPreferredSize(preferedDimension);

        //Storing each button with key that is items name that it contains
        buttons.put(item.getName().toLowerCase(),button);

        //Adding button to the panel that contains all article buttons
        mainArticlePanel.add(button,gbc);
        mainArticlePanel.revalidate();

        //This is used for aligning all the article buttons to the left top
        moveSpacer(gbc.gridx, gbc.gridy);

        componentCount++;

        //Repaint so that every button that is not really on that spot isnt glitched
        repaint();
    }

    public void clear(){
        //This method is called whenever you need to clear all the articles from the main article panel
        mainArticlePanel.removeAll();
        componentCount=0;
    }

    public Map<String, JButton> getButtons(){
        return buttons;
    }

    private void recalculate(){
        //Dynamically adjust the size of the article button so that there are always 4X3.5 matrix
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                preferedDimension = new Dimension((int) (panelWidth*0.24), (int) (panelHeight*0.28));
                rescaleButtons();
                mainArticlePanel.revalidate();
            }
        });
    }

    private void rescaleButtons(){
        //Called when you need to update the size of the buttons
        Component[] components = mainArticlePanel.getComponents();
        for(Component c : components){
            if(c instanceof JButton){
                c.setPreferredSize(preferedDimension);
            }
        }
    }

    private void touchControls(){
        // add "touch scroll" behavior
        JViewport view = this.getViewport();
        final Point lastDrag = new Point();
        this.getViewport().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point viewPos = view.getViewPosition();
                int dx = lastDrag.x - e.getX();
                int dy = lastDrag.y - e.getY();
                view.setViewPosition(new Point(viewPos.x + dx, viewPos.y + dy));
                lastDrag.setLocation(e.getPoint());
            }
        });

        this.getViewport().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastDrag.setLocation(e.getPoint());
            }
        });
    }

    private void moveSpacer(int currentGridX, int currentGridY){
        //This method is called whenever you need realign the buttons in the grid to the top left

        gbcSpacer.weightx = 1;
        gbcSpacer.weighty = 0;
        gbcSpacer.fill = GridBagConstraints.HORIZONTAL;

        gbcSpacer.gridy = currentGridY;

        //Spacer should always be in front of the article button IF there is less than 4 buttons
        gbcSpacer.gridx = currentGridX+1;

        //Width should be all the spaces that are blank
        gbcSpacer.gridwidth = 4-gbcSpacer.gridx;

        //Check if there is less than 4 buttons
        if (gbcSpacer.gridx <= 3){
            mainArticlePanel.add(spacerHorizontal,gbcSpacer);
        }else {
            mainArticlePanel.remove(spacerHorizontal);
        }
    }

}
