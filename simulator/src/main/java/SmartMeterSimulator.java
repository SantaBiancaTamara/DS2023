import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class SmartMeterSimulator {

    public static void main(String[] args) {
        try {
            String filePath = "C:\\UTCN4\\SEM1\\DS\\project\\sensor.csv";

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the device ID: ");
            String deviceID = scanner.nextLine();

            CSVReader reader = new CSVReader(new FileReader(filePath));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Print the contents of each line for debugging
                //System.out.println(Arrays.toString(nextLine));

                String timestamp = String.valueOf(System.currentTimeMillis());
                String jsonData = JSONGenerator.generateJSON(timestamp, deviceID, nextLine[0]);

                QueueSender.sendDataToQueue(jsonData);

                TimeUnit.SECONDS.sleep(10); // Wait for 10 s (simulating the interval)
            }

            reader.close(); // Close the CSVReader after reading
        } catch (IOException | CsvValidationException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}