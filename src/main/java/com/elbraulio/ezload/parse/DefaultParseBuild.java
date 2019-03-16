package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;

import java.util.LinkedList;
import java.util.List;

/**
 * Default parser builder.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultParseBuild implements ParseBuild {
    private final String expression;
    private final int columnsNumber;
    private final List<Column> columns;

    /**
     * Ctor.
     *
     * @param expression    separator expression.
     * @param columnsNumber number of columns.
     */
    public DefaultParseBuild(String expression, int columnsNumber) {
        this(expression, columnsNumber, new LinkedList<>());
    }

    /**
     * Ctor.
     *
     * @param expression    separator expression.
     * @param columnsNumber number of columns.
     * @param columns       columns formats.
     */
    public DefaultParseBuild(
            String expression, int columnsNumber, List<Column> columns
    ) {

        this.expression = expression;
        this.columnsNumber = columnsNumber;
        this.columns = columns;
    }

    @Override
    public ParseBuild withCol(Column col) {
        this.columns.add(col);
        return this;
    }

    @Override
    public Parser parser() {
        return new DefaultParser(
                this.expression, this.columnsNumber, this.columns
        );
    }
}
