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

package com.elbraulio.ezload.action;

import com.elbraulio.ezload.exception.EzException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link AddPreparedStatement}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class AddPreparedStatementTest {

    private PreparedStatement preparedStatement;
    private ArgumentCaptor<Integer> indexCaptor;

    @Before
    public void setUp() {
        preparedStatement = mock(PreparedStatement.class);
        indexCaptor = ArgumentCaptor.forClass(Integer.class);
    }

    @Test
    public void addInteger() throws EzException, SQLException {
        int index = 1;
        int value = 10;
        Action action = new AddPreparedStatement(preparedStatement, index);
        action.execute(value);
        ArgumentCaptor<Integer> valueCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(preparedStatement, times(1)).setInt(indexCaptor.capture(), valueCaptor.capture());
        assertEquals(index, indexCaptor.getValue().intValue());
        assertEquals(value, valueCaptor.getValue().intValue());
    }

    @Test
    public void intFail() {
        try {
            doThrow(SQLException.class).when(preparedStatement).setInt(anyInt(), anyInt());
            Action action = new AddPreparedStatement(preparedStatement, 0);
            action.execute(10);
        } catch (SQLException e) {
            fail();
        } catch (EzException e) {
            assertEquals("setInt error with value '10': java.sql.SQLException", e.getMessage());
        }
    }

    @Test
    public void integerNull() throws EzException, SQLException {
        Action action = new AddPreparedStatement(preparedStatement, 1);
        Integer value = null;
        action.execute(value);
        verify(preparedStatement, times(1)).setNull(anyInt(), anyInt());
    }

    @Test
    public void addDouble() throws EzException, SQLException {
        int index = 1;
        double value = 10d;
        Action action = new AddPreparedStatement(preparedStatement, index);
        action.execute(value);
        ArgumentCaptor<Double> valueCaptor = ArgumentCaptor.forClass(Double.class);
        verify(preparedStatement, times(1)).setDouble(indexCaptor.capture(), valueCaptor.capture());
        assertEquals(index, indexCaptor.getValue().intValue());
        assertEquals(value, valueCaptor.getValue(), 0.0001);
    }

    @Test
    public void doubleFail() {
        try {
            doThrow(SQLException.class).when(preparedStatement).setDouble(anyInt(), anyDouble());
            Action action = new AddPreparedStatement(preparedStatement, 0);
            action.execute(10d);
        } catch (SQLException e) {
            fail();
        } catch (EzException e) {
            assertEquals("setDouble error with value '10.0': java.sql.SQLException", e.getMessage());
        }
    }

    @Test
    public void doubleNull() throws EzException, SQLException {
        Action action = new AddPreparedStatement(preparedStatement, 1);
        Double value = null;
        action.execute(value);
        verify(preparedStatement, times(1)).setNull(anyInt(), anyInt());
    }

    @Test
    public void addString() throws EzException, SQLException {
        int index = 1;
        String value = "hello";
        Action action = new AddPreparedStatement(preparedStatement, index);
        action.execute(value);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(preparedStatement, times(1)).setString(indexCaptor.capture(), valueCaptor.capture());
        assertEquals(index, indexCaptor.getValue().intValue());
        assertEquals(value, valueCaptor.getValue());
    }

    @Test
    public void stringFail() {
        try {
            doThrow(SQLException.class).when(preparedStatement).setString(anyInt(), anyString());
            Action action = new AddPreparedStatement(preparedStatement, 0);
            action.execute("hello");
        } catch (SQLException e) {
            fail();
        } catch (EzException e) {
            assertEquals("setString error with value 'hello': java.sql.SQLException", e.getMessage());
        }
    }

    @Test
    public void stringNull() throws EzException, SQLException {
        Action action = new AddPreparedStatement(preparedStatement, 1);
        String value = null;
        action.execute(value);
        verify(preparedStatement, times(1)).setNull(anyInt(), anyInt());
    }

    @Test
    public void addDateTime() throws EzException, SQLException {
        int index = 1;
        LocalDateTime value = LocalDateTime.now();
        Action action = new AddPreparedStatement(preparedStatement, index);
        action.execute(value);
        ArgumentCaptor<Timestamp> valueCaptor = ArgumentCaptor.forClass(Timestamp.class);
        verify(preparedStatement, times(1)).setTimestamp(indexCaptor.capture(), valueCaptor.capture());
        assertEquals(index, indexCaptor.getValue().intValue());
        assertEquals(value, valueCaptor.getValue().toLocalDateTime());
    }

    @Test
    public void dateTimeFail() {
        LocalDateTime value = LocalDateTime.now();
        try {
            doThrow(SQLException.class).when(preparedStatement).setTimestamp(anyInt(), any(Timestamp.class));
            Action action = new AddPreparedStatement(preparedStatement, 0);
            action.execute(value);
        } catch (SQLException e) {
            fail();
        } catch (EzException e) {
            assertEquals("setTimestamp error with value '" + value.toString() + "': java.sql.SQLException",
                    e.getMessage());
        }
    }

    @Test
    public void dateTimeNull() throws EzException, SQLException {
        Action action = new AddPreparedStatement(preparedStatement, 1);
        LocalDateTime value = null;
        action.execute(value);
        verify(preparedStatement, times(1)).setNull(anyInt(), anyInt());
    }

    @Test
    public void addDate() throws EzException, SQLException {
        int index = 1;
        LocalDate value = LocalDate.now();
        Action action = new AddPreparedStatement(preparedStatement, index);
        action.execute(value);
        ArgumentCaptor<Date> valueCaptor = ArgumentCaptor.forClass(Date.class);
        verify(preparedStatement, times(1)).setDate(indexCaptor.capture(), valueCaptor.capture());
        assertEquals(index, indexCaptor.getValue().intValue());
        assertEquals(value, valueCaptor.getValue().toLocalDate());
    }

    @Test
    public void dateFail() {
        LocalDate value = LocalDate.now();
        try {
            doThrow(SQLException.class).when(preparedStatement).setDate(anyInt(), any(Date.class));
            Action action = new AddPreparedStatement(preparedStatement, 0);
            action.execute(value);
        } catch (SQLException e) {
            fail();
        } catch (EzException e) {
            assertEquals("setDate error with value '" + value.toString() + "': java.sql.SQLException",
                    e.getMessage());
        }
    }

    @Test
    public void dateNull() throws EzException, SQLException {
        Action action = new AddPreparedStatement(preparedStatement, 1);
        LocalDate value = null;
        action.execute(value);
        verify(preparedStatement, times(1)).setNull(anyInt(), anyInt());
    }
}
