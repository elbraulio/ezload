/*
 * MIT License
 *
 * Copyright (c) 2019 - 2020 Braulio López
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

import com.elbraulio.ezload.action.AddPreparedStatement;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.exception.EzParseException;
import com.elbraulio.ezload.line.Line;
import com.elbraulio.ezload.parse.Parser;
import com.elbraulio.ezload.value.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * This implementation build a SQL query from a {@link Parser}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.1.0
 */
public final class InsertFromParser implements Insert {

    private final Parser parser;
    private final BuildSql buildSql;
    private final int chunkSize;

    /**
     * Ctor.
     *
     * @param parser    table name.
     * @param buildSql  sql builder.
     * @param chunkSize size to split the batch.
     */
    public InsertFromParser(Parser parser, BuildSql buildSql, int chunkSize) {

        this.parser = parser;
        this.buildSql = buildSql;
        this.chunkSize = chunkSize;
    }

    @Override
    public long execute(Connection connection, Stream<String> lines)
            throws EzException {
        final Iterator<String> iterableLines = lines.iterator();
        int count = 0;
        long modified = 0L;
        try (
                final PreparedStatement statement = connection.prepareStatement(
                        this.buildSql.sql()
                )
        ) {
            while (iterableLines.hasNext()) {
                final Line parsedLine = this.parser.parse(iterableLines.next());
                int index = 1;
                for (Value value : parsedLine.values()) {
                    value.accept(new AddPreparedStatement(statement, index++));
                }
                count++;
                statement.addBatch();
                if (count % chunkSize == 0) {
                    modified += Arrays.stream(statement.executeBatch()).sum();
                }
            }
            if (count % chunkSize != 0) {
                modified += Arrays.stream(statement.executeBatch()).sum();
            }
            return modified;
        } catch (SQLException e) {
            throw new EzException("Sql error: " + e.toString(), e);
        } catch (EzParseException e) {
            throw new EzException(
                    "errors on row " + count + " --> " + e.errors().toString()
            );
        }
    }
}
