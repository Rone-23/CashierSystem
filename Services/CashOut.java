package Services;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

//        double[] price = new double[readJSON(jsonString).length];
        ArrayList<Item> itemList = new ArrayList<>();
        ItemCountable[] items = new ItemCountable[itemList.toArray().length];
        ItemCountable ip = new ItemCountable("a",2.,2.);


        //make name and price array
        for (int i = 0; i<readJSON(jsonString).length;i++){

            itemList.add(new ItemCountable(
                readJSON(jsonString)[i].getString("name"),
                readJSON(jsonString)[i].getDouble("price"),
                readJSON(jsonString)[i].getDouble("amount")));
        }

        System.out.println(ip.getInfo());
        System.out.println(Arrays.toString(ip.refactor((itemList))));
        try{
            File receipt = new File(path.toString());
            if (receipt.createNewFile()){
                System.out.println("File created: "+receipt.getName());
            }
            else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(receipt);
            fileWriter.write(new Receipt().makeReceipt(itemList).toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
}
