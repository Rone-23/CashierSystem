package utility;

import services.Item;
import services.ItemUncountable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnReceipt {
    private static final StringBuilder receiptBuilder = new StringBuilder();
    private static final String adress = "Vladimíra Predmerského 2491/1, 91105";
    private static final int DIC_DPH = 0000000000;
    private static final int ICO = 00000000;
    private static final String KP = "xxxxxxxxxxxxxxxxx";

    public static StringBuilder makeReceipt(Item[] keptItems, Item[] returnedItems, String dateTime, int transactionID) {
        receiptBuilder.setLength(0);
        int cashPrice = 0;
        int cashReturned = 0;

        // Header
        receiptBuilder.append(String.format(" %25s\n", "Cashing System Tomas"));
        receiptBuilder.append(String.format(" %34s\n", "Bajkalská 19/A. 82102 Bratislava"));
        receiptBuilder.append(String.format(" %29s\n", "mestská časť Ružinov"));
        receiptBuilder.append(String.format(" %37s\n", adress));
        receiptBuilder.append(String.format(" %22s\n", "Trenčín"));
        receiptBuilder.append(String.format(" DIC :%s  IČ DPH: SK%s\n", DIC_DPH, DIC_DPH));
        receiptBuilder.append(String.format(" IČO :%s   KP: %s\n", ICO, KP));
        receiptBuilder.append(String.format("%s         č.bloku: %s\n", dateTime, transactionID));
        receiptBuilder.append("----------------------------------------\n");
        // Grouping items to print them together
        List<String> orderedNames = new ArrayList<>();
        Map<String, Item> keptMap = new HashMap<>();
        Map<String, Item> returnedMap = new HashMap<>();
        // Process Kept Items
        if (keptItems != null) {
            for (Item item : keptItems) {
                String name = item.getName();
                keptMap.put(name, item);
                if (!orderedNames.contains(name)) {
                    orderedNames.add(name);
                }
            }
        }
        // Process Returned Items
        if (returnedItems != null) {
            for (Item item : returnedItems) {
                String name = item.getName();
                cashReturned+=item.getPrice();
                returnedMap.put(name, item);
                if (!orderedNames.contains(name)) {
                    orderedNames.add(name);
                }
            }
        }

        for (String name : orderedNames) {
            Item keptItem = keptMap.get(name);
            Item returnedItem = returnedMap.get(name);

            if (keptItem != null) {
                appendItemLine(keptItem, false);
                cashPrice += keptItem.getPrice() * keptItem.getAmount();
            }

            if (returnedItem != null) {
                appendItemLine(returnedItem, true);
                cashPrice -= returnedItem.getPrice() * returnedItem.getAmount();
            }
        }

        receiptBuilder.append("----------------------------------------\n");
        receiptBuilder.append(String.format("SPOLU EUR %30.2f\n", cashPrice * 0.01));
        receiptBuilder.append(String.format("VRÁTENÉ %32.2f\n", cashReturned *0.01));
        receiptBuilder.append("----------------------------------------\n");

        System.out.println(receiptBuilder);
        return receiptBuilder;
    }

    private static void appendItemLine(Item item, boolean isReturn) {
        String name = item.getName();

        if (name.length() > 18) {
            name = name.substring(0, 18);
        }

        double amount = item.getAmount();
        double price = item.getPrice() * 0.01;

        String prefix = isReturn ? "-" : " ";

        if (item.getClass() == ItemUncountable.class) {
            String amountStr = String.format("%s%.3fkg", prefix, amount * 0.001);
            double rowTotal = price * amount * 0.001;
            if (isReturn) rowTotal = -rowTotal;

            receiptBuilder.append(String.format("%-18s %8s %9.2f B\n", name, amountStr, rowTotal));
        } else {
            String amountStr = String.format("%s%sks", prefix, (int)amount);

            double rowTotal = price * amount;
            if (isReturn) rowTotal = -rowTotal;

            receiptBuilder.append(String.format("%-18s %8s %9.2f B\n", name, amountStr, rowTotal));
        }
    }
}