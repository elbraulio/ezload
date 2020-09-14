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

package com.elbraulio.ezload;

import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.parse.Parser;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link EzInsert}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class EzInsertTest {

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
    public void insertString() throws EzException {
        Parser parser = EzLoad.parse(",", 1)
                .withCol(EzCol.string(0,
                        "string_val",
                        new NoConstraint<>()))
                .parser();
        long affected = EzInsert.fromParser(
                connection,
                "test",
                parser,
                Arrays.stream(new String[]{"hi!"}));
        assertEquals(affected, 1L);
    }

    @Test
    public void insertWithChunk() throws SQLException, EzException {
        int chunkSize = 1;
        doReturn(new int[]{chunkSize}).when(preparedStatement).executeBatch();
        Parser parser = EzLoad.parse(",", 1)
                .withCol(EzCol.string(0,
                        "string_val",
                        new NoConstraint<>()))
                .parser();
        String[] input = {"1", "b", "c"};
        long affected = EzInsert.fromParser(
                connection,
                "test",
                parser,
                Arrays.stream(input),
                chunkSize);
        verify(preparedStatement, times(input.length / chunkSize)).executeBatch();
        assertEquals(affected, input.length);
    }

    @Test
    public void exceptionsIncludeDetails() {
        try {
            Parser parser = EzLoad.parse(",", 1)
                    .withCol(EzCol.integer(0,
                            "int_val",
                            new NoConstraint<>()))
                    .parser();
            String[] input = {"hi!"};
            EzInsert.fromParser(
                    connection,
                    "test",
                    parser,
                    Arrays.stream(input));
            fail("Exception must be thrown");
        } catch (EzException e) {
            assertEquals("errors on row 0 --> [column 0: java.lang" +
                    ".NumberFormatException: For input " +
                    "string: \"hi!\"]", e.getMessage());
        }
    }

    @Test
    public void exceptionWithMultipleCols() {
        try {
            Parser parser = EzLoad.parse(",", 1)
                    .withCol(EzCol.integer(1,
                            "int_val",
                            new NoConstraint<>()))
                    .withCol(EzCol.string(0,
                            "string_val",
                            new NoConstraint<>()))
                    .parser();
            String[] input = {"1,bye"};
            EzInsert.fromParser(
                    connection,
                    "test",
                    parser,
                    Arrays.stream(input));
            fail("Exception must be thrown");
        } catch (EzException e) {
            assertEquals("errors on row 0 --> [column 1: java.lang" +
                    ".NumberFormatException: For input " +
                    "string: \"bye\"]", e.getMessage());
        }
    }

    @Test
    public void insertNullableInteger() throws EzException {
        Parser parser = EzLoad.parse(",", 1)
                .withCol(EzCol.nullable("",
                        EzCol.integer(0,
                                "int_val",
                                new NoConstraint<>())))
                .parser();
        long affected = EzInsert.fromParser(
                connection,
                "test",
                parser,
                Arrays.stream(new String[]{""}));
        assertEquals(affected, 1L);
    }
}
