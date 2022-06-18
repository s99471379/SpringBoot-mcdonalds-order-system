package mis.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn;

    private static final String URL = "jdbc:mariadb://localhost:3306/db_mcdpos";
    private static final String USER = "mis";
    private static final String PWD = "mis123";

    public static Connection getConnection() {

        try {
            if (conn != null && !conn.isClosed()) {
                System.out.println("取得已連線靜態物件connection");
                return conn;
            } else {
                conn = DriverManager.getConnection(URL, USER, PWD);
                System.out.println("使用帳號與密碼連線到資料庫...");
            }
        } catch (SQLException ex) {
            System.out.println("連線錯誤!");
            System.out.println(ex.toString());
            //ex.printStackTrace(); 
        }
        return conn;
    }
}
