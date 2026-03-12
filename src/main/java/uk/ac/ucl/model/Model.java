package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Model
{
    private static Model instance;
    private DataFrame dataFrame;
    public final static List<String> VISIBLE_COLUMNS = List.of(
            "FIRST", "LAST", "GENDER", "BIRTHDATE", "CITY", "STATE"
    );
    private final static List<String> QUERYABLE_COLUMNS = List.of(
            "ID", "FIRST", "LAST", "SSN", "DRIVERS", "PASSPORT", "ADDRESS", "CITY", "STATE", "ZIP"
    );

    private Model() {}

    public void loadData(String filePath) {
        dataFrame = DataLoader.load(filePath);
    }

    public DataFrame getDataFrame() {
        return dataFrame;
    }

    /**
     *
     * @param query The query to search for
     * @return A (possibly empty) list of rows where the query was found
     */
    public List<Integer> searchFor(String query) {
        List<Integer> rows = new ArrayList<>();

        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            for (String col : QUERYABLE_COLUMNS) {
                if (dataFrame.getValue(col, i).toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                    rows.add(i);
                }
            }
        }

        return rows;
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }

        return instance;
    }
}
