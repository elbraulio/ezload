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

import com.elbraulio.ezload.batch.StringBatch;
import com.elbraulio.ezload.constrain.NoConstrain;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.parse.DefaultParser;
import com.elbraulio.ezload.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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
                            0, "string_val", new NoConstrain<>(),
                            new ToString(), new StringBatch()
                    )
            );
            new InsertFromParser(
                    "test", new DefaultParser(",", 1, columns)
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
}