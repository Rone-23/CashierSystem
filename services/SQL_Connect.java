package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String url = "jdbc:sqlite:cashier_system_database.db";
            conn = DriverManager.getConnection(url);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA busy_timeout = 5000;"); //wait time 5s
            }
        } catch (Exception e) {
            System.out.println("Error unable to establish connection: " + e.getMessage());
        }
    }

    public void removeFromStock(double quantityToSubtract, String name) {
        String sql = "UPDATE Article SET stock = stock - ? WHERE article_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, quantityToSubtract);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
    }

    public Item[] getItems(String type) throws SQLException {
        String sql = "select name, price from article " +
                "join subtype on article.subtype_id= subtype.subtype_id " +
                "join type on subtype.subtype_id = type.type_id " +
                "where type.type_name= ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,type);
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new ItemCountable(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        1.0
                ));
            }

            return items.toArray(new Item[0]);
        }
    }

    public Item[] getItems(String type, String subtype) throws SQLException {
        String sql = "select name, price from article " +
                "join subtype on article.subtype_id= subtype.subtype_id  " +
                "join type on subtype.subtype_id = type.type_id " +
                "where type.type_name = ? and subtype.subtype_name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,type);
            pstmt.setString(2,subtype);
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new ItemCountable(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        1.0
                ));
            }

            return items.toArray(new Item[0]);
        }
    }

    public Item[] getAllItems() throws SQLException {
        String sql = "select name, price from article";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new ItemCountable(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        1.0
                ));
            }

            return items.toArray(new Item[0]);
        }
    }


    public String[] getSubTypes(String type) throws SQLException {
        String sql = "select subtype_name from subtype join type on subtype.parent_type_id = type.type_id where type_name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            pstmt.setString(1, type);
            pstmt.executeUpdate();

            List<String> subtypeNames = new ArrayList<>();
            while (rs.next()) {
                subtypeNames.add(rs.getString(1));
            }

            return subtypeNames.toArray(new String[0]);
        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
        return null;
    }


    public int getRows(String tableName){
        String sql = "select count(*) from " + tableName;

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

    public int createTransaction(int cashierID ,int customerID){
        String sqlInsert =  "insert into \"transaction\" (cashier_id, created_at, customer_id)" +
                "values (?,current_timestamp, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setInt(1, cashierID);
            pstmt.setInt(2, customerID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sqlGet =  "select transaction_id from \"transaction\" order by transaction_id desc limit 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGet)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return  0;
    }

    public int getLastTransactionNumber(){
        String sql = "select count(*) from \"transaction\" " +
                "where date(created_at) = date('now');";

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

    public Item[] getAllArticlesFromPastTransaction(int transactionID) throws SQLException{
        String sql = "select name, amount, price_at_sale from in_transaction " +
                "join article on in_transaction.article_id = article.article_id " +
                "where transaction_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,transactionID);
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new ItemCountable(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("amount")
                ));
            }

            return items.toArray(new Item[0]);
        }
    }

    public int logToDB(
            int transactionId,
            int articleId,
            int addAmount,
            int priceAtSale
    ) throws SQLException {

        conn.setAutoCommit(false);

        try {
            try (PreparedStatement stockStmt = conn.prepareStatement(
                    "SELECT stock FROM article WHERE article_id = ?"
            )) {
                stockStmt.setInt(1, articleId);
                ResultSet rs = stockStmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("Article not found");
                }

                int stock = rs.getInt("stock");
                if (stock < addAmount) {
                    throw new SQLException("Not enough stock");
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(
                    """
                    INSERT INTO in_transaction
                    (transaction_id, article_id, amount, price_at_sale)
                    VALUES (?, ?, ?, ?)
                    ON CONFLICT(transaction_id, article_id)
                    DO UPDATE SET amount = amount + ?
                    """
            )) {
                insertStmt.setInt(1, transactionId);
                insertStmt.setInt(2, articleId);
                insertStmt.setInt(3, addAmount);
                insertStmt.setInt(4, priceAtSale);
                insertStmt.setInt(5, addAmount);

                insertStmt.executeUpdate();
            }

            try (PreparedStatement updateStockStmt = conn.prepareStatement(
                    "UPDATE article SET stock = stock - ? WHERE article_id = ?"
            )) {
                updateStockStmt.setInt(1, addAmount);
                updateStockStmt.setInt(2, articleId);
                updateStockStmt.executeUpdate();
            }

            try (PreparedStatement getStockStmt = conn.prepareStatement(
                    "SELECT stock FROM article WHERE article_id = ?"
            )) {
                getStockStmt.setInt(1, articleId);
                ResultSet rs = getStockStmt.executeQuery();
                if(rs.next()){
                    return rs.getInt("stock");
                }
            }

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
        return 0;
    }

    public int getArticleID(String articleName){
        String sql =  "SELECT article_id FROM Article WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, articleName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("article_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
