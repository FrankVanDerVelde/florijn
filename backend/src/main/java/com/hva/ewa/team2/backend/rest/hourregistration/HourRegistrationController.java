import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@RestController
public class HourRegistrationController {

    @GetMapping("/hourRegistration")
    public String unsafeSql(@RequestParam String userId) {
        String response = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:yourdriver:yourdatabase", "username", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id = " + userId); // Unsafe
            while (resultSet.next()) {
                response += resultSet.getString("username");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            response = "Error: " + e.getMessage();
        }
        return response;
    }
}