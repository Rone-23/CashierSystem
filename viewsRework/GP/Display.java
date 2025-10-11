package viewsRework.GP;

import assets.Colors;
import assets.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class Display extends JTextArea {
    private final String[] text = new String[2];
    private final Constants displayType;
    private final Dimension dimension = new Dimension(400,100);
    public Display() {
        this.displayType = Constants.TOTAL;
        setPreferredSize(dimension);
        setOpaque(false);
        setBorder(new EmptyBorder(10,10,10,10));
        setMargin(new Insets(30,30,30,30));
        setFont(new Font("Roboto", Font.BOLD, 40));
        setEditable(false);
        setFocusable(false);
    }

    public Display(Constants displayType) {
        this.displayType = displayType;
        setPreferredSize(dimension);
        setOpaque(false);
        setBorder(new EmptyBorder(10,10,10,10));
        setMargin(new Insets(30,30,30,30));
        setFont(new Font("Roboto", Font.BOLD, 40));
        setEditable(false);
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Painting display shape
        int arc = getHeight();
        int inset = 5;
        Shape pill = new RoundRectangle2D.Double(
                inset, inset, getWidth() - 2 * inset, getHeight() - 2 * inset, arc, arc
        );
        g2.setColor(Colors.DEFAULT_BLUE.getColor());
        g2.fill(pill);

        //Setting up variants
        g2.setPaint(Colors.BLACK_TEXT.getColor());
        int drawTextCoordinatesX;
        int drawTextCoordinatesY;

        switch(displayType){
            case TOTAL -> {
                g2.setFont(paintVariantTotal());
                FontMetrics fm = g2.getFontMetrics();
                Shape writableArea = new Rectangle(
                        (int) (inset+ (double) getHeight() /2*0.6),
                        (int) (inset+ (double) getHeight() /2-getHeight()*0.4),
                        (int) (getWidth()-getHeight()*0.6-inset*2),
                        (int) (getHeight()*0.8-inset*2)
                        );

                drawTextCoordinatesX = writableArea.getBounds().x;
                drawTextCoordinatesY = writableArea.getBounds().y+fm.getHeight();

                g2.drawString("Total",drawTextCoordinatesX,drawTextCoordinatesY);

                int strWidth = fm.stringWidth(getText() +" €");
                drawTextCoordinatesX = (int) writableArea.getBounds().getWidth()+writableArea.getBounds().x-strWidth;
                g2.drawString(getText()+" €",drawTextCoordinatesX,drawTextCoordinatesY);


                //Only for debug purposes
//                g2.setPaint(new Color(100,100,100,100));
//                g2.fill(writableArea);
            }
            case WEIGHT -> {
                g2.setFont(paintVariantWeight());
                FontMetrics fm = g2.getFontMetrics();
                Shape writableArea = new Rectangle(
                        (int) (inset+ (double) getHeight() /2*0.6),
                        (int) (inset+ (double) getHeight() /2-getHeight()*0.4),
                        (int) (getWidth()-getHeight()*0.6-inset*2),
                        (int) (getHeight()*0.8-inset*2)
                );

                drawTextCoordinatesX = writableArea.getBounds().x;
                drawTextCoordinatesY = writableArea.getBounds().y+fm.getHeight();

                g2.drawString("Počet",drawTextCoordinatesX,drawTextCoordinatesY);

                int strWidth = fm.stringWidth(getText() +" ks");
                drawTextCoordinatesX = (int) writableArea.getBounds().getWidth()+writableArea.getBounds().x-strWidth;
                g2.drawString(getText()+" ks",drawTextCoordinatesX,drawTextCoordinatesY);
            }
            case SPLIT -> {
                int strWidth;
                g2.setFont(paintVariantSplit(g2));
                FontMetrics fm = g2.getFontMetrics();
                Shape writableAreaTop = new Rectangle(
                        (int) (inset+ (double) getHeight() /2*0.6),
                        (int) (inset+ (double) getHeight() /2-getHeight()*0.4),
                        (int) (getWidth()-getHeight()*0.6-inset*2),
                        (int) (getHeight()*0.8-inset*4)/2
                );
                Shape writableAreaBottom = new Rectangle(
                        (int) (inset+ (double) getHeight() /2*0.6),
                        (int) (inset*2+ (double) getHeight() /2-getHeight()*0.4 + writableAreaTop.getBounds().getHeight() + inset),
                        (int) (getWidth()-getHeight()*0.6-inset*2),
                        (int) (getHeight()*0.8-inset*2)/2
                );

                drawTextCoordinatesX = writableAreaTop.getBounds().x;
                drawTextCoordinatesY = (int) (writableAreaTop.getBounds().y+fm.getHeight()*0.7);

                g2.drawString("Dokopy",drawTextCoordinatesX,drawTextCoordinatesY);

                strWidth = fm.stringWidth(getTextArray()[0] +" €");
                drawTextCoordinatesX = (int) writableAreaTop.getBounds().getWidth()+writableAreaTop.getBounds().x-strWidth;
                g2.drawString(getTextArray()[0] + " €",drawTextCoordinatesX,drawTextCoordinatesY);

                drawTextCoordinatesX = writableAreaBottom.getBounds().x;
                drawTextCoordinatesY = (int) (writableAreaBottom.getBounds().y+fm.getHeight()*0.7);

                g2.drawString("Množstvo",drawTextCoordinatesX,drawTextCoordinatesY);

                strWidth = fm.stringWidth(getTextArray()[1] + " ks");
                drawTextCoordinatesX =  (int) writableAreaBottom.getBounds().getWidth()+writableAreaBottom.getBounds().x-strWidth;
                g2.drawString(getTextArray()[1]+ " ks",drawTextCoordinatesX,drawTextCoordinatesY);


                //Only for debug purposes
//                g2.setPaint(new Color(100,100,100,100));
//                g2.fill(writableAreaTop);
//                g2.fill(writableAreaBottom);
            }
        }
        //TODO: Ensure there is no null??

        g2.dispose();
        repaint();
    }

    public void setText(String text1, String text2){
        text[0] = text1;
        text[1] = text2;
    }

    public String[] getTextArray(){
        try{
            return text;
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    private Font paintVariantTotal(){

        final float fontHeightRatio =  0.45f;

        int newFontSize = (int) (getHeight() * fontHeightRatio);

        newFontSize = Math.max(12, newFontSize);
        newFontSize = Math.min(60, newFontSize);

        Font currentFont = getFont();

        return currentFont.deriveFont((float) newFontSize);
    }

    private Font paintVariantWeight(){
        final float fontHeightRatio =  0.45f;

        int newFontSize = (int) (getHeight() * fontHeightRatio);

        newFontSize = Math.max(12, newFontSize);
        newFontSize = Math.min(60, newFontSize);

        Font currentFont = getFont();

        return currentFont.deriveFont((float) newFontSize);
    }

    private Font paintVariantSplit(Graphics2D g2){
        int insets = 20;
        float thickness = 3.0f;
        g2.setPaint(Colors.BLACK_TEXT.getColor());

        float[] dottedPattern = {15.0f, 15.0f};
        Stroke dottedStroke = new BasicStroke(
                thickness,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                10.0f,
                dottedPattern,
                8.0f
        );
        g2.setStroke(dottedStroke);
        g2.drawLine(insets, getHeight() /2,getWidth()-insets,getHeight() /2);


        final float fontHeightRatio =  0.55f;

        int newFontSize = (int) ((float) getHeight() /2 * fontHeightRatio);

        newFontSize = Math.max(12, newFontSize);
        newFontSize = Math.min(60, newFontSize);
        Font currentFont = getFont();

        return currentFont.deriveFont((float) newFontSize);
    }


}
