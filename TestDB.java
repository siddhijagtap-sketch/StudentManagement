import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC",
                "root",  // your MySQL username
                "1234"       // your MySQL password (blank if none)
            );

            System.out.println("DATABASE CONNECTED SUCCESSFULLY");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
