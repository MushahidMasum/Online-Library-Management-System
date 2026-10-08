import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_db";

    private static final String USER = "root";

    private static final String PASSWORD = "Sahil786";

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database connected successfully!");

            return con;

        } catch (Exception e) {
            System.out.println("DATABASE ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

    

