package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQL_Connect {
    private Connection conn;
    static SQL_Connect instance;

    private SQL_Connect() {
        connect();
    }

    public static SQL_Connect getInstance(){
        if(instance == null){
            instance = new SQL_Connect();
        }
        return instance;
    }

    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:shop_rework.db";
            conn = DriverManager.getConnection(url);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA busy_timeout = 5000;"); //wait time 5s
            }
        } catch (Exception e) {
            System.out.println("Error unable to establish connection: " + e.getMessage());
        }
    }

    public void removeFromStock(double quantityToSubtract, String name) {
        String sql = "UPDATE Articles SET stock = stock - ? WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, quantityToSubtract);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
    }

    public void addToStock(double quantityToAdd, String name) {
        String sql = "UPDATE Articles SET stock = stock + ? WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, quantityToAdd);
            pstmt.setString(2, name);
                pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
    }

    public void addArticle(String nameOfArticle, double price, double initialStock, String type, String subtype){
        String sql = "INSERT INTO Articles (name,price,stock,type,subtype) VALUES (?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nameOfArticle);
            pstmt.setDouble(2, price);
            pstmt.setDouble(3, initialStock);
            pstmt.setString(4, type);
            pstmt.setString(5, subtype);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding article: " + e.getMessage());
        }
    }

    public double getStock(int article_id){
        String sql = "SELECT stock FROM Articles WHERE article_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,article_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getDouble("stock");
            }
        }
        catch (SQLException e){
            System.out.println("Error getting stock: " + e.getMessage());
        }
        return 0;
    }

    public String[] getNames(String subtype) throws SQLException {
        String sql = "SELECT name FROM Articles WHERE subtype = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,subtype);
            ResultSet rs = pstmt.executeQuery();

            List<String> names = new ArrayList<>();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }

            return names.toArray(new String[0]);
        }
    }

    public String[] getTypes() throws SQLException {
        String sql = "SELECT type FROM Articles";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            Set<String> types = new HashSet<>() {
            };
            while (rs.next()) {
                if(!types.contains(rs.getString("type"))){
                    types.add(rs.getString("type"));
                }
            }

            return types.toArray(new String[0]);
        }
    }

    public String[] getSubTypes(String type) throws SQLException {
        String sql = "SELECT subtype FROM Articles WHERE type = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();

            Set<String> subtypes = new HashSet<>() {
            };
            while (rs.next()) {
                if(!subtypes.contains(rs.getString("subtype"))){
                    subtypes.add(rs.getString("subtype"));
                }
            }

            return subtypes.toArray(new String[0]);
        }
    }

    public double getPrice(int article_id){
        String sql = "SELECT price FROM Articles WHERE article_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,article_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getDouble("price");
            }
        }
        catch (SQLException e){
            System.out.println("Error getting price: " + e.getMessage());
        }
        return 0;
    }

    public String getString(int article_id) throws SQLException{
        String sql = "SELECT name FROM Articles WHERE article_id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, article_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString("name");
            }
            throw new RuntimeException("Cant find such id");
    }

    public int getRows(String tableName){
        String sql = "SELECT COUNT(*) FROM " + tableName;

        try (PreparedStatement pstmt = conn.prepareStatement(sql) ){
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
               return rs.getInt(1);
            }

        }
        catch (SQLException e){
            System.out.println("Error getting number of rows : " + e.getMessage());
        }
        return 0;
    }

    public void logToDB(String itemList, double totalAmount,int transactionId){
        String sql =  "INSERT INTO Transactions (items, total_amount, transaction_dayid) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, itemList);
            pstmt.setDouble(2, totalAmount);
            pstmt.setInt(3, transactionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLogFromDB(String date, int numberOfReceipt){
        String sql =  "SELECT items FROM Transactions WHERE transaction_dayid = ? and  datetime(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, numberOfReceipt);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString("items");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getLastTimeStamp(){
        String sql =  "SELECT transaction_date FROM Transactions WHERE transaction_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,getRows("Transactions"));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString("transaction_date");
            }

        } catch (SQLException e) {
            System.out.println("Error getting name : " + e.getMessage());
        }
        return null;
    }

}
