import com.fasterxml.jackson.databind.ObjectMapper;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fixes for insecure deserialization vulnerability and Cross-Site Scripting (XSS) vulnerability:
 * 
 * Issue 1: Insecure Deserialization
 * - The original code used Java's native deserialization, which is vulnerable to attacks if untrusted data is deserialized.
 *   Deserializing objects from untrusted sources can lead to remote code execution among other attacks.
 * 
 * Solution 1:
 * 1. Use a safer serialization method like JSON. This involves using a library like Jackson for deserialization, which is less prone to exploitation.
 * 2. Only allow deserialization of known-safe classes. Implement a whitelist approach if you absolutely must use Java's native deserialization.
 * 
 * Issue 2: Cross-Site Scripting (XSS)
 * - Unsanitized input from an HTTP parameter is reflected back in the response. This can lead to XSS if special HTML characters in the input are not properly escaped.
 * 
 * Solution 2:
 * 1. Avoid directly appending user-controlled data into the response. Instead, use safer methods to process or sanitize the data before inclusion.
 * 2. When returning data that includes user-controlled input, ensure any potentially harmful characters are properly escaped to prevent script injection.
 */
@RestController
public class CreateHourRegistrationRequestBody {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/createHourRegistration")
    public String createHourRegistration(@RequestBody String objectData) {
        try {
            HourRegistration registration = objectMapper.readValue(objectData, HourRegistration.class);
            String safeName = escapeHtml(registration.getName()); // Example for a getName() method
            return "Received registration for: " + safeName;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String escapeHtml(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;")
                    .replace("/", "&#x2F;");
    }
}