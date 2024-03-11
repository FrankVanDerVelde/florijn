import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fixes for hardcoding sensitive information:
 * 
 * Issue: Hardcoding Sensitive Information
 * - The original code hardcoded sensitive database credentials directly in the source code. 
 *   This practice is insecure as it exposes sensitive information to anyone who has access to the source code.
 * 
 * Solution:
 * 1. Externalize configuration details into a properties file or environment variables. 
 *    This approach allows you to keep sensitive information out of the source code and makes it easier to update them without changing the code.
 * 2. Use a properties file or environment variables to store database credentials and read them at runtime.
 */
public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", System.getenv("DATABASE_USER"));
        properties.setProperty("password", System.getenv("DATABASE_PASSWORD"));

        String databaseUrl = System.getenv("DATABASE_URL");

        return DriverManager.getConnection(databaseUrl, properties);
    }
}
