package utility;

import org.json.JSONObject;
import services.Item;

import java.util.Iterator;
import java.util.Map;

public class JSON {

    public static String makeJSON(Map<String,Item> listOfItems){
        JSONObject items = new JSONObject();
        JSONObject item = new JSONObject();
        Iterator<Item> iterator = listOfItems.values().iterator();
        int index=1;
        while(iterator.hasNext()){
            Item element = iterator.next();
            item.put("name", element.getName());
            item.put("price", element.getPrice());
            item.put("amount", element.getAmount());
            items.put("item"+index, item);
            index++;
        }
        return items.toString();
    }
    //TODO: Add read JSON
}
