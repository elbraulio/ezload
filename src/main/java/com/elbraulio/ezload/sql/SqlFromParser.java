package com.elbraulio.ezload.sql;

import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.parse.Parser;

import java.util.List;

/**
 * Build a SQL query from {@link Parser}. The column order is the same as the
 * list {@link Parser#columns()}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SqlFromParser implements BuildSql {
    private final String name;
    private final Parser parser;

    /**
     * Ctor.
     *
     * @param name   table name.
     * @param parser parser with columns format.
     */
    public SqlFromParser(String name, Parser parser) {

        this.name = name;
        this.parser = parser;
    }

    @Override
    public String sql() {
        // start with 'INSERT INTO {table name}'
        final StringBuilder query = new StringBuilder(
                "INSERT INTO " + this.name + " ("
        );
        final List<Column> columns = this.parser.columns();
        // first column name if exists
        if (!columns.isEmpty()) {
            query.append(columns.get(0).name());
        }
        // rest of columns separated by ','
        for (int i = 1; i < columns.size(); i++) {
            query.append(",");
            query.append(columns.get(i).name());
        }
        // then ') VALUES ('
        query.append(") VALUES (");
        // a '?' character for first column if exists
        if (!columns.isEmpty()) {
            query.append("?");
        }
        // a '?' character for remaining column
        for (int i = 1; i < columns.size(); i++) {
            query.append(",?");
        }
        // end with ');'
        query.append(");");
        return query.toString();
    }
}
