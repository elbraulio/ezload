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

package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;

import java.util.LinkedList;
import java.util.List;

/**
 * Default parser builder.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class DefaultParseBuild implements ParseBuild {
    private final String expression;
    private final int columnsNumber;
    private final List<Column> columns;

    /**
     * Ctor.
     *
     * @param expression    separator expression.
     * @param columnsNumber number of columns.
     */
    public DefaultParseBuild(String expression, int columnsNumber) {
        this(expression, columnsNumber, new LinkedList<>());
    }

    /**
     * Ctor.
     *
     * @param expression    separator expression.
     * @param columnsNumber number of columns.
     * @param columns       columns formats.
     */
    public DefaultParseBuild(
            String expression, int columnsNumber, List<Column> columns
    ) {

        this.expression = expression;
        this.columnsNumber = columnsNumber;
        this.columns = columns;
    }

    @Override
    public ParseBuild withCol(Column col) {
        this.columns.add(col);
        return this;
    }

    @Override
    public Parser parser() {
        return new DefaultParser(
                this.expression, this.columnsNumber, this.columns
        );
    }
}
