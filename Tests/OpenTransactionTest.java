import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import controllers.MakeTransaction;
import services.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

class OpenTransactionTest {
    OpenTransaction openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
    MakeTransaction makeTransaction = new MakeTransaction();
    SimpleModule module = new SimpleModule();
    ObjectMapper mapper = new ObjectMapper();
    @org.junit.jupiter.api.Test
    void addItem() {
        ItemCountable item1 = new  ItemCountable("ROZOK",0.13,1.0);
        ItemCountable item2 = new  ItemCountable("CESNAKOVA-BAGETA",0.13,3.0);
        ItemUncountable item3 = new  ItemUncountable("POMARANC",0.72,2.0);
        ItemUncountable item4 = new  ItemUncountable("POMARANC",0.72,2.32);

        openTransaction.addItem(item1);
        openTransaction.addItem(item2);
        openTransaction.addItem(item3);
        openTransaction.addItem(item4);
        makeTransaction.makeTransaction(openTransaction);
    }
    @org.junit.jupiter.api.Test
    void charTest(){
        String slovo = "20253102121";
        StringBuilder sb= new StringBuilder();
        String year = slovo.substring(0,4);
        String month = slovo.substring(4,6);
        String day= slovo.substring(6,8);
        String cBloku = slovo.substring(8);
        System.out.printf("year %s month %s day %s C bolku %s",year,month,day,cBloku);

    }
    @org.junit.jupiter.api.Test
    void sqlRetrieveFromLog() throws JsonProcessingException {
        String slovo = "202507020";
        String year = slovo.substring(0,4);
        String month = slovo.substring(4,6);
        String day= slovo.substring(6,8);
        String cBloku = slovo.substring(8);

        String json = SQL_Connect.getInstance().getLogFromDB(String.format("%s-%s-%s",year,month,day),Integer.parseInt(cBloku));
        System.out.println(json);
        Map<String,ItemCountable> items = mapper.readValue(json, new TypeReference<Map<String, ItemCountable>>() {
        });


        System.out.println(items.toString());

    }
    @org.junit.jupiter.api.Test
    void testOfJackson() throws JsonProcessingException {
        String json1 = "{\"ROZOK\":{\"totalAmount\":3,\"amount\":3,\"price\":0.13,\"name\":\"ROZOK\"},\"CESNAKOVA-BAGETA\":{\"totalAmount\":3,\"amount\":3,\"price\":0.13,\"name\":\"CESNAKOVA-BAGETA\"}}";
        String json ="{\"ROZOK\":{\"name\":\"ROZOK\",\"price\":0.13,\"amount\":1.0,\"totalAmount\":4.0},\"CESNAKOVA-BAGETA\":{\"name\":\"CESNAKOVA-BAGETA\",\"price\":0.13,\"amount\":3.0,\"totalAmount\":4.0},\"POMARANC\":{\"name\":\"POMARANC\",\"price\":0.72,\"amount\":4.32,\"weight\":2.0,\"weightList\":[2.0,2.32],\"totalAmount\":4.0}}";
        Map<String,Item> items = mapper.readValue(json, new TypeReference<Map<String, Item>>() {});
        for (Item element : items.values().toArray(new Item[0])){

            if(element.getClass()== ItemUncountable.class){
                ItemUncountable el = (ItemUncountable) element;
                System.out.println(((ItemUncountable) element).getWeightList().toString());
            }

        }
    }

}