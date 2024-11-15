package Services;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class CashOut {
    SQL_Connect conn = new SQL_Connect();
    public CashOut(){
    }

    public String makeJSON(List<Item> listOfItems){

        JSONObject items = new JSONObject();
        for (int positionInArrayList=0; positionInArrayList<listOfItems.toArray().length;positionInArrayList++){
            JSONObject item = new JSONObject();
            item.put("name", listOfItems.get(positionInArrayList).getName());
            item.put("price", listOfItems.get(positionInArrayList).getPrice());
            item.put("amount", listOfItems.get(positionInArrayList).getAmount());
            items.put("item"+(positionInArrayList+1),item);
        }
        return items.toString();
    }
    public JSONObject[] readJSON(String jsonString){
        JSONObject items = new JSONObject(jsonString);
        JSONObject[] itemPair = new JSONObject[items.length()];
        for(int i=0; i<items.length();i++){
            itemPair[i] = items.getJSONObject("item"+(i+1));
        }
        return  itemPair;
    }
    public void makeReceipt(String jsonString){

        String date = conn.getTimeStamp().split(" ")[0];
        String time = conn.getTimeStamp().split(" ")[1];
        String filePath = String.format("%s_%s-%s-%s.txt",date.split(":")[0],time.split(":")[0],time.split(":")[1],time.split(":")[2]);
        Path path = Paths.get("Receipts",filePath);

        //make array  ready to print
        ArrayList<Item> itemList = new ArrayList<>();
        HashMap<String, Item> sortedListoToPrint = new HashMap<>();
        for (int i = 0; i<readJSON(jsonString).length;i++){

            itemList.add(new ItemCountable(
                readJSON(jsonString)[i].getString("name"),
                readJSON(jsonString)[i].getDouble("price"),
                readJSON(jsonString)[i].getDouble("amount")));
        }

        for (Item item:itemList){
            String name = item.getName() ;
            if (sortedListoToPrint.containsKey(name)){
                sortedListoToPrint.get(name).setAmount(sortedListoToPrint.get(name).getAmount()+item.getAmount());
            }
            else{
                sortedListoToPrint.put(name,item);
            }
        }
        Item[] finalArray = sortedListoToPrint.values().toArray(new Item[0]);
        double totalAmount =0;
        for(Item item:finalArray){
            double amount=item.getAmount();
            totalAmount += amount;
        }
        finalArray[0].setTotalAmount(totalAmount);
        try{
            File receipt = new File(path.toString());
            if (receipt.createNewFile()){
                System.out.println("File created: "+receipt.getName());
            }
            else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(receipt);
            fileWriter.write(new Receipt().makeReceipt(finalArray).toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
}
