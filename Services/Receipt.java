package Services;

import java.util.ArrayList;

public class Receipt {
    StringBuilder receiptBuilder = new StringBuilder();
    String adress = "Vladimíra Predmerského 2491/1, 91105";
    int DIC_DPH = 2020312503;
    int ICO = 31347037;
    String KP = "88820203125030905";
    String date = "03-11-2024 20:00:12";
    int numOfReceipt = 666;
    double totalAmount;
    ArrayList <Item>itemArrayList = new ArrayList<>();
    public Receipt(){
    }
    public StringBuilder makeReceipt(Item[] itemArrayList){

        //header
        receiptBuilder.append(String.format(" %25s\n","BILLA s.r.o."));
        receiptBuilder.append(String.format(" %34s\n","Bajkalská 19/A. 82102 Bratislava"));
        receiptBuilder.append(String.format(" %29s\n","mestská časť Ružinov"));
        receiptBuilder.append(String.format(" %37s\n",adress));
        receiptBuilder.append(String.format(" %22s\n","Trenčín"));
        receiptBuilder.append(String.format(" DIC :%s  IČ DPH: SK%s\n",DIC_DPH,DIC_DPH));
        receiptBuilder.append(String.format(" IČO :%s   KP: %s\n",ICO,KP));
        receiptBuilder.append(String.format("%s         č.bloku: %s\n",date,numOfReceipt));
        receiptBuilder.append("----------------------------------------\n");
        //items
        for (Item item : itemArrayList) {
            while (item.getName().length() != 18) {
                item.setName(item.getName() + " ");
            }

            receiptBuilder.append(String.format("%s   %.1fks   %9.2f B\n", item.getName(),
                    item.getAmount(),
                    (item.getPrice()) * item.getAmount()));
            totalAmount += item.getPrice();
        }
        receiptBuilder.append("----------------------------------------\n");
        receiptBuilder.append(String.format("NA ÚHRADU EUR %26.2f\n",totalAmount));
        receiptBuilder.append(String.format("KARTA %34.2f\n",totalAmount));
        receiptBuilder.append("----------------------------------------\n");


        System.out.println(receiptBuilder.toString());
        return receiptBuilder;
    }
}
