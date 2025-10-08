package viewsRework.GP;

import assets.Colors;
import services.Item;
import services.ItemUncountable;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DisplayScrollable extends JScrollPane {
    JPanel mainItemPanel = new JPanel();
    int itemContainerCount = 0;
    GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();

    public DisplayScrollable(){
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weighty=0;
        mainItemPanel.setLayout(new GridBagLayout());
        mainItemPanel.setOpaque(false);

        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setBackground(Colors.BACKGROUND_WHITE.getColor());
        setViewportView(mainItemPanel);
        setOpaque(false);
        touchControls();


    }

    public void addItem(Item item){
        // Setting up item container
        JPanel itemContainer = new JPanel();
        itemContainer.setLayout(new FlowLayout());
        itemContainer.setBackground(Colors.BACKGROUND_WHITE.getColor());
        itemContainer.setBorder(new DottedBorderTopBottom(Colors.GRAY.getColor(), 1));
        itemContainer.setPreferredSize(new Dimension(0,180));

        if(itemContainerCount % 2 == 1){
            itemContainer.setBackground(Colors.BUTTON_BACKGROUND_WHITE_ELEVATED.getColor());
        }

        if(compoundItems(item) == 0){
            makeItemContainer(item, itemContainer);
            mainItemPanel.add(itemContainer,gbc);
            gbc.gridy++;

            itemContainerCount++;
        }else{
            compoundItems(item);
        }

    }

    private void makeItemContainer(Item item, JPanel itemContainer) {
        JPanel itemNameContainer = new JPanel();
        JPanel itemPriceAmountContainer = new JPanel();

        itemNameContainer.setOpaque(false);
        itemPriceAmountContainer.setOpaque(false);

        itemNameContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemPriceAmountContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        itemNameContainer.setBorder(new EmptyBorder(0,20,0,20));
        itemPriceAmountContainer.setBorder(new EmptyBorder(0,20,0,20));


        itemNameContainer.setForeground(Colors.BLACK_TEXT.getColor());
        itemPriceAmountContainer.setForeground(Colors.BLACK_TEXT.getColor());

        itemNameContainer.add(new JLabel(item.getName()));
        itemPriceAmountContainer.add(new JLabel(item.getPrice() + " €"));
        if(item instanceof ItemUncountable){
            itemPriceAmountContainer.add(new JLabel(item.getAmount() + " kg"));
        }else{
            int intItemAmount = (int) Math.round(item.getAmount());
            itemPriceAmountContainer.add(new JLabel(intItemAmount + " ks"));
        }

        itemContainer.add(itemNameContainer);
        itemContainer.add(itemPriceAmountContainer);
    }

    private int compoundItems(Item item) {

        for(Component itemContainer : mainItemPanel.getComponents()){
            if(itemContainer instanceof JPanel itemContainers){

                JPanel itemNameContainer = (JPanel) itemContainers.getComponent(0);
                JPanel itemPriceAmountContainer = (JPanel) itemContainers.getComponent(1);

                String itemName = ((JLabel) itemNameContainer.getComponent(0)).getText();
                double itemPrice = Double.parseDouble(((JLabel) itemPriceAmountContainer.getComponent(0)).getText().split(" ")[0]);
                double itemAmount = Double.parseDouble(((JLabel) itemPriceAmountContainer.getComponent(1)).getText().split(" ")[0]);

                if(itemName.equals(item.getName())){
                    itemPrice = Math.ceil(( itemPrice + item.getPrice() ) * 100) / 100;
                    itemAmount = Math.ceil(( itemAmount + item.getAmount() ) * 1000) / 1000;
                    ((JLabel) itemPriceAmountContainer.getComponent(0)).setText(itemPrice +" €");

                    if (item instanceof ItemUncountable){
                        ((JLabel) itemPriceAmountContainer.getComponent(1)).setText(itemAmount + " kg");
                    }else{
                        int intItemAmount = (int) Math.round(itemAmount);
                        ((JLabel) itemPriceAmountContainer.getComponent(1)).setText(intItemAmount + " ks");
                    }
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

}
