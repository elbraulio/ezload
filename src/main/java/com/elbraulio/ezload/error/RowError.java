package com.elbraulio.ezload.error;

import java.util.List;

public class RowError implements EzError {
    private final int row;
    private final List<String> errors;

    public RowError(int row, List<String> errors) {
        this.row = row;
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "error at row " + this.row + ": " + this.errors;
    }
}
