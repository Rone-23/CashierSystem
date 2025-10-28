package views.Components;

import assets.Colors;
import services.Item;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class DisplayScrollableArticles extends JScrollPane {
    JPanel mainArticlePanel = new JPanel();
    Dimension preferedDimension;
    GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
    JPanel spacer = new JPanel();
    GridBagConstraints gbcSpacer = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

    private Map<String, JButton> buttons = new HashMap<>();

    public DisplayScrollableArticles(){
        spacer.setOpaque(false);
        gbc.anchor=GridBagConstraints.NORTHWEST;
        mainArticlePanel.setLayout(new GridBagLayout());
        mainArticlePanel.setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(null);
        setOpaque(false);
        setPreferredSize(new Dimension(0,0));
        setViewportView(mainArticlePanel);
        setOpaque(false);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        touchControls();
        recalculate();
    }

    public void addArticle(Item item){

        Component[] components = mainArticlePanel.getComponents();
        gbc.weighty=0;
        gbc.weightx=0;
        gbc.gridy = (int) Math.floor( (double) components.length /4);
        if((gbc.gridx+1)%5==0){
            gbc.gridx=0;
        }
        JButton button = new ArticleButton(Colors.BUTTON_LIGHT_BLUE.getColor(),item);
        button.setPreferredSize(preferedDimension);

        buttons.put(item.getName().toLowerCase(),button);

        mainArticlePanel.add(button,gbc);
        mainArticlePanel.revalidate();
        gbc.gridx+=1;
        moveSpacer(gbc.gridy);
    }

    public Map<String, JButton> getButtons(){
        return buttons;
    }

    private void recalculate(){
        this.addComponentListener(new ComponentAdapter() {
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

    private void moveSpacer(int currentGridY){
        gbcSpacer.gridx = 0;
        gbcSpacer.gridy = currentGridY+1;
        gbcSpacer.weightx = 0.0;
        gbcSpacer.weighty = 1.0;
        gbcSpacer.fill = GridBagConstraints.BOTH;
        mainArticlePanel.add(spacer,gbcSpacer);
    }

}
