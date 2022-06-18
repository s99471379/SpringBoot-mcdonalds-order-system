package mis.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mis.models.Product;

public class ProductDAO {

    //private Connection conn = DBConnection.getConnection();
    private Connection conn;
    
    
    public List<Product> getAllProducts() {
        
        conn = DBConnection.getConnection();
        String query = "select * from product";
        List<Product> product_list = new ArrayList();

        try {
            PreparedStatement ps
                    = conn.prepareStatement(query);
            ResultSet rset = ps.executeQuery();

            while (rset.next()) {
                Product product = new Product();
                product.setProduct_id(rset.getString("product_id"));
                product.setCategory(rset.getString("category"));
                product.setName(rset.getString("name"));
                product.setPrice(rset.getInt("price"));
                product.setPhoto(rset.getString("photo"));
                product.setDescription(rset.getString("description"));
                product_list.add(product);
                
                //不要斷線，一直會用到，使用持續連線的方式
               //conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("getAllproducts異常:" + ex.toString());
        }

        return product_list;
    }

 
    //選擇特定字串"孫大毛"或是"孫%"或是"%毛"
    public List<Product> findByNameContaining(String name_str) {
        conn = DBConnection.getConnection();
        boolean success = false;
        List<Product> product_list = new ArrayList();
        //String query = String.format("select * from student where name like '%s%s'", name_str, "%");
        //String query = String.format("select * from student where name like '%s'", name_str);
        String query = "select * from product where name like ?";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, "%"+name_str+"%");
            ResultSet rset = state.executeQuery();
            while (rset.next()) {
                Product product = new Product();
                product.setProduct_id(rset.getString("product_id"));
                product.setName(rset.getString("name"));
                product.setPrice(rset.getInt("price"));
                product.setPhoto(rset.getString("photo"));
                product_list.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByName操作異常:" + ex.toString());
        }
        return product_list;
    }

   public List<Product> findByPriceLessThanEqual(int price) {
        conn = DBConnection.getConnection();
        List<Product> product_list = new ArrayList();
        String query = "select * from product where price <= ?";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setInt(1, price);
            ResultSet rset = state.executeQuery();
            while (rset.next()) {
                Product product = new Product();
                product.setProduct_id(rset.getString("product_id"));
                product.setCategory(rset.getString("category"));
                product.setName(rset.getString("name"));
                product.setPrice(rset.getInt("price"));
                product.setPhoto(rset.getString("photo"));
                product.setDescription(rset.getString("description"));
                product_list.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByPrice作異常:" + ex.toString());
        }
        return product_list;
    }
    
  
   public List<Product> findByCate(String cate) {
        conn = DBConnection.getConnection();
        List<Product> product_list = new ArrayList();
        String query = "select * from product where category = ?";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, cate);
            ResultSet rset = state.executeQuery();
            while (rset.next()) {
                Product product = new Product();
                product.setProduct_id(rset.getString("product_id"));
                product.setCategory(rset.getString("category"));
                product.setName(rset.getString("name"));
                product.setPrice(rset.getInt("price"));
                product.setPhoto(rset.getString("photo"));
                product.setDescription(rset.getString("description"));
                product_list.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByCate異常:" + ex.toString());
        }
        return product_list;
    }
    
    public Product findById(String id) {
        conn = DBConnection.getConnection();
        boolean success = false;
        String query = "select * from product where product_id = ?";
        Product product = new Product();
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, id);
            ResultSet rset = state.executeQuery();

            while (rset.next()) {
                success = true;
                product.setProduct_id(rset.getString("product_id"));
                product.setCategory(rset.getString("category"));
                product.setName(rset.getString("name"));
                product.setPrice(rset.getInt("price"));
                product.setPhoto(rset.getString("photo"));
                product.setDescription(rset.getString("description"));
            }
        } catch (SQLException ex) {
            System.out.println("資料庫selectByID操作異常:" + ex.toString());
        }

        if (success) {
            return product;
        } else {
            return null;
        }

    }
  
       public boolean insert(Product product) {
        conn = DBConnection.getConnection();
        String query = "insert into product(product_id,name,category,price,photo,description) VALUES (?,?,?,?,?,?)";
        boolean success = false;
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(1, product.getProduct_id());
            state.setString(2, product.getName());
            state.setString(3, product.getCategory());
            state.setInt(4, product.getPrice());
            state.setString(5, product.getPhoto());
            state.setString(6, product.getDescription());

            state.execute();
            //state.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            System.out.println("insert異常:" + ex.toString());
        }
        return success;
    } 
    
       
    public boolean delete(String id) {
        conn = DBConnection.getConnection();
        String query = "delete from product where product_id =?";
        boolean sucess = false;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id);
            //statement.execute();
            sucess = statement.executeUpdate() > 0;
            if (sucess) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (SQLException ex) {
            System.out.println("delete異常:\n" + ex.toString());
        }
        return sucess;
    }

    public void update(Product product) {
        conn = DBConnection.getConnection();
        String query = "update product set name=?, category=?, price=?, photo= ?, description=? where product_id = ?";
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setString(6, product.getProduct_id());
            state.setString(1, product.getName());
            state.setString(2, product.getCategory());
            state.setInt(3, product.getPrice());
            state.setString(4, product.getPhoto());
            state.setString(5, product.getDescription());
            
            state.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("update異常:" + ex.toString());
        }
    }
    
    public static void main(String[] args) {

        ProductDAO dao = new ProductDAO();

        // select All
        List<Product> product_list = dao.getAllProducts();
        for (Product product : product_list) {
            System.out.println(product);
        }

    }
}

