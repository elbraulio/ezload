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

import com.elbraulio.ezload.EzCol;
import com.elbraulio.ezload.EzLoad;
import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.parse.Parser;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link InsertFromParser}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertFromParserTest {

    private Connection connection;
    private PreparedStatement preparedStatement;

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        doReturn(preparedStatement).when(connection).prepareStatement(anyString());
        doReturn(new int[]{1}).when(preparedStatement).executeBatch();
    }

    @Test
    public void addString() throws EzException, SQLException {
        Parser parser = EzLoad.parse(",", 1)
                .withCol(EzCol.string(0,
                        "string_val",
                        new NoConstraint<>()))
                .parser();
        Insert insert = new InsertFromParser(parser,
                new SqlFromParser("test", parser),
                Integer.MAX_VALUE);
        insert.execute(connection, Arrays.stream(new String[]{"hello!"}));
        verify(preparedStatement).executeBatch();
    }

    @Test
    public void insertWithChunkSize() throws EzException, SQLException {
        Parser parser = EzLoad.parse(",", 1)
                .withCol(EzCol.string(0,
                        "string_val",
                        new NoConstraint<>()))
                .parser();
        Insert insert = new InsertFromParser(parser,
                new SqlFromParser("test", parser),
                1);
        insert.execute(connection, Arrays.stream(new String[]{"a", "b", "c", "d", "f", "g"}));
        verify(preparedStatement, times(6)).executeBatch();
    }

    @Test
    public void sqlError() throws SQLException {
        try {
            doThrow(SQLException.class).when(connection).prepareStatement(anyString());
            Parser parser = EzLoad.parse(",", 1)
                    .withCol(EzCol.string(0,
                            "string_val",
                            new NoConstraint<>()))
                    .parser();
            Insert insert = new InsertFromParser(parser,
                    new SqlFromParser("test", parser),
                    Integer.MAX_VALUE);
            insert.execute(connection, Arrays.stream(new String[]{"hello!"}));
            fail();
        } catch (EzException e) {
            assertEquals("Sql error: java.sql.SQLException", e.getMessage());
        }
    }
}
