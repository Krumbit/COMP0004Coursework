package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private final String name;
    private final List<String> rows = new ArrayList<>();
    private boolean required = true;

    public Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return rows.size();
    }

    public String getRowValue(int row) {
        return rows.get(row);
    }

    public String setRowValue(int row, String value) {
        // If at least one column value is empty, we assume the whole column is optional.
        // This prevents hardcoding column required state.
        if (value.isEmpty() && required) {
            required = false;
        }
        return rows.set(row, value);
    }

    public void addRowValue(String value) {
        // If at least one column value is empty, we assume the whole column is optional.
        // This prevents hardcoding column required state.
        if (value.isEmpty() && required) {
            required = false;
        }
        rows.add(value);
    }

    public void removeRow(int row) {
        rows.remove(row);
    }

    public boolean isRequired() {
        return required;
    }
}
