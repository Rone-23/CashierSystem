package views.Components;

import assets.Colors;
import services.Item;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class ArticleButton extends JButton {
    Color color;
    Color colorActive;
    Color colorDisabled;
    String itemName;
    int itemPrice;
    public ArticleButton(Color color, Item item){
        this.itemName = item.getName();
        this.itemPrice = item.getPrice();
        this.color = color;
        colorActive = color;
        colorDisabled = color.brighter();
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Roboto", Font.BOLD, 12));
        setForeground(Colors.BLACK_TEXT.getColor());
        setMargin(new Insets(55,55,40,40));
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel buttonModel = getModel();

        if (buttonModel.isSelected()){
            drawButtonPressed(g2);
        } else {
            drawButtonIdle(g2);
        }
        g2.dispose();
    }

    public void drawButtonIdle(Graphics2D g2){
        int arc = getWidth()/9;
        int inset = getWidth()/18;

        g2.setPaint(color);
        Shape main = new RoundRectangle2D.Double(inset, inset, getWidth()-inset*2, getHeight() -inset*2, arc, arc);
        g2.fill(main);


        int insetTop = getHeight()/26;
        int insetBottom =getHeight()/3;
        int insetSide = getWidth()/6;
        int width = getWidth() - inset*2 - insetSide*2;
        int height = getHeight() - inset*2 - insetBottom - insetTop;

        FontMetrics fm = getFontMetrics(getFont());

        g2.setPaint(ColorManipulation.lighten(color,10f));
        Shape inner = new RoundRectangle2D.Double( inset+insetSide, inset+insetTop, width, height-insetTop, arc, arc );
        g2.fill(inner);

        g2.setPaint(Colors.BLACK_TEXT.getColor());
        Shape writableAreaName = new Rectangle2D.Double(
                insetSide+inset,
                insetTop+inset+height,
                width,
                (double) insetBottom /2
        );

        g2.drawString(itemName,writableAreaName.getBounds().x,writableAreaName.getBounds().y+fm.getHeight());

//        g2.setPaint(new Color(100,100,100,100));
//        g2.fill(writableAreaName);

        Shape writableAreaPrice = new Rectangle2D.Double(
                insetSide+inset,
                insetTop+inset+height+writableAreaName.getBounds().height,
                width,
                (double) insetBottom /2
        );

        g2.drawString(String.format("%.2f€",itemPrice*0.01),writableAreaPrice.getBounds().x,writableAreaPrice.getBounds().y+fm.getHeight()/2);

//        g2.setPaint(new Color(200,200,200,150));
//        g2.fill(writableAreaPrice);
    }

    public void drawButtonPressed(Graphics2D g2){
        int arc = getWidth()/9;
        int inset = getWidth()/18;

        g2.setPaint(ColorManipulation.darken(color,0.58f));
        Shape main = new RoundRectangle2D.Double(inset, inset, getWidth()-inset*2, getHeight() -inset*2, arc, arc);
        g2.fill(main);


        int insetTop = getHeight()/26;
        int insetBottom =getHeight()/3;
        int insetSide = getWidth()/6;
        int width = getWidth() - inset*2 - insetSide*2;
        int height = getHeight() - inset*2 - insetBottom - insetTop;

        FontMetrics fm = getFontMetrics(getFont());

        g2.setPaint(ColorManipulation.lighten(color,10f));
        Shape inner = new RoundRectangle2D.Double( inset+insetSide, inset+insetTop, width, height-insetTop, arc, arc );
        g2.fill(inner);

        g2.setPaint(Colors.BLACK_TEXT.getColor());
        Shape writableAreaName = new Rectangle2D.Double(
                insetSide+inset,
                insetTop+inset+height,
                width,
                (double) insetBottom /2
        );

        g2.drawString(itemName,writableAreaName.getBounds().x,writableAreaName.getBounds().y+fm.getHeight());

//        g2.setPaint(new Color(100,100,100,100));
//        g2.fill(writableAreaName);

        Shape writableAreaPrice = new Rectangle2D.Double(
                insetSide+inset,
                insetTop+inset+height+writableAreaName.getBounds().height,
                width,
                (double) insetBottom /2
        );

        g2.drawString(String.format("%.2f€",itemPrice*0.01),writableAreaPrice.getBounds().x,writableAreaPrice.getBounds().y+fm.getHeight()/2);

//        g2.setPaint(new Color(200,200,200,150));
//        g2.fill(writableAreaPrice);
    }

    public int getItemPrice(){
        return itemPrice;
    }

    public String getItemName(){
        return itemName;
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        if(b){
            color = colorActive;
        }else{
            color = colorDisabled;
        }
        repaint();
    }
}
