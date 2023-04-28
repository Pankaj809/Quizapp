package project_demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class dbConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "Bikash@2000");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
