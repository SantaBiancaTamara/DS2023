import java.util.Scanner;

public class JSONGenerator {

    public static String generateJSON(String timestamp, String deviceId, String measurementValue) {
        return "{"
                + "\"timestamp\": " + timestamp + ","
                + "\"deviceId\": \"" + deviceId + "\","
                + "\"measurementValue\": " + measurementValue
                + "}";
    }
}
