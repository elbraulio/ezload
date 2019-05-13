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

import com.elbraulio.ezload.column.Column;
import com.elbraulio.ezload.column.GenericColumn;
import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.parse.DefaultParser;
import com.elbraulio.ezload.parse.Parser;
import com.elbraulio.ezload.transform.ToString;
import com.elbraulio.ezload.value.StringValue;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import util.DropData;
import util.ReadValue;
import util.SqliteConnection;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.fail;

/**
 * Unit test for {@link InsertFromParser}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertFromParserTest {
    @Test
    public void addString() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            0, "string_val", new NoConstraint<>(),
                            new ToString(), StringValue::new
                    )
            );
            Parser parser = new DefaultParser(",", 1, columns);
            new InsertFromParser(
                    parser, new SqlFromParser("test", parser),
                    Integer.MAX_VALUE
            ).execute(
                    connection,
                    new BufferedReader(new StringReader("hello!"))
            );
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    new ReadValue(
                            "SELECT string_val FROM test;", connection
                    ).value((rs) -> rs.getString(1)).get(0),
                    CoreMatchers.is("hello!")
            );
        } catch (SQLException | EzException e) {
            e.printStackTrace();
        } finally {
            try {
                new DropData("test", new SqliteConnection().connection())
                        .drop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void insertWithChunkSize() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            0, "string_val", new NoConstraint<>(),
                            new ToString(), StringValue::new
                    )
            );
            Parser parser = new DefaultParser(",", 1, columns);
            new InsertFromParser(
                    parser, new SqlFromParser("test", parser),
                    1
            ).execute(
                    connection,
                    new BufferedReader(new StringReader("a\nb\nc\nd\nf\ng"))
            );
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    new ReadValue(
                            "SELECT count(string_val) FROM test;", connection
                    ).value((rs) -> rs.getInt(1)).get(0),
                    CoreMatchers.is(6)
            );
        } catch (SQLException | EzException e) {
            e.printStackTrace();
        } finally {
            try {
                new DropData("test", new SqliteConnection().connection())
                        .drop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void sqlError() {
        try (Connection connection = new SqliteConnection().connection()) {
            connection.close();
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            0, "string_val", new NoConstraint<>(),
                            new ToString(), StringValue::new
                    )
            );
            Parser parser = new DefaultParser(",", 1, columns);
            new InsertFromParser(
                    parser, new SqlFromParser("test", parser),
                    1
            ).execute(
                    connection,
                    new BufferedReader(new StringReader("a\nb\nc\nd\nf\ng"))
            );
            fail();
        } catch (SQLException | EzException e) {
            MatcherAssert.assertThat(
                    "sql errors must include message",
                    e.toString(),
                    Matchers.is(
                            "com.elbraulio.ezload.exception.EzException: " +
                                    "Sql error: java.sql.SQLException: " +
                                    "database connection closed"
                    )
            );
        } finally {
            try {
                new DropData("test", new SqliteConnection().connection())
                        .drop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}