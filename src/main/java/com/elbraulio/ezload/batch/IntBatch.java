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

package com.elbraulio.ezload.batch;

import com.elbraulio.ezload.logger.EzLogger;
import com.elbraulio.ezload.logger.NoLog;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Add {@link Integer} values to the {@link PreparedStatement}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class IntBatch implements AddBatch<Integer> {

    private final EzLogger logger;

    /**
     * Ctor.
     */
    public IntBatch() {
        this(new NoLog());
    }

    /**
     * Ctor.
     *
     * @param logger logger.
     */
    public IntBatch(EzLogger logger) {
        this.logger = logger;
    }

    @Override
    public PreparedStatement addValue(
            PreparedStatement ps, int index, Integer value
    ) throws SQLException {
        this.logger.info("setInt of " + value, "IntBatch");
        ps.setInt(index, value);
        return ps;
    }
}
