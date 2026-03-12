package uk.ac.ucl.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataFrame {
    // Column name -> column for O(1) lookup
    // LinkedHashMap instead of HashMap to preserve insertion order
    private final Map<String, Column> columns = new LinkedHashMap<>();

    public void addColumn(String name) {
        Column column = new Column(name);
        columns.put(name, column);
    }

    public List<String> getColumnNames() {
        return columns.keySet().stream().toList();
    }

    public int getRowCount() {
        if (columns.isEmpty()) {
            return 0;
        }

        return columns.values().iterator().next().getSize();
    }

    public String getValue(String colName, int row) {
        return columns.get(colName).getRowValue(row);
    }

    public String putValue(String colName, int row, String value) {
        return columns.get(colName).setRowValue(row, value);
    }

    public void addValue(String colName, String value) {
        columns.get(colName).addRowValue(value);
    }

    public boolean isColumnRequired(String colName) {
        return columns.get(colName).isRequired();
    }
}
