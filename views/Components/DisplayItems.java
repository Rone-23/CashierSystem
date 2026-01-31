package views.Components;

import assets.Colors;
import services.Item;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DisplayItems extends JScrollPane {
    JPanel mainItemPanel = new JPanel();
    JPanel spacer = new JPanel();
    GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();
    GridBagConstraints gbcSpacer = GridBagConstraintsBuilder.buildGridBagConstraints();
    private final Font font = new Font("Roboto",Font.BOLD,21);

    public DisplayItems(){
        spacer.setOpaque(false);
        spacer.setName("spacer");
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weighty=0;
        gbc.weightx=1;
        mainItemPanel.setLayout(new GridBagLayout());
        mainItemPanel.setOpaque(false);

        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setBackground(Colors.BACKGROUND_WHITE.getColor());
        setViewportView(mainItemPanel);
        getViewport().setOpaque(false);
        setBorder(null);
        setOpaque(false);
        touchControls();
        setPreferredSize(new Dimension(0,0));
        setAlignmentY(JScrollPane.TOP_ALIGNMENT);
        getVerticalScrollBar().setUnitIncrement(20);
    }

    public void addItem(Item item){

        if(compoundItems(item) == 0){
            mainItemPanel.add(new ListItemButton(Colors.BACKGROUND_WHITE.getColor(),item),gbc);
            gbc.gridy++;
            moveSpacer(gbc.gridy);
        }

        setBackgroundVariation();
        mainItemPanel.revalidate();
    }

    public void addPayment(String typeOfPayment,int content){

        JPanel itemContainer = new JPanel(new GridBagLayout());
        itemContainer.setBackground(Colors.BACKGROUND_WHITE.getColor());
        itemContainer.setBorder(new DottedBorderTopBottom(Colors.GRAY.getColor(), 1));
        itemContainer.setPreferredSize(new Dimension(0,70));
        itemContainer.setName("Payment");


        //TODO: make prettier
        GridBagConstraints gbcItemContainer = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);

        JPanel itemNameContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel itemPriceAmountContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        itemNameContainer.setOpaque(false);
        itemPriceAmountContainer.setOpaque(false);

        itemNameContainer.setBorder(new EmptyBorder(0,20,0,20));
        itemPriceAmountContainer.setBorder(new EmptyBorder(0,20,0,20));

        itemNameContainer.setForeground(Colors.BLACK_TEXT.getColor());
        itemPriceAmountContainer.setForeground(Colors.BLACK_TEXT.getColor());

        JLabel itemNameLabel = new JLabel(typeOfPayment,SwingConstants.LEFT);
        itemNameLabel.setFont(font);
        itemNameLabel.setForeground(Colors.BLACK_TEXT.getColor());
        itemNameContainer.add(itemNameLabel);

        JLabel itemPriceLabel = new JLabel(String.format("%.2f €", content * 0.01));
        itemPriceLabel.setFont(font);
        itemPriceLabel.setForeground(Colors.BLACK_TEXT.getColor());
        itemPriceLabel.setBorder(new EmptyBorder(0,20,0,20));
        itemPriceAmountContainer.add(itemPriceLabel);


        itemContainer.add(itemNameContainer,gbcItemContainer);
        gbcItemContainer.gridx++;
        itemContainer.add(itemPriceAmountContainer,gbcItemContainer);


        mainItemPanel.add(itemContainer,gbc);
        gbc.gridy++;
        moveSpacer(gbc.gridy);

        setBackgroundVariation();
        mainItemPanel.revalidate();
    }

    public void removeItem(Item item){
        for(Component component : mainItemPanel.getComponents()){
            if(component.getName().equals(item.getName())){
                mainItemPanel.remove(component);
            }
        }
        setBackgroundVariation();
        mainItemPanel.revalidate();
    }

    private void setBackgroundVariation() {
        //Swapping of background colors
        int i = 0;

        for(Component component : mainItemPanel.getComponents()){
            if(!component.getName().equals("spacer")){
                if(i % 2 == 1) {
                    component.setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());
                }else{
                    component.setBackground(Colors.BACKGROUND_GRAY.getColor());
                }
                i++;
            }
        }
    }

    public void clear(){
        for(Component component : mainItemPanel.getComponents()){
            if(!component.getName().equals("spacer")){
                mainItemPanel.remove(component);
            }
        }
        mainItemPanel.revalidate();
    }

    private int compoundItems(Item item) {
        //Checking for existing item listings and adding them together
        for(Component itemContainer : mainItemPanel.getComponents()){
            if(itemContainer instanceof ListItemButton listItemButton){
                //Selecting out the spacer which contents are 0
                if(listItemButton.getName().equals(item.getName())){
                    return 1;
                }
            }
        }
        return 0;
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
        mainItemPanel.add(spacer,gbcSpacer);

    }


}
