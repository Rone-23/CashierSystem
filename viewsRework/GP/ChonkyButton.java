package viewsRework.GP;

import assets.Colors;
import com.itextpdf.awt.geom.Rectangle;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class ChonkyButton extends JButton {

    Color color;
    public ChonkyButton(String text, Color color) {
        super(text);
        this.color = color;
        setContentAreaFilled(false); // prevent default fill
        setOpaque(false);
        setFocusPainted(false);      // remove ugly focus rectangle
        setBorderPainted(false);     // we will draw our own border
        setFont(new Font("Roboto", Font.BOLD, 40)); // style text
        setForeground(Colors.BLACK_TEXT.getColor());
        setMargin(new Insets(55,55,40,40));

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //setting width and height
        int width = getWidth();
        int height = getHeight();

        //setting main rectangle
        int margin = Math.max(5,(int) (Math.min(width,height)*0.1));
        int mainArc = (int) (height * 0.3);

        g2.setPaint(ColorManipulation.darken(color,0.91f));
        g2.fillRoundRect(margin,margin,width-margin,height-margin,mainArc,mainArc);

        //setting shadow rectangle
        int heightZ = Math.max(5,(int) (Math.min(width,height)*0.25)) + margin;
        double marginShadow = Math.max(5,(int) (Math.min(width,height)*0.1));
        int mainArcShadow = (int) (height * 0.3);

        Rectangle2D topRect = new Rectangle2D.Double(marginShadow, marginShadow,width,marginShadow*1.5);
        Rectangle2D rightRect = new Rectangle2D.Double(width-margin,0,margin,height);
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
        areaShadow.intersect(new Area(new RoundRectangle2D.Double(margin,margin,width-margin,height-margin,mainArc,mainArc)));

        Color shadowColor = ColorManipulation.darken(color,0.73f);
        Color shadowColorAlpha = new Color(shadowColor.getRed(),
                shadowColor.getGreen(),
                shadowColor.getBlue(),
                125
                );
//        g2.setPaint(new GradientPaint(width/2,height/2,new Color(0,0,0,0) , width, 0, shadowColorAlpha));
        g2.setPaint(shadowColor);
        g2.fill(areaShadow);

        //setting inner rectangle
        int marginInner = Math.max(5,(int) ((Math.min(width,height)-margin )*0.22));
        int innerArc = (int) (height * 0.25);
        int innerWidth = width-heightZ;
        int innerHeight = height-heightZ;

        g2.setPaint(color);
        g2.fillRoundRect(heightZ - margin, marginInner,innerWidth,innerHeight,innerArc,innerArc);

        final float fontHeightRatio =  0.3f;

        int newFontSize = (int) (innerHeight * fontHeightRatio);

        newFontSize = Math.max(12, newFontSize);
        newFontSize = Math.min(60, newFontSize);

        Font currentFont = getFont();
        Font scaledFont = currentFont.deriveFont((float) newFontSize);

        setFont(scaledFont);

        g2.dispose();
        super.paintComponent(g);


    }
}
