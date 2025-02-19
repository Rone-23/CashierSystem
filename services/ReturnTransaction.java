package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class ReturnTransaction {
    private static int transactionID;
    private LocalDateTime returnDateTime;
    private Date transactionDateTime;
    private final Map<String,Item> itemsInTransaction;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public ReturnTransaction(String json, String inputDate) throws JsonProcessingException {
        this.returnDateTime = LocalDateTime.now();
        this.returnDateTime.format(dtf);

//        String transactionYear = inputDate.substring(0,4);
//        String transactionMonth = inputDate.substring(4,6);
//        String transactionDay= inputDate.substring(6,8);
//        String transactionReceiptNumber = inputDate.substring(8);
//        String json = sql.getLogFromDB(String.format("%s-%s-%s",transactionYear,transactionMonth,transactionDay),Integer.parseInt(transactionReceiptNumber));
        ObjectMapper mapper = new ObjectMapper();
        this.itemsInTransaction = mapper.readValue(json, new TypeReference<Map<String, Item>>() {});

        //TODO: add it back to articles table
        //TODO: add new table and log to it after making a return
    }

    public Item returnItem(Item item){
        if(this.itemsInTransaction.containsValue(item)){
            this.itemsInTransaction.remove(item.getName());
        }
        throw new RuntimeException("Item was not found in transaction");
    }
    public Map<String, Item> getItemsInTransaction(){return this.itemsInTransaction;}
}
