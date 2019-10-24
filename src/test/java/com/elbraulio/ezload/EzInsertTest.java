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

package com.elbraulio.ezload;

import com.elbraulio.ezload.column.Column;
import com.elbraulio.ezload.column.GenericColumn;
import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.parse.DefaultParser;
import com.elbraulio.ezload.transform.ToInt;
import com.elbraulio.ezload.transform.ToString;
import com.elbraulio.ezload.value.IntValue;
import com.elbraulio.ezload.value.StringValue;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import util.DropData;
import util.SqliteConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Unit test for {@link EzInsert}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class EzInsertTest {
    @Test
    public void insertString() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            0, "string_val", new NoConstraint<>(),
                            new ToString(), StringValue::new
                    )
            );
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    EzInsert.fromParser(
                            connection, "test",
                            new DefaultParser(",", 1, columns),
                            Arrays.stream(new String[]{"hi!"})
                    ),
                    CoreMatchers.is(1L)
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
    public void insertWithChunk() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            0, "string_val", new NoConstraint<>(),
                            new ToString(), StringValue::new
                    )
            );
            MatcherAssert.assertThat(
                    "values must be added to batch by chunks",
                    EzInsert.fromParser(
                            connection, "test",
                            new DefaultParser(",", 1, columns),
                            Arrays.stream(new String[]{"1", "b", "c"}),
                            1
                    ),
                    CoreMatchers.is(3L)
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
    public void exceptionsIncludeDetails() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            0, "int_val", new NoConstraint<>(),
                            new ToInt(), IntValue::new
                    )
            );
            EzInsert.fromParser(
                    connection, "test",
                    new DefaultParser(",", 1, columns),
                    Arrays.stream(new String[]{"hi!"})
            );
            fail("Exception must be thrown");
        } catch (SQLException | EzException | NumberFormatException e) {
            //e.printStackTrace();
            MatcherAssert.assertThat(
                    "exception must contain descriptions",
                    e.getMessage(),
                    CoreMatchers.is(
                            "errors on row 0 --> [column 0: java.lang" +
                                    ".NumberFormatException: For input " +
                                    "string: \"hi!\"]"
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

    @Test
    public void exceptionWithMultipleCols() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    new GenericColumn<>(
                            1, "int_val", new NoConstraint<>(),
                            new ToInt(), IntValue::new
                    )
            );
            columns.add(
                    new GenericColumn<>(
                            0, "string_val", new NoConstraint<>(),
                            new ToString(), StringValue::new
                    )
            );
            EzInsert.fromParser(
                    connection, "test",
                    new DefaultParser(",", 2, columns),
                    Arrays.stream(new String[]{"1,bye"})
            );
            fail("Exception must be thrown");
        } catch (SQLException | EzException | NumberFormatException e) {
            MatcherAssert.assertThat(
                    "exception must contain descriptions",
                    e.getMessage(),
                    CoreMatchers.is(
                            "errors on row 0 --> [column 1: java.lang" +
                                    ".NumberFormatException: For input " +
                                    "string: \"bye\"]"
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

    @Test
    public void insertNullableInteger() {
        try (Connection connection = new SqliteConnection().connection()) {
            final List<Column> columns = new LinkedList<>();
            columns.add(
                    EzCol.nullable(
                            "",
                            EzCol.integer(
                                    0,
                                    "string_val",
                                    new NoConstraint<>()
                            )
                    )
            );
            MatcherAssert.assertThat(
                    "null values must be added to batch",
                    EzInsert.fromParser(
                            connection, "test",
                            new DefaultParser(",", 1, columns),
                            Arrays.stream(new String[]{""})
                    ),
                    CoreMatchers.is(1L)
            );
        } catch (SQLException | EzException e) {
            e.printStackTrace();
            fail("Exception not expected");
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