package views.Components;

import assets.Colors;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class ChonkyButton extends JButton {
    protected Shape innerRectangle;
    private final Insets insets = new Insets(60,25,60,25);

    Color color;
    public ChonkyButton(Color color){
        this.color = color;
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setMargin(insets);
    }

    public ChonkyButton(String text, Color color) {
        super(text);
        this.color = color;
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Roboto", Font.BOLD, 40));
        setMargin(insets);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel buttonModel = getModel();

        if (buttonModel.isPressed()){
            drawButtonPressed(g2);
        } else {
            drawButtonIdle(g2);
        }

        g2.dispose();
        super.paintComponent(g);
    }


    private void drawButtonIdle(Graphics2D g2){
        //setting width and height
        int width = getWidth();
        int height = getHeight();

        //setting main rectangle
        int margin = Math.max(5,(int) (Math.min(width,height)*0.1));
        int mainArc = (int) (height * 0.3);

        g2.setPaint(ColorManipulation.darken(color,0.93f));
        g2.fillRoundRect(margin/2,margin/2,width-margin,height-margin,mainArc,mainArc);

        //setting shadow rectangle
        int heightZ = Math.max(5,(int) (Math.min(width,height)*0.25)) + margin/2;
        double marginShadow = Math.max(5,(int) (Math.min(width,height)*0.1));
        int mainArcShadow = (int) (height * 0.3);

        Rectangle2D topRect = new Rectangle2D.Double(marginShadow, marginShadow/2,width,marginShadow*1.5);
        Rectangle2D rightRect = new Rectangle2D.Double(width-margin*2,0,margin*2, (double) height /2);
        Rectangle2D rightSquare = new Rectangle2D.Double(width-margin-mainArcShadow,0,margin+mainArcShadow,margin+mainArcShadow);

        Polygon triangleLeft = new Polygon();
        triangleLeft.addPoint(margin,0);
        triangleLeft.addPoint(heightZ+mainArcShadow,heightZ+mainArcShadow);
        triangleLeft.addPoint(0,heightZ+mainArcShadow);

        Polygon triangleRight = new Polygon();
        triangleRight.addPoint(width-margin,height);
        triangleRight.addPoint(width-margin-mainArcShadow,height/2);
        triangleRight.addPoint(width,height/2);


        Area areaTopRect = new Area(topRect);
        Area areaRightRect = new Area(rightRect);
        Area areaRightSquare = new Area(rightSquare);
        Area areaTriangleLeft = new Area(triangleLeft);
        Area areaTriangleRight = new Area(triangleRight);

        Area areaShadow = new Area();
        areaShadow.add(areaTopRect);
        areaShadow.add(areaRightRect);
        areaShadow.add(areaRightSquare);
        areaShadow.subtract(areaTriangleLeft);
        areaShadow.add(areaTriangleRight);

        //Overlap with mainRect
        areaShadow.intersect(new Area(new RoundRectangle2D.Double((double) margin /2, (double) margin /2,width-margin,height-margin,mainArc,mainArc)));

        Color shadowColor = ColorManipulation.darken(color,0.79f);
        Color shadowColorAlpha = new Color(shadowColor.getRed(),
                shadowColor.getGreen(),
                shadowColor.getBlue(),
                189
        );
        g2.setPaint(shadowColorAlpha);
        g2.fill(areaShadow);

        //setting inner rectangle
        int marginInner = Math.max(5,(int) ((Math.min(width,height)- (double) margin )*0.22));
        int innerArc = (int) (height * 0.25);
        int innerWidth = width-heightZ ;
        int innerHeight = height-heightZ;

        //TODO: Make inner shadow/glow (Check figma)

        innerRectangle = new RoundRectangle2D.Double(heightZ - margin, marginInner- (double) margin /2,innerWidth- (double) margin /2,innerHeight,innerArc,innerArc);
        g2.setPaint(color);
        g2.fill(innerRectangle);

        float fontHeightRatio =  0.35f;
        FontMetrics fm = getFontMetrics(getFont());
        Font currentFont = getFont();
        Font scaledFont;
        int newFontSize = 0;
        while(fm.stringWidth(getText()) > innerRectangle.getBounds().getWidth()- (double) marginInner /2){
            if(newFontSize==12){
                return;
            }
            fm = getFontMetrics(getFont());
            newFontSize = (int) (innerHeight * fontHeightRatio);
            fontHeightRatio-=0.01f;
            newFontSize = Math.max(12, newFontSize);
            newFontSize = Math.min(60, newFontSize);
            scaledFont = currentFont.deriveFont((float) newFontSize);
            setFont(scaledFont);
        }
        setForeground(Colors.BLACK_TEXT.getColor());
    }

    private void drawButtonPressed(Graphics2D g2) {

        //setting width and height
        int width = getWidth();
        int height = getHeight();

        //setting main rectangle
        int margin = Math.max(5, (int) (Math.min(width, height) * 0.1));
        int mainArc = (int) (height * 0.3);

        g2.setPaint(ColorManipulation.darken(color, 0.91f));
        g2.fillRoundRect(margin / 2, margin / 2, width - margin, height - margin, mainArc, mainArc);

        //setting shadow rectangle
        int heightZ = Math.max(5, (int) (Math.min(width, height) * 0.25)) + margin ;
        double marginShadow = Math.max(5, (int) (Math.min(width, height) * 0.1));
        int mainArcShadow = (int) (height * 0.3);

        Rectangle2D topRect = new Rectangle2D.Double(marginShadow, marginShadow / 2, width, marginShadow * 1.5);
        Rectangle2D rightRect = new Rectangle2D.Double(width - margin * 2, 0, margin * 2, (double) height / 2);
        Rectangle2D rightSquare = new Rectangle2D.Double(width - margin - mainArcShadow, 0, margin + mainArcShadow, margin + mainArcShadow);

        Polygon triangleLeft = new Polygon();
        triangleLeft.addPoint(margin, 0);
        triangleLeft.addPoint(heightZ + mainArcShadow, heightZ + mainArcShadow);
        triangleLeft.addPoint(0, heightZ + mainArcShadow);

        Polygon triangleRight = new Polygon();
        triangleRight.addPoint(width - margin, height);
        triangleRight.addPoint(width - margin - mainArcShadow, height / 2);
        triangleRight.addPoint(width, height / 2);


        Area areaTopRect = new Area(topRect);
        Area areaRightRect = new Area(rightRect);
        Area areaRightSquare = new Area(rightSquare);
        Area areaTriangleLeft = new Area(triangleLeft);
        Area areaTriangleRight = new Area(triangleRight);

        Area areaShadow = new Area();
        areaShadow.add(areaTopRect);
        areaShadow.add(areaRightRect);
        areaShadow.add(areaRightSquare);
        areaShadow.subtract(areaTriangleLeft);
        areaShadow.add(areaTriangleRight);

        //Overlap with mainRect
        areaShadow.intersect(new Area(new RoundRectangle2D.Double((double) margin / 2, (double) margin / 2, width - margin, height - margin, mainArc, mainArc)));

        Color shadowColor = ColorManipulation.darken(color, 0.76f);
        Color shadowColorAlpha = new Color(shadowColor.getRed(),
                shadowColor.getGreen(),
                shadowColor.getBlue(),
                189
        );
        g2.setPaint(shadowColorAlpha);
        g2.fill(areaShadow);

        //setting inner rectangle
        int marginInner = Math.max(5, (int) ((Math.min(width, height) - (double) margin) * 0.22));
        int innerArc = (int) (height * 0.25);
        int innerWidth = width - heightZ;
        int innerHeight = height - heightZ;

        //TODO: Make inner shadow/glow (Check figma)

        innerRectangle = new RoundRectangle2D.Double(heightZ*0.9 - margin, marginInner*1.2 - (double) margin / 2, innerWidth - (double) margin / 2, innerHeight, innerArc, innerArc);
        g2.setPaint(ColorManipulation.darken(color,0.99f));
        g2.fill(innerRectangle);

        float fontHeightRatio = 0.35f;
        FontMetrics fm = getFontMetrics(getFont());
        Font currentFont = getFont();
        Font scaledFont;
        int newFontSize = 0;
        while (fm.stringWidth(getText()) > innerRectangle.getBounds().getWidth() - (double) marginInner / 2) {
            if (newFontSize == 12) {
                return;
            }
            fm = getFontMetrics(getFont());
            newFontSize = (int) (innerHeight * fontHeightRatio);
            fontHeightRatio -= 0.01f;
            newFontSize = Math.max(12, newFontSize);
            newFontSize = Math.min(60, newFontSize);
            scaledFont = currentFont.deriveFont((float) newFontSize);
            setFont(scaledFont);
        }
    }
}
