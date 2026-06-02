
package dao;


import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Connection con;

    public DAO() {
        if(con == null){
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=Chess;encrypt=true;trustServerCertificate=true";
                String user = "sa";
                String password = "123";

                con = DriverManager.getConnection(url, user, password);

                System.out.println("Kết nối thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
