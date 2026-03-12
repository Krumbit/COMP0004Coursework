package uk.ac.ucl.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DataLoader {
    public static DataFrame load(String filePath) {
        DataFrame dataFrame = new DataFrame();

        try (Reader reader = new FileReader(filePath)) {
             CSVParser csvParser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .parse(reader);

            csvParser.getHeaderNames().forEach(dataFrame::addColumn);

            for (CSVRecord csvRecord : csvParser) {
                csvParser.getHeaderNames().forEach(colName -> dataFrame.addValue(colName, csvRecord.get(colName)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataFrame;
    }
}
