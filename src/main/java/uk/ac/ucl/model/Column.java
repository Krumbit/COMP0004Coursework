package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private final String name;
    private final List<String> rows = new ArrayList<>();

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
        return rows.set(row, value);
    }

    public void addRowValue(String value) {
        rows.add(value);
    }
}
