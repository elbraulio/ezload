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

package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Read a value from a connection with a given query.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class ReadValue {
    private final String sql;
    private final Connection connection;

    /**
     * Ctor.
     *
     * @param sql        query to retrieve data.
     * @param connection connection to database.
     */
    public ReadValue(String sql, Connection connection) {
        this.sql = sql;
        this.connection = connection;
    }

    /**
     * Retrieve data from database.
     *
     * @param rq  user specification to take the data.
     * @param <T> data type to return.
     * @return list of results.
     */
    public <T> List<T> value(ResultGet<T> rq) {
        try (
                ResultSet rs = this.connection.createStatement()
                        .executeQuery(this.sql)
        ) {
            final List<T> result = new LinkedList<>();
            while (rs.next()) {
                result.add(rq.get(rs));
            }
            return result;
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }
}
