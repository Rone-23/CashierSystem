import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {
    public static void main(String[] args) {
        // Example data for the receipt
        String storeName = "Store Name";
        String address = "123 Main Street, City, ST";
        String phone = "(555) 123-4567";
        String date = "2024-11-08";
        String time = "14:35";
        int receiptNumber = 666;

// Items purchased
        String[][] items = {
                {"Milk", "2", "2.99", "5.98"},
                {"Bread", "1", "1.49", "1.49"},
                {"Eggs", "1", "3.49", "3.49"}
        };

// Totals
        double subtotal = 10.96;
        double tax = 0.55;
        double total = 11.51;
        String paymentMethod = "Credit Card";
        double amountTendered = 12.00;
        double change = 0.49;

        StringBuilder receiptBuilder = new StringBuilder();

// Add header information
        receiptBuilder.append("------------------------------------------------------\n");
        receiptBuilder.append(String.format("%30s\n", storeName));
        receiptBuilder.append(String.format("%30s\n", address));
        receiptBuilder.append(String.format("Phone: %s\n", phone));
        receiptBuilder.append("\n");

        receiptBuilder.append(String.format("Date: %s   Time: %s\n", date, time));
        receiptBuilder.append(String.format("Receipt #%d\n", receiptNumber));
        receiptBuilder.append("------------------------------------------------------\n");

        receiptBuilder.append(String.format("%-15s %-5s %-8s %-8s\n", "Item", "Qty", "Price", "Total"));
        receiptBuilder.append("------------------------------------------------------\n");

// Add items
        for (String[] item : items) {
            receiptBuilder.append(String.format("%-15s %-5s %-8s %-8s\n", item[0], item[1], item[2], item[3]));
        }

// Add totals
        receiptBuilder.append("------------------------------------------------------\n");
        receiptBuilder.append(String.format("Subtotal%36.2f\n", subtotal));
        receiptBuilder.append(String.format("Tax%41.2f\n", tax));
        receiptBuilder.append(String.format("Total%39.2f\n", total));
        receiptBuilder.append("------------------------------------------------------\n");

// Payment details
        receiptBuilder.append(String.format("Paid with: %s\n", paymentMethod));
        receiptBuilder.append(String.format("Amount Tendered: %.2f\n", amountTendered));
        receiptBuilder.append(String.format("Change: %.2f\n", change));
        receiptBuilder.append("------------------------------------------------------\n");
        receiptBuilder.append("Thank you for shopping with us!\n");
        receiptBuilder.append("Visit us at www.storewebsite.com\n");
        receiptBuilder.append("------------------------------------------------------\n");

        String receiptText = receiptBuilder.toString();
        System.out.println(receiptBuilder.toString());  // For testing, print to console

//        receiptText = receiptBuilder.toString(); // Assuming receiptBuilder was created

        // Define file path and write to file
        String filePath = "receipt.txt";

//        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
//            writer.print(receiptText);
//            System.out.println("Receipt saved to " + filePath);
//        } catch (IOException e) {
//            System.err.println("Error writing to file: " + e.getMessage());
//        }
    }
}
