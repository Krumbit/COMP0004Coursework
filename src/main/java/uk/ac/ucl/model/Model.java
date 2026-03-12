package uk.ac.ucl.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Model
{
    private static Model instance;
    private DataFrame dataFrame;
    public static final List<String> VISIBLE_COLUMNS = List.of(
            "FIRST", "LAST", "GENDER", "BIRTHDATE", "CITY", "STATE"
    );
    private static final List<String> QUERYABLE_COLUMNS = List.of(
            "ID", "FIRST", "LAST", "SSN", "DRIVERS", "PASSPORT", "ADDRESS", "CITY", "STATE", "ZIP"
    );

    private Model() {}

    public void loadData(String filePath) {
        if (dataFrame == null) {
            dataFrame = DataLoader.load(filePath);
        }
    }

    public DataFrame getDataFrame() {
        return dataFrame;
    }

    /**
     *
     * @param query The query to search for
     * @return A (possibly empty) list of rows where the query was found
     */
    private List<Integer> searchFor(String query) {
        if (query.isEmpty()) {
            return getAllRows();
        }

        String lowerQuery = query.toLowerCase(Locale.ROOT);
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            for (String col : QUERYABLE_COLUMNS) {
                if (dataFrame.getValue(col, i).toLowerCase(Locale.ROOT).contains(lowerQuery)) {
                    rows.add(i);
                    break;
                }
            }
        }

        return rows;
    }

    /**
     *
     * @param id The patient ID
     * @return An optional integer representing the row of the patient
     */
    public Optional<Integer> searchById(String id) {
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            if (dataFrame.getValue("ID", i).equals(id)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public List<Integer> getRows(String query, String sortCol, String sortDir) {
        List<Integer> visibleRows = searchFor(query);

        if (
                sortCol != null
                && !sortCol.trim().isEmpty()
                && dataFrame.getColumnNames().contains(sortCol.toUpperCase(Locale.ROOT))
        ) {
            Comparator<Integer> comparator = Comparator.comparing(row ->
                    dataFrame.getValue(sortCol, row)
            );

            if (sortDir.equals("desc")) {
                comparator = comparator.reversed();
            }

            visibleRows.sort(comparator);
        }

        return visibleRows;
    }

    private List<Integer> getAllRows() {
        return IntStream.range(0, dataFrame.getRowCount()).boxed().collect(Collectors.toList());
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }

        return instance;
    }
}
