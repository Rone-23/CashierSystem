package views.Components;

import services.Item;

public interface ContainsItem {
    Item getItem();
    String getItemName();
    int getItemPrice();
    int getItemAmount();
}
