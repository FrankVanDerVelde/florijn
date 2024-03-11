import java.io.*;
import java.util.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateHourRegistrationRequestBody {

    @PostMapping("/createHourRegistration")
    public String unsafeDeserialize(@RequestParam String objectData) {
        try {
            byte[] data = Base64.getDecoder().decode(objectData);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return "Deserialized object: " + o.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
