package views.Components;

import assets.Colors;
import assets.ThemeManager;
import assets.ThemeObserver;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class DisplayArticles extends JScrollPane implements ThemeObserver {
    JPanel container = new JPanel(new GridBagLayout());
    JPanel mainArticlePanel = new JPanel(new GridBagLayout());
    Dimension preferedDimension;
    GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
    JPanel spacerVertical = new JPanel();
    JPanel spacerHorizontal = new JPanel();
    GridBagConstraints gbcSpacer = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
    int componentCount = 0;
    private final Map<String, JToggleButton> buttons = new HashMap<>();

    public DisplayArticles(){
        ThemeManager.getInstance().addObserver(this);

        getViewport().setOpaque(false);
        setBorder(null);
        setOpaque(false);
        setPreferredSize(new Dimension(0,0));
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(container);

        JScrollBar verticalScrollBar = getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBar());
        verticalScrollBar.setPreferredSize(new Dimension(20, 0));
        verticalScrollBar.setOpaque(false);
        verticalScrollBar.setUnitIncrement(20);

        spacerVertical.setName("SPACER_VERTICAL");
        spacerVertical.setOpaque(false);

        spacerHorizontal.setName("SPACER_HORIZONTAL");
        spacerHorizontal.setOpaque(false);

        mainArticlePanel.setOpaque(false);
        container.setBackground(Colors.BACKGROUND_GRAY.getColor());

        onThemeChange();
//        touchControls();
        recalculate();

        gbc.weighty=0;
        gbc.weightx=0;
        gbcSpacer.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);

        gbcSpacer.weighty=0;
        container.add(mainArticlePanel,gbcSpacer);
        gbcSpacer.fill = GridBagConstraints.HORIZONTAL;
        gbcSpacer.weighty=1;
        gbcSpacer.gridy++;
        container.add(spacerVertical,gbcSpacer);

    }

    public void addArticle(ArticleButton articleButton){

        gbc.gridx = componentCount % 4;
        gbc.gridy = componentCount / 4;

        articleButton.setPreferredSize(preferedDimension);

        buttons.put(articleButton.getName().toLowerCase(),articleButton);

        mainArticlePanel.add(articleButton,gbc);
        componentCount++;

        moveSpacer(gbc.gridx, gbc.gridy);
    }

    public void clear() {
        mainArticlePanel.removeAll();
        buttons.clear();
        componentCount = 0;
        mainArticlePanel.revalidate();
        mainArticlePanel.repaint();
    }

    public Map<String, JToggleButton> getButtons(){
        return buttons;
    }

    private void recalculate(){
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
        Component[] components = mainArticlePanel.getComponents();
        for(Component c : components){
            if(c instanceof JButton){
                c.setPreferredSize(preferedDimension);
            }
        }
    }

    private void touchControls(){
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


        gbcSpacer.gridy = currentGridY;

        gbcSpacer.gridx = currentGridX+1;

        gbcSpacer.gridwidth = 4-gbcSpacer.gridx;

        if (gbcSpacer.gridx <= 3){
            mainArticlePanel.add(spacerHorizontal,gbcSpacer);
        }else {
            mainArticlePanel.remove(spacerHorizontal);
        }
    }

    @Override
    public void onThemeChange() {
        Color bg = Colors.BACKGROUND_WHITE.getColor();
        setBackground(bg);
        container.setBackground(bg);
        mainArticlePanel.setBackground(bg);
        getViewport().setBackground(bg);
        this.repaint();
    }
}
