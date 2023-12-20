import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

public class CSVDataReader {

    private CSVReader reader;

    public CSVDataReader(String filePath) throws IOException {
        this.reader = new CSVReader(new FileReader(filePath));
    }

    public String[] readNext() throws IOException, CsvValidationException {
        return reader.readNext();
    }

    public void close() throws IOException {
        reader.close();
    }
}