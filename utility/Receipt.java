package utility;

import services.Item;

import java.util.ArrayList;

public class Receipt {
    private static final StringBuilder receiptBuilder = new StringBuilder();
    private static final String adress = "Vladimíra Predmerského 2491/1, 91105";
    private static final int DIC_DPH = 0000000000;
    private static final int ICO = 00000000;
    private static final String KP = "xxxxxxxxxxxxxxxxx";
//    String date = "03-11-2024 20:00:12";
//    int numOfReceipt = 666;
    ArrayList <Item>itemArrayList = new ArrayList<>();
    public Receipt(){
    }

    public static StringBuilder makeReceipt(Item[] itemArrayList, String dateTime, int transactionID){
        double totalAmount = 0;
        //TODO: Make c bloku counting work now it shows only 1
        //header
        receiptBuilder.append(String.format(" %25s\n","Cashing System Tomas"));
        receiptBuilder.append(String.format(" %34s\n","Bajkalská 19/A. 82102 Bratislava"));
        receiptBuilder.append(String.format(" %29s\n","mestská časť Ružinov"));
        receiptBuilder.append(String.format(" %37s\n",adress));
        receiptBuilder.append(String.format(" %22s\n","Trenčín"));
        receiptBuilder.append(String.format(" DIC :%s  IČ DPH: SK%s\n",DIC_DPH,DIC_DPH));
        receiptBuilder.append(String.format(" IČO :%s   KP: %s\n",ICO,KP));
        receiptBuilder.append(String.format("%s         č.bloku: %s\n",dateTime,transactionID));
        receiptBuilder.append("----------------------------------------\n");
        //items
        for (Item item : itemArrayList) {
            //TODO: check for item name length, adjust for receipt length
//            while (item.getName().length() != 18) {
//
//                item.setName(item.getName() + " ");
//            }

            receiptBuilder.append(String.format("%s   %.1fks   %9.2f B\n", item.getName(), item.getAmount(), item.getPrice() * item.getAmount()));
            totalAmount += item.getPrice() * item.getAmount();
        }
        receiptBuilder.append("----------------------------------------\n");
        receiptBuilder.append(String.format("NA ÚHRADU EUR %26.2f\n",totalAmount));
        receiptBuilder.append(String.format("KARTA %34.2f\n",totalAmount));
        receiptBuilder.append("----------------------------------------\n");


        System.out.println(receiptBuilder);
        return receiptBuilder;
    }
}
