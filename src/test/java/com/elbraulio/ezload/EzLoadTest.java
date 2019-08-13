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

import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.transform.ToInt;
import com.elbraulio.ezload.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import util.DropData;
import util.SqliteConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Unit test for {@link EzLoad}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class EzLoadTest {
    @Test
    public void insertValues() {
        try (Connection connection = new SqliteConnection().connection()) {
            MatcherAssert.assertThat(
                    "insert values",
                    EzInsert.fromParser(
                            connection,
                            "test",
                            EzLoad.parse(",", 2).withCol(
                                    EzCol.integer(
                                            0, "int_val",
                                            new NoConstraint<>(), new ToInt()
                                    )
                            ).withCol(
                                    EzCol.string(
                                            1, "string_val",
                                            new NoConstraint<>(), new ToString()
                                    )
                            ).parser(),
                            Arrays.stream(new String[]{"1,dos"})
                    ),
                    CoreMatchers.is(1L));
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