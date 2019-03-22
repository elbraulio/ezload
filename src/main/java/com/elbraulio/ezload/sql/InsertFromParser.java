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

import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.logger.EzLogger;
import com.elbraulio.ezload.logger.NoLog;
import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.parse.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

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
    private final EzLogger logger;

    /**
     * Ctor.
     *
     * @param parser    parser with format.
     * @param buildSql  sql query builder.
     * @param chunkSize size to split the batch.
     */
    public InsertFromParser(Parser parser, BuildSql buildSql, int chunkSize) {
        this(parser, buildSql, chunkSize, new NoLog());
    }

    /**
     * Ctor.
     *
     * @param parser    table name.
     * @param buildSql  sql builder.
     * @param chunkSize size to split the batch.
     * @param logger    logger.
     */
    public InsertFromParser(
            Parser parser, BuildSql buildSql, int chunkSize, EzLogger logger) {

        this.parser = parser;
        this.buildSql = buildSql;
        this.chunkSize = chunkSize;
        this.logger = logger;
    }

    /*
    @todo change Insert:execute buffered reader by a EzReader
    @body giving the BufferedReader implies that it always reads by line. It
    @body will be much better if the user can decide the way that the source
    @body is read.
     */
    @Override
    public long execute(Connection connection, BufferedReader bufferedReader)
            throws EzException {
        /*
         * @todo this method from InsertFromParser is too complex
         * @body > from #40
         * @body
         * @body must be refactored and decoupled on different objects.
         */
        try (
                PreparedStatement psmt = connection.prepareStatement(
                        this.buildSql.sql()
                )
        ) {
            String line;
            int lines = 0;
            int modified = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lines++;
                this.logger.info("line read: " + line, "InsertFromParser");
                final String[] split = line.split(this.parser.separator());
                int index = 1;
                for (Column col : this.parser.columns()) {
                    if (col.order() >= split.length || col.order() < 0) {
                        throw new EzException(
                                "column order " + col.order() + " must be " +
                                        "on range [0, " + split.length + "]."
                        );
                    }
                    final String raw = split[col.order()];
                    try {
                        if (col.isValid(raw)) {
                            col.addToPreparedStatement(psmt, index, raw);
                        } else {
                            throw new EzException(
                                    "raw value '" + raw + "' is not valid."
                            );
                        }
                    } catch (Exception e) {
                        throw new EzException(
                                "(column " + col.order() + ", row " +
                                        (lines - 1) + ") --> " + e.toString()
                        );
                    }
                    index++;
                }
                psmt.addBatch();
                if (lines % chunkSize == 0) {
                    modified += Arrays.stream(psmt.executeBatch()).sum();
                }
            }
            if (lines % chunkSize != 0) {
                modified += Arrays.stream(psmt.executeBatch()).sum();
            }
            return modified;
        } catch (SQLException e) {
            throw new EzException("Sql error", e);
        } catch (IOException e) {
            throw new EzException("IO error", e);
        }
    }
}
