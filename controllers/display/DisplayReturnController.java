package controllers.display;

import services.Item;
import views.Components.Display;

public class DisplayReturnController extends DisplayController{
    public DisplayReturnController(Display display) {
        super(display);
    }

    @Override
    public void onItemAdd(Item item) {
        display.setText(String.format("%.2f",(openTransaction.getTotalWhenReturn()-openTransaction.getMissing())*0.01));
    }
}
