package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;

import java.io.File;
import java.util.List;

/**
 * Default parser for files.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultParser implements Parser {
    /**
     * @todo DefaultParser needs a line-reader
     * @body file, expression and columnsNumber are variables related to the
     * @body File reading. So may be the Ctor can be changed.
     */
    private final File file;
    private final String expression;
    private final int columnsNumber;
    private final List<Column> columns;

    /**
     * Ctor
     *
     * @param file          file to read.
     * @param expression    separation expression.
     * @param columnsNumber number of columns.
     * @param columns       columns.
     */
    public DefaultParser(
            File file, String expression, int columnsNumber,
            List<Column> columns
    ) {

        this.file = file;
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
