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
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Remove all data from a given table.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DropData {
    private final String table;
    private final Connection connection;

    /**
     * Ctor.
     *
     * @param table      table where to remove data.
     * @param connection connection to db.
     */
    public DropData(String table, Connection connection) {
        this.table = table;
        this.connection = connection;
    }

    /**
     * Removes the data from the given table.
     *
     * @throws SQLException connection issues.
     */
    public void drop() throws SQLException {
        try (
                PreparedStatement psmt = this.connection.prepareStatement(
                        "DELETE FROM " + this.table + " ;"
                )
        ) {
            psmt.executeUpdate();
        }
    }
}
