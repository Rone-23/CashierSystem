package assets;

import java.awt.*;

public class Assets {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public double width = (screenSize.height*4/3)-(screenSize.height*4/300);
    public double height = (screenSize.height)-(screenSize.height*4/300);
    public int buttonWidth = 183;
    public int buttonHeight = 133;
    public Dimension buttonD = new Dimension(buttonWidth,buttonHeight);
    public Dimension navButtonD = new Dimension(100,100);
    public int navButtonWidth = (int)width/3;
    public int navButtonHeight = 100;
    //main layout
    public double leftPanelFactor = 0.413;
    public double rightPanelFactor = 1-leftPanelFactor;
    public double topPanelFactor = 0.945;
    public double bottomPanelFactor = 1-topPanelFactor;
    public double mainInsets = width*0.01;
    //left content layout
    public double leftPanelDisplayPartFactor = 0.46;
    public double leftPanelKeyboardPartFactor = 1-leftPanelDisplayPartFactor;
    //keypad layout
    public double keypadPanelFactor = 0.77;
    public double keypadUtilityButtonsFactor = 1-keypadPanelFactor;
    //buttons size
    public double keyButtonFactor = leftPanelFactor*(keypadPanelFactor/3);
    public double keypadButtonWidth = (width*keyButtonFactor);
    public double keypadButtonHeight = keypadButtonWidth*0.89;
    //utility dimension
    public Dimension utilityDimension = new Dimension((int) (width*leftPanelFactor*keypadUtilityButtonsFactor),(int)(width*leftPanelFactor*keypadUtilityButtonsFactor));
    //display area sizes
    public double mainDisplayFactor = 0.775;
    public Dimension dialsD = new Dimension(670,1162);
    public Dimension operationsD = new Dimension(956,1162);
}
