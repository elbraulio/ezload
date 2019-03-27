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

package com.elbraulio.ezload.action;

import com.elbraulio.ezload.exception.EzException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import util.DropData;
import util.ReadValue;
import util.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Unit test for {@link AddPreparedStatement}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class AddPreparedStatementTest {
    @Test
    public void addInteger() {
        try (Connection connection = new SqliteConnection().connection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into test (int_val) values (?);"
            );
            new AddPreparedStatement(ps, 1).execute(42);
            ps.execute();
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    new ReadValue(
                            "select int_val from test;", connection
                    ).value(rs -> rs.getInt(1)).get(0),
                    CoreMatchers.is(42)
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
    public void addDouble() {
        try (Connection connection = new SqliteConnection().connection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into test (double_val) values (?);"
            );
            new AddPreparedStatement(ps, 1).execute(42d);
            ps.execute();
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    new ReadValue(
                            "select double_val from test;", connection
                    ).value(rs -> rs.getDouble(1)).get(0),
                    CoreMatchers.is(42.0)
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
    public void addString() {
        try (Connection connection = new SqliteConnection().connection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into test (string_val) values (?);"
            );
            new AddPreparedStatement(ps, 1).execute("value");
            ps.execute();
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    new ReadValue(
                            "select string_val from test;", connection
                    ).value(rs -> rs.getString(1)).get(0),
                    CoreMatchers.is("value")
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
}