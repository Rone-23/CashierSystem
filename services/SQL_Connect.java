package services;



import assets.Constants;

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

    /*
    ITEMS
     */

    public void removeFromStock(double quantityToSubtract, String name) throws SQLException{
        String sql = "UPDATE Article SET stock = stock - ? WHERE article_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, quantityToSubtract);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        }
    }

    public Item[] getAllItems(int currentCashierId) throws SQLException {
        List<Item> items = new ArrayList<>();

        String sql = """
            SELECT 
                a.name, 
                a.price, 
                t.type_name as category, 
                s.subtype_name as subcategory,
                CASE WHEN fa.article_id IS NOT NULL THEN 1 ELSE 0 END as is_favorite,
                MAX(CASE WHEN d.requires_customer_card = 0 THEN d.discount_percent ELSE 0 END) as general_discount,
                MAX(CASE WHEN d.requires_customer_card = 1 THEN d.discount_percent ELSE 0 END) as customer_discount
            FROM article a
            JOIN subtype s ON a.subtype_id = s.subtype_id
            JOIN type t ON s.parent_type_id = t.type_id
            LEFT JOIN favorite_article fa ON a.article_id = fa.article_id AND fa.cashier_id = ?
            LEFT JOIN discount d ON a.article_id = d.article_id AND (d.valid_to IS NULL OR d.valid_to > CURRENT_TIMESTAMP)
            GROUP BY a.article_id
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentCashierId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int basePrice = rs.getInt("price");

                Item item = new ItemCountable(name, basePrice, 0);
                item.setCategory(rs.getString("category"));
                item.setSubcategory(rs.getString("subcategory"));
                item.setIsFavorite(rs.getInt("is_favorite") == 1);

                int genDiscount = rs.getInt("general_discount");
                int custDiscount = rs.getInt("customer_discount");

                if (custDiscount > 0 && custDiscount > genDiscount) {
                    item.setDiscountType(Constants.CUSTOMER);
                    item.setDiscountPrice(basePrice * (100 - custDiscount) / 100);
                }
                else if (genDiscount > 0) {
                    item.setDiscountType(Constants.GENERAL);
                    item.setDiscountPrice(basePrice * (100 - genDiscount) / 100);
                }
                else {
                    item.setDiscountType(Constants.NONE);
                    item.setDiscountPrice(basePrice);
                }

                items.add(item);
            }
        }
         return items.toArray(new Item[0]);
    }


    public Item[] getItemsBySubCategory(String type) throws SQLException {
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
                WHERE s.subtype_name = ?;
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

    /*
    TRANSACTION
     */

    public int createTransaction(int cashierID) throws SQLException {
        String sqlInsert =  "insert into \"transaction\" (cashier_id, created_at)" +
                "values (?,current_timestamp)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setInt(1, cashierID);
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

    public void setCustomerId(int transactionID,int cashierID) throws SQLException {

        try (PreparedStatement pstmt = conn.prepareStatement("""
                UPDATE 'Transaction' SET customer_id = ? WHERE transaction_id = ?
                """)) {
            pstmt.setInt(1, cashierID);
            pstmt.setInt(2, transactionID);
            pstmt.executeUpdate();
        }
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

    /*
    RETURN TRANSACTION
     */

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

    public void returnItem(int transactionId, int articleId, int amountToReturn) throws SQLException {
        if (amountToReturn <= 0) return;

        try {
            conn.setAutoCommit(false);

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

            String upsertReturn = "INSERT INTO returned_items (transaction_id, article_id, amount) VALUES (?, ?, ?) " +
                    "ON CONFLICT(transaction_id, article_id) DO UPDATE SET amount = amount + EXCLUDED.amount";
            try (PreparedStatement pstmt = conn.prepareStatement(upsertReturn)) {
                pstmt.setInt(1, transactionId);
                pstmt.setInt(2, articleId);
                pstmt.setInt(3, amountToReturn);
                pstmt.executeUpdate();
            }

            updateArticleStock(articleId, amountToReturn);

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

    public int getReturnedAmount(int article_id, int transaction_id) throws SQLException{
        String sql =  "SELECT amount FROM returned_items WHERE article_id = ? and transaction_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, article_id);
            pstmt.setInt(2, transaction_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        }
        return 0;
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

    /*
    UTILITY METHODS
     */

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

    public String getPathToImage(int articleID) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("""
                SELECT path_to_image FROM Article where article_id = ?
                """)) {
            pstmt.setInt(1, articleID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getString(1);
            }
        }
        return null;
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

    public int getStock(int articleID) throws SQLException{
        try (PreparedStatement stockStmt = conn.prepareStatement(
                "SELECT stock FROM article WHERE article_id = ?"
        )) {
            stockStmt.setInt(1, articleID);
            ResultSet rs = stockStmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Article not found");
            }else{
                return rs.getInt(1);
            }
        }
    }

    /*
    FAVORITE ARTICLES
     */

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

    /*
    CUSTOMER CARDS
     */

    public int getCustomerIdByCard(int cardNumber) throws SQLException {
        String sql = "SELECT customer_id FROM customer WHERE customer_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id");
            }
        }
        return -1;
    }

    public void createNewCustomerCard(int cardNumber, String customerName) throws SQLException {
        if (getCustomerIdByCard(cardNumber) != -1) {
            throw new SQLException("Karta s číslom " + cardNumber + " už existuje v systéme!");
        }

        String sql = "INSERT INTO customer (name, customer_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customerName);
            pstmt.setInt(2, cardNumber);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                rs.getInt(1);
                return;
            }
        }
        throw new SQLException("Nepodarilo sa vytvoriť zákaznícku kartu.");
    }

    //USERS
    public boolean validateCashier(int cashierId) throws SQLException {
        String sql = "SELECT cashier_id FROM cashier WHERE cashier_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cashierId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    /*
    VOUCHERS
     */
    public boolean checkVoucherExists(int voucherId) throws SQLException {
        String sql = "SELECT voucher_id FROM voucher WHERE voucher_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, voucherId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public void activateVoucher(int voucherId) throws SQLException {
        String checkSql = "SELECT is_active, balance FROM voucher WHERE voucher_id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, voucherId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Poukážka s číslom " + voucherId + " neexistuje v systéme!");
            }

            int isActive = rs.getInt("is_active");
            if (isActive == 1) {
                throw new SQLException("Poukážka " + voucherId + " je už aktívna!");
            }
        }

        String updateSql = "UPDATE voucher SET is_active = 1 WHERE voucher_id = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setInt(1, voucherId);
            updateStmt.executeUpdate();
        }
    }

    public int getVoucherBalance(int voucherId) throws SQLException {
        String sql = "SELECT balance, is_active FROM voucher WHERE voucher_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, voucherId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("is_active") == 0) {
                    throw new SQLException("Poukážka je neaktívna alebo vyčerpaná!");
                }
                return rs.getInt("balance"); // Returns balance in cents
            }
            throw new SQLException("Poukážka " + voucherId + " neexistuje!");
        }
    }

    public int deductVoucherBalance(int voucherId, int amountToDeductInCents) throws SQLException {
        int currentBalance = getVoucherBalance(voucherId);
        if (amountToDeductInCents > currentBalance) {
            throw new SQLException("Nedostatok prostriedkov! Zostatok je len " + (currentBalance / 100.0) + " EUR");
        }

        int newBalance = currentBalance - amountToDeductInCents;
        int newActiveState = (newBalance > 0) ? 1 : 0;

        String updateSql = "UPDATE voucher SET balance = ?, is_active = ? WHERE voucher_id = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setInt(1, newBalance);
            updateStmt.setInt(2, newActiveState);
            updateStmt.setInt(3, voucherId);
            updateStmt.executeUpdate();
        }

        return newBalance;
    }


}
