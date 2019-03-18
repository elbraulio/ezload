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

package com.elbraulio.ezload.model;

import com.elbraulio.ezload.batch.IntBatch;
import com.elbraulio.ezload.batch.StringBatch;
import com.elbraulio.ezload.constrain.NoConstrain;
import com.elbraulio.ezload.transform.ToInt;
import com.elbraulio.ezload.transform.ToString;
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
 * Unit test for {@link GenericColumn}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GenericColumnTest {

    private final Column<String> col = new GenericColumn<>(
            0, "column_name",
            new NoConstrain<>(), new ToString(),
            new StringBatch()
    );

    @Test
    public void plainTextValueWithNoConstrains() {
        MatcherAssert.assertThat(
                "a plain text with no constrains and transformations" +
                        "should keep the same",
                col.value("plain text"),
                CoreMatchers.is("plain text")
        );
    }

    @Test
    public void orderNotChange() {
        MatcherAssert.assertThat(
                "order always is the same",
                col.order(),
                CoreMatchers.is(0)
        );
    }

    @Test
    public void nameNotChange() {
        MatcherAssert.assertThat(
                "name always is the same",
                col.name(),
                CoreMatchers.is("column_name")
        );
    }

    @Test
    public void validationTrueWithNoConstrains() {
        MatcherAssert.assertThat(
                "always is valid",
                col.isValid("some input"),
                CoreMatchers.is(true)
        );
    }

    @Test
    public void addValueToBatch() {
        try (
                Connection connection = new SqliteConnection().connection();
                PreparedStatement psmt = connection.prepareStatement(
                        "INSERT INTO test (int_val) VALUES (?);"
                )
        ) {
            new GenericColumn<>(
                    0, "name", new NoConstrain<>(), new ToInt(),
                    new IntBatch()
            ).addToPreparedStatement(psmt, 1, "2");
            psmt.addBatch();
            psmt.executeBatch();
            MatcherAssert.assertThat(
                    "values must be added to batch",
                    new ReadValue(
                            "SELECT int_val FROM test;", connection
                    ).value((rs) -> rs.getInt(1)).get(0),
                    CoreMatchers.is(2)
            );
        } catch (SQLException e) {
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