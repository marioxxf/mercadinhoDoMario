package mercadinhoprojectSrc;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;
import forms.mainFrame;
import java.awt.List;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mario de Sousa Jr.
 */
public class bdManager {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public void connect(){
        String server = "jdbc:mysql://localhost:3306/gioias";
        String user = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        
        try{
            Class.forName(driver);
            this.connection = DriverManager.getConnection(server, user, password);
            this.statement = this.connection.createStatement();
        } catch(Exception error){
            System.out.println("Error: " + error.getMessage());
        }
    }
    
    public Vector getProductByName(String stringSearched){
        String server = "jdbc:mysql://localhost:3306/gioias";
        String user = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        Vector product = new Vector();
        try{
            Class.forName(driver);
            Connection con = DriverManager.getConnection(server, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Product WHERE Name LIKE '%" + stringSearched + "%' LIMIT 1");
            while(rs.next()){
                product.add(rs.getString("ProductId"));
                product.add(rs.getString("Code"));
                product.add(rs.getString("Status"));
                product.add(rs.getString("Name"));
                product.add(rs.getString("Description"));
                product.add(rs.getString("StockQtd"));
                product.add(rs.getString("MinStockQtd"));
                product.add(rs.getString("MaxStockQtd"));
                product.add(rs.getString("BuyAmount"));
                product.add(rs.getString("SaleAmount"));
                product.add(rs.getString("EanCode"));
                product.add(rs.getString("NcmCode"));
                product.add(rs.getString("ProfitPercentage"));
                product.add(rs.getString("RegisterDate"));
                product.add(rs.getString("ProductImgPath"));
            }
            return product;
        } catch(Exception error) {
            System.out.println("Erro: " + error);
            return product;
        }
    }
    
    public ArrayList getAllProducts(){
        String server = "jdbc:mysql://localhost:3306/gioias";
        String user = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        ArrayList<Vector> products = new ArrayList<Vector>();
        try{
            Class.forName(driver);
            Connection con = DriverManager.getConnection(server, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Product");
            while(rs.next()){
                Vector product = new Vector();
                product.add(rs.getString("ProductId"));
                product.add(rs.getString("Code"));
                product.add(rs.getString("Status"));
                product.add(rs.getString("Name"));
                product.add(rs.getString("Description"));
                product.add(rs.getString("StockQtd"));
                product.add(rs.getString("MinStockQtd"));
                product.add(rs.getString("MaxStockQtd"));
                product.add(rs.getString("BuyAmount"));
                product.add(rs.getString("SaleAmount"));
                product.add(rs.getString("EanCode"));
                product.add(rs.getString("NcmCode"));
                product.add(rs.getString("ProfitPercentage"));
                product.add(rs.getString("RegisterDate"));
                product.add(rs.getString("ProductImgPath"));
                products.add(product);
            }
            return products;
        } catch(Exception error) {
            System.out.println("Erro: " + error);
            return products;
        }
    }
    
    public DefaultTableModel getAllContactsForMainTable(){
        String server = "jdbc:mysql://localhost:3306/gioias";
        String user = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        mainFrame mainFrameInstance = new mainFrame();
        DefaultTableModel tableModel = (DefaultTableModel) mainFrameInstance.productsTable.getModel();
        
        try{
            Class.forName(driver);
            Connection con = DriverManager.getConnection(server, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Product");
            Vector v = new Vector();
            
            while(rs.next()){
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime dateTimeFormat = LocalDateTime.parse(rs.getString("RegisterDate"), inputFormat);
                String registerDateFormated = dateTimeFormat.format(outputFormat);
                v.add(rs.getString("ProductId"));
                v.add(rs.getString("Name"));
                v.add(rs.getString("StockQtd"));
                v.add(rs.getString("BuyAmount"));
                v.add(rs.getString("SaleAmount"));
                v.add(registerDateFormated);
                tableModel.addRow(v);
                v = new Vector();
            }
            return tableModel;
        } catch(Exception error) {
            System.out.println("Erro: " + error);
            return tableModel;
        }
    }
    
    public void disconnect(){
        try{
            this.connection.close();
        } catch(Exception error){
            System.out.println("Error: " + error.getMessage());
        }
    }
    
    public int deleteProduct(int productId){
        try{
            connect();
            String query = "DELETE FROM Product WHERE Productid = " + productId;
            this.statement.execute(query);
            disconnect();
            return 1;
        } catch(Exception error){
            System.out.println("Error: " + error.getMessage());
            disconnect();
            return 0;
        }
    }
    
    public int dynamicEditContact(String ContactId, String Name, String Number, String Email){
        try{
            connect();
            String query = "UPDATE Contact SET Name = '" + Name + "', Number = '" + Number + "', Email = '" + Email + "' WHERE RegisterId = '" + ContactId + "'";
            this.statement.execute(query);
            disconnect();
            return 1;
        } catch(Exception error){
            System.out.println("Error: " + error.getMessage());
            disconnect();
            return 0;
        }
    }
    
    public int dynamicEditProduct(int productId, String statusCode, String name, String description, int stockQtd, int minStockQtd, int maxStockQtd, float buyAmount, float saleAmount, BigInteger eanCode, int ncmCode, float profitPercentage){
        try{
            connect();
            String query = "UPDATE Product set Status = '" + statusCode + "', Name = '" + name + "', Description = '" + description + "', StockQtd = '" + stockQtd + "', MinStockQtd = '" + minStockQtd + "', MaxStockQtd = '" + maxStockQtd + "', BuyAmount = '" + buyAmount + "', SaleAmount = '" + saleAmount + "', EanCode = '" + eanCode + "', NcmCode = '" + ncmCode + "', ProfitPercentage = '" + profitPercentage + "' WHERE ProductId = " + productId;
            this.statement.execute(query);
            disconnect();
            return 1;
        } catch(Exception error){
            System.out.println("Error: " + error.getMessage());
            disconnect();
            return 0;
        }
    }
    
    public int dynamicAddProduct(String code, String statusCode, String name, String description, int stockQtd, int minStockQtd, int maxStockQtd, float buyAmount, float saleAmount, BigInteger eanCode, int ncmCode, float profitPercentage, String productImgPath){
        try{
            connect();
            String query = "INSERT INTO Product (Code, Status, Name, Description, StockQtd, MinStockQtd, MaxStockQtd, BuyAmount, SaleAmount, EanCode, NcmCode, ProfitPercentage, RegisterDate, ProductImgPath) VALUES ('" + code + "', '" + statusCode + "', '" + name + "', '" + description + "', " + stockQtd + ", " + minStockQtd + ", " + maxStockQtd + ", " + buyAmount + ", " + saleAmount + ", " + eanCode + ", " + ncmCode + ", " + profitPercentage + ", CURRENT_TIMESTAMP, '" + productImgPath + "')";                                                                                                                                                                                              
            this.statement.execute(query);
            disconnect();
            return 1;
        } catch(Exception error){
            System.out.println("Error: " + error.getMessage());
            disconnect();
            return 0;
        }
    }
}