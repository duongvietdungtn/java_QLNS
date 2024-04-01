/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ConnectDB {
  public static Connection getConnection() {
        Connection result = null;
        try {           
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mySQL://localhost:3306/qlnv";
            String userName = "root";
            String password = "";
            result = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu !", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
