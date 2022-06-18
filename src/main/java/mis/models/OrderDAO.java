package mis.models;

import mis.models.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mis.models.Order;

public class OrderDAO {

    private Connection conn;

    public String getMaxOrderNum() {
        conn = DBConnection.getConnection();
        String maxVal = null;

        String query = "SELECT Max(order_num) as `max_id` FROM `sale_order`";
        //String query = "SELECT Max(order_num) as `max_id` FROM `sale_order` LIMIT 1";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            ResultSet rset = state.executeQuery();
            while (rset.next()) {
                maxVal = rset.getString("max_id");
            }
        } catch (SQLException ex) {
            System.out.println("資料庫getMaxOrderNum操作異常:" + ex.toString());
        }
        return maxVal;
    }

    public boolean insertCart(Order cart) {
        //String order_num =  getMaxOrderNum();
        conn = DBConnection.getConnection();
        String query = "insert into `sale_order`(order_num,total_price,"
                + "customer_name,customer_phone, customer_address) "
                + "VALUES (?, ?, ?, ?, ?)";
        boolean success = false;
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, cart.getOrder_num());
            state.setInt(2, cart.getTotal_price());
            state.setString(3, cart.getCustomer_name());
            state.setString(4, cart.getCustomer_phtone());
            state.setString(5, cart.getCustomer_address());
            state.execute();
            success = true;
        } catch (SQLException ex) {
            System.out.println("insert異常:" + ex.toString());
        }
        return success;
    }

       public boolean insertCartItem(OrderDetail item) {
        //String order_num =  getMaxOrderNum();
        conn = DBConnection.getConnection();
        
        String query = "INSERT INTO `order_detail` (`order_num`, `product_id`, `quantity`, product_price, product_name) VALUES (?, ?, ?, ?, ?)";
        //String query = "INSERT INTO `order_detail` (`order_num`, `product_id`, `quantity`) VALUES (?, ?, ?)";
        boolean success = false;
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, item.getOrder_num());
            state.setString(2, item.getProduct_id());
            state.setInt(3, item.getQuantity());
            state.setInt(4, item.getProduct_price()); //optional
            state.setString(5, item.getProduct_name());//optional
            state.execute();
            success = true;
        } catch (SQLException ex) {
            System.out.println("insert異常:" + ex.toString());
        }
        return success;
    }
    
    public static void main(String[] args) {

        OrderDAO orddao = new OrderDAO();
        System.out.println(orddao.getMaxOrderNum());
        
        String ordNum = "ord102";
        
        Order cart = new Order(ordNum, "2021-05-01", 123, "李大同");
        orddao.insertCart(cart);
        
        OrderDetail crti = new OrderDetail();
        crti.setOrder_num(ordNum);
        crti.setQuantity(2);
        crti.setProduct_id("p-j-103");
        
        orddao.insertCartItem(crti);
    }

}

