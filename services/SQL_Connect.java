package services;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL_Connect {
    private Connection conn;
    private static SQL_Connect instance;

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

    public void removeFromStock(double quantityToSubtract, String name) throws SQLException{
        String sql = "UPDATE Article SET stock = stock - ? WHERE article_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, quantityToSubtract);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        }
    }

    public Item[] getAllItems() throws SQLException {
        String sql = """
                SELECT a.name, a.price,
                    EXISTS (
                        SELECT 1
                        FROM favorite_article f
                        JOIN cashier c on f.cashier_id = c.cashier_id
                        WHERE f.article_id = a.article_id
                        AND f.cashier_id = 1
                    ) as is_favorite
                FROM article a
                INNER JOIN subtype s ON a.subtype_id = s.subtype_id
                INNER JOIN type t ON s.parent_type_id = t.type_id;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = new ItemCountable(
                        rs.getString("name"),
                        rs.getInt("price"),
                        0
                );
                item.setIsFavorite(rs.getInt("is_favorite") > 0);
                items.add(item);
            }

            return items.toArray(new Item[0]);
        }
    }

    public Item[] getItems(String type) throws SQLException {
        String sql = """
                SELECT a.name, a.price,
                    EXISTS (
                        SELECT 1
                        FROM favorite_article f
                        JOIN cashier c on f.cashier_id = c.cashier_id
                        WHERE f.article_id = a.article_id
                        AND f.cashier_id = 1
                    ) as is_favorite
                FROM article a
                INNER JOIN subtype s ON a.subtype_id = s.subtype_id
                INNER JOIN type t ON s.parent_type_id = t.type_id
                WHERE t.type_name = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,type);
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = new ItemCountable(
                        rs.getString("name"),
                        rs.getInt("price"),
                        0
                );
                item.setIsFavorite(rs.getInt("is_favorite") > 0);
                items.add(item);
            }

            return items.toArray(new Item[0]);
        }
    }

    public Item[] getItems(String type, String subtype) throws SQLException {
        String sql = """
                SELECT a.name, a.price,
                    EXISTS (
                        SELECT 1
                        FROM favorite_article f
                        JOIN cashier c on f.cashier_id = c.cashier_id
                        WHERE f.article_id = a.article_id
                        AND f.cashier_id = 1
                    ) as is_favorite
                FROM article a
                INNER JOIN subtype s ON a.subtype_id = s.subtype_id
                INNER JOIN type t ON s.parent_type_id = t.type_id
                WHERE t.type_name = ? AND s.subtype_name = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,type);
            pstmt.setString(2,subtype);
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = new ItemCountable(
                        rs.getString("name"),
                        rs.getInt("price"),
                        0
                );
                item.setIsFavorite(rs.getInt("is_favorite") > 0);
                items.add(item);
            }

            return items.toArray(new Item[0]);
        }
    }

    public String[] getSubTypes(String type) throws SQLException {
        String sql = "select subtype_name from subtype join type on subtype.parent_type_id = type.type_id where type_name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            pstmt.setString(1, type);
            pstmt.executeQuery();

            List<String> subtypeNames = new ArrayList<>();
            while (rs.next()) {
                subtypeNames.add(rs.getString(1));
            }

            return subtypeNames.toArray(new String[0]);
        }
    }


    public int getRows(String tableName) throws SQLException{
        String sql = "select count(*) from " + tableName;

        try (PreparedStatement pstmt = conn.prepareStatement(sql) ){
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
               return rs.getInt(1);
            }

        }
        throw new SQLException("Amount of rows couldn't be retrieved from database. ");
    }

    public int createTransaction(int cashierID ,int customerID) throws SQLException {
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
        }
        throw new SQLException("No last transaction number could be found. ");
    }

    public int getLastTransactionNumber() throws SQLException{
        String sql = "select count(*) from \"transaction\" " +
                "where date(created_at) = date('now');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql) ){
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }

        }
        throw new SQLException("No last transaction number could be found. ");
    }

    public Item[] getAllArticlesFromPastTransaction(int transactionID) throws SQLException{
        String sql = """
                select name, amount, price_at_sale from in_transaction
                join article on in_transaction.article_id = article.article_id
                where transaction_id = ?
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,transactionID);
            ResultSet rs = pstmt.executeQuery();

            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new ItemCountable(
                        rs.getString("name"),
                        rs.getInt("price_at_sale"),
                        rs.getInt("amount")
                ));
            }

            return items.toArray(new Item[0]);
        }
    }

    /**
     * Calculates the "Original Purchase Amount" by summing current transaction quantity
     * and already returned quantity. This is the absolute maximum allowed for this item.
     */
    public int getMaxAllowedAmount(int transactionId, int articleId) throws SQLException {
        int inTrans = 0;
        int inReturns = 0;

        String sql = "SELECT " +
                "(SELECT amount FROM in_transaction WHERE transaction_id = ? AND article_id = ?) as trans_qty, " +
                "(SELECT amount FROM returned_items WHERE transaction_id = ? AND article_id = ?) as return_qty";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.setInt(2, articleId);
            pstmt.setInt(3, transactionId);
            pstmt.setInt(4, articleId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                inTrans = rs.getInt("trans_qty");
                inReturns = rs.getInt("return_qty");
            }
        }
        return inTrans + inReturns;
    }

    /**
     * Moves items from the transaction to returns.
     * Constraint: Cannot return more than what is currently in the transaction.
     */
    public void returnItem(int transactionId, int articleId, int amountToReturn) throws SQLException {
        if (amountToReturn <= 0) return;

        try {
            conn.setAutoCommit(false);

            // Verify current amount in transaction
            int currentInTrans = 0;
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM in_transaction WHERE transaction_id = ? AND article_id = ?")) {
                pstmt.setInt(1, transactionId);
                pstmt.setInt(2, articleId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) currentInTrans = rs.getInt("amount");
            }

            if (amountToReturn > currentInTrans) {
                throw new SQLException("Cannot return " + amountToReturn + " items. Only " + currentInTrans + " are in the active transaction.");
            }

            // 1. Decrease Transaction
            if (currentInTrans == amountToReturn) {
                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM in_transaction WHERE transaction_id = ? AND article_id = ?")) {
                    pstmt.setInt(1, transactionId);
                    pstmt.setInt(2, articleId);
                    pstmt.executeUpdate();
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement("UPDATE in_transaction SET amount = amount - ? WHERE transaction_id = ? AND article_id = ?")) {
                    pstmt.setInt(1, amountToReturn);
                    pstmt.setInt(2, transactionId);
                    pstmt.setInt(3, articleId);
                    pstmt.executeUpdate();
                }
            }

            // 2. Increase Returns
            String upsertReturn = "INSERT INTO returned_items (transaction_id, article_id, amount) VALUES (?, ?, ?) " +
                    "ON CONFLICT(transaction_id, article_id) DO UPDATE SET amount = amount + EXCLUDED.amount";
            try (PreparedStatement pstmt = conn.prepareStatement(upsertReturn)) {
                pstmt.setInt(1, transactionId);
                pstmt.setInt(2, articleId);
                pstmt.setInt(3, amountToReturn);
                pstmt.executeUpdate();
            }

            // 3. Update Stock (Items coming back to store)
            updateArticleStock(articleId, amountToReturn);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * Moves items from returns back to transaction.
     * Constraint: The final 'in_transaction' amount cannot exceed (Current Trans + Current Returns).
     */
    public void addBackToTransaction(int transactionId, int articleId, int amountToAddBack) throws SQLException {
        if (amountToAddBack <= 0) return;

        try {
            conn.setAutoCommit(false);

            // 1. Calculate the Hard Max (Trans + Returns)
            int maxPossible = getMaxAllowedAmount(transactionId, articleId);

            // 2. Check current returns to see if we have enough to move back
            int currentReturns = 0;
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT amount FROM returned_items WHERE transaction_id = ? AND article_id = ?")) {
                pstmt.setInt(1, transactionId);
                pstmt.setInt(2, articleId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) currentReturns = rs.getInt("amount");
            }

            if (amountToAddBack > currentReturns) {
                throw new SQLException("Cannot add back " + amountToAddBack + ". Only " + currentReturns + " are in the returns list.");
            }

            // 3. Decrease Returns
            if (currentReturns == amountToAddBack) {
                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM returned_items WHERE transaction_id = ? AND article_id = ?")) {
                    pstmt.setInt(1, transactionId);
                    pstmt.setInt(2, articleId);
                    pstmt.executeUpdate();
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement("UPDATE returned_items SET amount = amount - ? WHERE transaction_id = ? AND article_id = ?")) {
                    pstmt.setInt(1, amountToAddBack);
                    pstmt.setInt(2, transactionId);
                    pstmt.setInt(3, articleId);
                    pstmt.executeUpdate();
                }
            }

            // 4. Increase Transaction
            String upsertTrans = "INSERT INTO in_transaction (transaction_id, article_id, amount, price_at_sale) " +
                    "VALUES (?, ?, ?, (SELECT price FROM article WHERE article_id = ?)) " +
                    "ON CONFLICT(transaction_id, article_id) DO UPDATE SET amount = amount + EXCLUDED.amount";
            try (PreparedStatement pstmt = conn.prepareStatement(upsertTrans)) {
                pstmt.setInt(1, transactionId);
                pstmt.setInt(2, articleId);
                pstmt.setInt(3, amountToAddBack);
                pstmt.setInt(4, articleId);
                pstmt.executeUpdate();
            }

            // 5. Update Stock (Items leaving store again)
            updateArticleStock(articleId, -amountToAddBack);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private void updateArticleStock(int articleId, int change) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE article SET stock = stock + ? WHERE article_id = ?")) {
            pstmt.setInt(1, change);
            pstmt.setInt(2, articleId);
            pstmt.executeUpdate();
        }
    }

    public String getDateOfTransaction(int transactionID) throws SQLException{
        String sql = """
                select created_at from 'transaction' where transaction_id = ?
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,transactionID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                return rs.getString(1);
            }
        }
        throw new SQLException("No transaction found for this TransactionID.");
    }

    public int addToTransaction(
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

    public int removeFromTransaction(
            int transactionId,
            int articleId,
            int removeAmount
    ) throws SQLException{
        conn.setAutoCommit(false);

        try{
            try(PreparedStatement removeFromInTransaction = conn.prepareStatement(
                    "UPDATE in_transaction SET amount = amount - ? WHERE transaction_id = ? and article_id = ?"
            )){
                removeFromInTransaction.setInt(1,removeAmount);
                removeFromInTransaction.setInt(2,transactionId);
                removeFromInTransaction.setInt(3,articleId);
                removeFromInTransaction.executeUpdate();
            }

            try(PreparedStatement inTransactionAmount = conn.prepareStatement(
                    "SELECT amount FROM in_transaction WHERE transaction_id = ? and article_id = ?"
            )){
                inTransactionAmount.setInt(1,transactionId);
                inTransactionAmount.setInt(2,articleId);

                ResultSet rs = inTransactionAmount.executeQuery();
                if(rs.next()){
                    if(rs.getInt("amount")<=0){
                        throw new SQLException("This transaction does not contain this item. ");
                    }
                }
            }

            try(PreparedStatement addToStock = conn.prepareStatement(
                    "UPDATE article SET stock = stock + ? WHERE article_id = ?"
            )){
                addToStock.setInt(1,removeAmount);
                addToStock.setInt(2,articleId);
                addToStock.executeUpdate();
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

        }catch(SQLException e) {
            conn.rollback();
            throw e;
        }finally {
            conn.setAutoCommit(true);
        }
        return 0;
    }

    public void registerPayment(int amount, String paymentOption) throws SQLException {
        try(PreparedStatement addPaymentToTransaction = conn.prepareStatement(
                "UPDATE \"transaction\" SET " + paymentOption + " = " + paymentOption + " + ? where transaction_id = ?"
        )){
            addPaymentToTransaction.setInt(1,amount);
            addPaymentToTransaction.setInt(2,getLastTransactionNumber());
            addPaymentToTransaction.executeUpdate();
        }
    }

    public void registerPayment(int amount, String paymentOption, int transactionID) throws SQLException {
        try(PreparedStatement addPaymentToTransaction = conn.prepareStatement(
                "UPDATE \"transaction\" SET " + paymentOption + " = " + paymentOption + " + ? where transaction_id = ?"
        )){
            addPaymentToTransaction.setInt(1,amount);
            addPaymentToTransaction.setInt(2,transactionID);
            addPaymentToTransaction.executeUpdate();
        }
    }

    public int getArticleID(String articleName) throws SQLException {
        String sql =  "SELECT article_id FROM Article WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, articleName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("article_id");
            }
        }
        throw new SQLException("Item not found, no id could be returned. ");
    }

    public Item[] getFavoriteArticles(int cashierID) throws SQLException{
        try(PreparedStatement getFavoriteArticles = conn.prepareStatement(
                "SELECT article.name, article.price from favorite_article join article on favorite_article.article_id = article.article_id where cashier_id = ?"
        )){
            getFavoriteArticles.setInt(1,cashierID);
            ResultSet rs =  getFavoriteArticles.executeQuery();
            if(rs.next()){
                List<Item> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(new ItemCountable(
                            rs.getString("name"),
                            rs.getInt("price"),
                            0
                    ));
                }

                return items.toArray(new Item[0]);
            }
            throw new SQLException("No favorite articles for this cashierId. ");
        }
    }

    public void connectFavoriteArticle(int cashierID, int articleID) throws SQLException{
        try(PreparedStatement connectFavoriteArticle = conn.prepareStatement(
                "INSERT INTO \"favorite_article\" VALUES(?,?)"
        )){
            connectFavoriteArticle.setInt(1, cashierID);
            connectFavoriteArticle.setInt(2,articleID);
            connectFavoriteArticle.executeUpdate();
        }
    }

    public void disconnectFavoriteArticle(int cashierID, int articleID) throws SQLException{
        try(PreparedStatement connectFavoriteArticle = conn.prepareStatement(
                "DELETE FROM \"favorite_article\" WHERE cashier_id = ? AND article_id = ?;"
        )){
            connectFavoriteArticle.setInt(1, cashierID);
            connectFavoriteArticle.setInt(2,articleID);
            connectFavoriteArticle.executeUpdate();
        }
    }
}
