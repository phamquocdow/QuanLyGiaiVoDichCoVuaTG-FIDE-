package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Connection con;

    public DAO() {
        if(con == null){
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=Test;encrypt=true;trustServerCertificate=true";
                String user = "sa";
                String password = "12345678";

                con = DriverManager.getConnection(url, user, password);

                System.out.println("Kết nối thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
