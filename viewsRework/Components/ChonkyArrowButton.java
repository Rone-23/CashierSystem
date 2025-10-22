package viewsRework.Components;

import assets.Colors;
import assets.Constants;

import java.awt.*;

public class ChonkyArrowButton extends ChonkyButton {

    Constants arrowDirection;
    public ChonkyArrowButton(Color color, Constants arrowDirection) {
        super(color);
        this.arrowDirection = arrowDirection;
        setMargin(new Insets(55,55,55,55));
        setPreferredSize(new Dimension(150,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Colors.BLACK_TEXT.getColor());

        int innerRectangleX = super.innerRectangle.getBounds().x;
        int innerRectangleY = super.innerRectangle.getBounds().y;
        int innerRectangleWidth = (int) super.innerRectangle.getBounds().getWidth();
        int innerRectangleHeight = (int) super.innerRectangle.getBounds().getHeight();

        Polygon triangle = new Polygon();
        
        switch(arrowDirection){
            case RIGHT -> {
                triangle.addPoint( innerRectangleX+innerRectangleWidth*2/7, innerRectangleY+ innerRectangleHeight /7);
                triangle.addPoint( innerRectangleX+innerRectangleWidth*6/7, innerRectangleY+ innerRectangleHeight /2);
                triangle.addPoint( innerRectangleX+innerRectangleWidth*2/7, innerRectangleY+ innerRectangleHeight*6/7);
            }
            case LEFT -> {
                triangle.addPoint( innerRectangleX+innerRectangleWidth*5/7, innerRectangleY+ innerRectangleHeight /7);
                triangle.addPoint( innerRectangleX+ innerRectangleWidth /7, innerRectangleY+ innerRectangleHeight /2);
                triangle.addPoint( innerRectangleX+innerRectangleWidth*5/7, innerRectangleY+ innerRectangleHeight*6/7);
            }
            case UP -> {
                triangle.addPoint( innerRectangleX+ innerRectangleWidth /7, innerRectangleY+innerRectangleHeight*6/7);
                triangle.addPoint( innerRectangleX+innerRectangleWidth*6/7, innerRectangleY+ innerRectangleHeight*6/7);
                triangle.addPoint( innerRectangleX+ innerRectangleWidth /2, innerRectangleY+ innerRectangleHeight /7);
            }
            case DOWN -> {
                triangle.addPoint( innerRectangleX+ innerRectangleWidth /7, innerRectangleY+ innerRectangleHeight /7);
                triangle.addPoint( innerRectangleX+innerRectangleWidth*6/7, innerRectangleY+ innerRectangleHeight /7);
                triangle.addPoint( innerRectangleX+ innerRectangleWidth /2, innerRectangleY+ innerRectangleHeight*6/7);
            }
        }

        g2.fillPolygon(triangle);


        g2.dispose();
    }

}
