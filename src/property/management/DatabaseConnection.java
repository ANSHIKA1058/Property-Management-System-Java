package property.management;
import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {

    static final String url="jdbc:mysql://localhost:3306/PropertyManagement";
    static final String user="java";
    static final String pass="java123";
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Database connection error ❌");
            e.printStackTrace();
            return null;
        }
    }




}
