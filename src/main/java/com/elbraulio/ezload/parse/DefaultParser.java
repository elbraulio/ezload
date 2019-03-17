package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;

import java.util.List;

/**
 * Default parser for files.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class DefaultParser implements Parser {

    private final String expression;
    private final int columnsNumber;
    private final List<Column> columns;

    /**
     * Ctor
     *
     * @param expression    separation expression.
     * @param columnsNumber number of columns.
     * @param columns       columns.
     */
    public DefaultParser(
            String expression, int columnsNumber, List<Column> columns
    ) {

        this.expression = expression;
        this.columnsNumber = columnsNumber;
        this.columns = columns;
    }

    @Override
    public List<Column> columns() {
        return this.columns;
    }

    @Override
    public String separator() {
        return this.expression;
    }
}
