package utility;

import java.awt.*;

public class GridBagConstraintsBuilder {

    public static GridBagConstraints buildGridBagConstraints(){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        return gridBagConstraints;
    }
    public static GridBagConstraints buildGridBagConstraints(int gridX){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = gridX;
        return gridBagConstraints;
    }
    public static GridBagConstraints buildGridBagConstraints(int gridX,int gridY){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = gridX;
        gridBagConstraints.gridy = gridY;
        return gridBagConstraints;
    }
}
