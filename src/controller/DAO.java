package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    protected static Connection con;

    public DAO() {
        if (con == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=chess;encrypt=true;trustServerCertificate=true";
                String user = "sa";
                String password = "1234";

                con = DriverManager.getConnection(url, user, password);

                System.out.println("Kết nối thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}