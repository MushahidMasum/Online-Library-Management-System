import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_db";

    private static final String USER = "root";

    private static final String PASSWORD =
            System.getenv("LIBRARY_DB_PASSWORD");

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            if (PASSWORD == null || PASSWORD.isEmpty()) {
                throw new RuntimeException(
                    "LIBRARY_DB_PASSWORD environment variable is not set."
                );
            }

            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println(
                "Database connected successfully!"
            );

            return con;

        } catch (Exception e) {

            System.out.println(
                "DATABASE ERROR: " + e.getMessage()
            );

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}