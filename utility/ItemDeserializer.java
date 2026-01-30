package utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import services.Item;
import services.ItemCountable;
import services.ItemUncountable;

import java.io.IOException;
import java.util.ArrayList;

public class ItemDeserializer  extends JsonDeserializer<Item> {

    @Override
    public Item deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if(node.has("amount") && node.has("weight")){


            String name = node.get("name").asText();
            int price = node.get("price").asInt();
            ArrayList<Double> weight = new ArrayList<>();
            JsonNode weightList = node.get("weightList");
            for(JsonNode value : weightList){
                weight.add(value.asDouble());
            }
            return new ItemUncountable(name, price, weight);
        }
        else if(node.has("amount")){
            String name = node.get("name").asText();
            int price = node.get("price").asInt();
            int amount = node.get("amount").asInt();
            return new ItemCountable(name,price,amount);
        }

        throw new RuntimeException("Unknown item type");
    }
}
