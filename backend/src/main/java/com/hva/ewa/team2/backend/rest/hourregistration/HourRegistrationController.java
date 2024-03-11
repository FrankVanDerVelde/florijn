import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

/**
 * Fixes for SQL Injection vulnerability:
 * 
 * Issue: SQL Injection
 * - The original code constructs a SQL query by directly concatenating user input into the query string.
 *   This practice is unsafe because it allows for SQL Injection attacks, where an attacker could manipulate the SQL query
 *   to perform unauthorized actions on the database.
 * 
 * Solution:
 * 1. Use PreparedStatement instead of Statement for executing SQL queries. This allows you to safely include user input in your SQL queries
 *    by using parameterized queries, which prevents attackers from injecting malicious SQL.
 */
@RestController
public class HourRegistrationController {

    @GetMapping("/hourRegistration")
    public String safeSql(@RequestParam String userId) {
        String response = "";
        String connectionString = "jdbc:yourdriver:yourdatabase"; 
        try (Connection connection = DriverManager.getConnection(connectionString, "username", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
             
            preparedStatement.setString(1, userId); // Safely set the user ID parameter
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    response += resultSet.getString("username");
                }
            }
        } catch (Exception e) {
            response = "Error: " + e.getMessage();
        }
        return response;
    }
}
