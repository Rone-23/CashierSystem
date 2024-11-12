package Services;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class CashOut {
    SQL_Connect conn = new SQL_Connect();


    public CashOut(){
    }

    public String makeJSON(List listOfIDs){
        JSONObject items = new JSONObject();
        for (int positionInArrayList=0; positionInArrayList<listOfIDs.toArray().length;positionInArrayList++){
            JSONObject item = new JSONObject();
            item.put("name", conn.getString((int) listOfIDs.toArray()[positionInArrayList]));
            item.put("price", conn.getPrice((int) listOfIDs.toArray()[positionInArrayList]));
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
        List<String> name = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        String[] nameArray = name.toArray(String[]::new);
        Double[] priceArray = price.toArray(Double[]::new);

        //make name and price array
        for (int i = 0; i<readJSON(jsonString).length;i++){
            name.add(readJSON(jsonString)[i].getString("name"));
            price.add(readJSON(jsonString)[i].getDouble("price"));
        }

        try{
            File receipt = new File(path.toString());
            if (receipt.createNewFile()){
                System.out.println("File created: "+receipt.getName());
            }
            else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(receipt);
            fileWriter.write(new Receipt().makeReceipt( nameArray,1,priceArray).toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        price.clear();
        name.clear();
        count.clear();

    }
}
