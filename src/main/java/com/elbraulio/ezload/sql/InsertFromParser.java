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

import com.elbraulio.ezload.action.AddPreparedStatement;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.exception.EzParseException;
import com.elbraulio.ezload.line.Line;
import com.elbraulio.ezload.parse.Parser;
import com.elbraulio.ezload.value.Value;

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

    /*
    @todo change Insert:execute buffered reader by a EzReader
    @body giving the BufferedReader implies that it always reads by line. It
    @body will be much better if the user can decide the way that the source
    @body is read.
     */
    @Override
    public long execute(Connection connection, BufferedReader bufferedReader)
            throws EzException {
        String raw;
        int lines = 0;
        long modified = 0L;
        try (
                PreparedStatement psmt = connection.prepareStatement(
                        this.buildSql.sql()
                )
        ) {
            while ((raw = bufferedReader.readLine()) != null) {
                Line parsedLine = this.parser.parse(raw);
                int index = 1;
                for (Value value : parsedLine.values()) {
                    value.accept(new AddPreparedStatement(psmt, index++));
                }
                lines++;
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
            throw new EzException("Sql error: " + e.toString(), e);
        } catch (IOException e) {
            throw new EzException("IO error: " + e.toString(), e);
        } catch (EzParseException e) {
            throw new EzException(
                    "errors on row " + lines + " --> " +
                            e.errors().toString()
            );
        }
    }
}
