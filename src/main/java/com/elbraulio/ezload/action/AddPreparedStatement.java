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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * This action add a value to a PreparedStatement.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.3.0
 */
public final class AddPreparedStatement implements Action {
    private final PreparedStatement psmt;
    private final int index;

    /**
     * Ctor.
     *
     * @param psmt  prepared statement.
     * @param index index to add value.
     */
    public AddPreparedStatement(PreparedStatement psmt, int index) {
        this.psmt = psmt;
        this.index = index;
    }

    @Override
    public void execute(Integer value) throws EzException {
        try {
            if (value != null)
                this.psmt.setInt(this.index, value);
            else
                this.psmt.setNull(this.index, Types.INTEGER);
        } catch (SQLException e) {
            throw new EzException(
                    "setInt error with value '" + value + "': " + e.toString(),
                    e
            );
        }
    }

    @Override
    public void execute(Double value) throws EzException {
        try {
            if (value != null)
                this.psmt.setDouble(this.index, value);
            else
                this.psmt.setNull(this.index, Types.DOUBLE);
        } catch (SQLException e) {
            throw new EzException(
                    "setDouble error with value '" + value + "': " +
                            e.toString(),
                    e
            );
        }
    }

    @Override
    public void execute(String value) throws EzException {
        try {
            if (value != null)
                this.psmt.setString(this.index, value);
            else
                this.psmt.setNull(this.index, Types.VARCHAR);
        } catch (SQLException e) {
            throw new EzException(
                    "setString error with value '" + value + "': " +
                            e.toString(),
                    e
            );
        }
    }

    @Override
    public void execute(LocalDateTime value) throws EzException {
        try {
            if (value != null)
                this.psmt.setTimestamp(this.index, Timestamp.valueOf(value));
            else
                this.psmt.setNull(this.index, Types.TIMESTAMP);
        } catch (SQLException e) {
            throw new EzException(
                    "setTimestamp error with value '" + value + "': " +
                            e.toString(),
                    e
            );
        }
    }
}
