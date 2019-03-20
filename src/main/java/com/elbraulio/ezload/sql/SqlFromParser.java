/*
 * MIT License
 *
 * Copyright (c) 2019 Braulio López
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.elbraulio.ezload.sql;

import com.elbraulio.ezload.logger.EzLogger;
import com.elbraulio.ezload.logger.NoLog;
import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.parse.Parser;

import java.util.List;

/**
 * Build a SQL query from {@link Parser}. The column order is the same as the
 * list {@link Parser#columns()}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.1.0
 */
public final class SqlFromParser implements BuildSql {
    private final String name;
    private final Parser parser;
    private final EzLogger logger;

    /**
     * Ctor.
     *
     * @param name   table name.
     * @param parser parser with columns format.
     */
    public SqlFromParser(String name, Parser parser) {
        this(name, parser, new NoLog());
    }

    /**
     * Ctor.
     *
     * @param name   table name.
     * @param parser parser with columns format.
     * @param logger logger.
     */
    public SqlFromParser(String name, Parser parser, EzLogger logger) {

        this.name = name;
        this.parser = parser;
        this.logger = logger;
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
        this.logger.info(
                "query generated: " + query.toString(), "SqlFromParser"
        );
        return query.toString();
    }
}
