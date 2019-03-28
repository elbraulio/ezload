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

import com.elbraulio.ezload.column.Column;
import com.elbraulio.ezload.exception.EzParseException;
import com.elbraulio.ezload.line.DefaultLine;
import com.elbraulio.ezload.line.Line;
import com.elbraulio.ezload.value.Value;

import java.util.LinkedList;
import java.util.List;

/**
 * Default parser for files.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.1.0
 */
public final class DefaultParser implements Parser {

    private final String expression;
    private final Integer columnsNumber;
    private final List<Column> columns;

    /**
     * Ctor
     *
     * @param expression    separation expression.
     * @param columnsNumber number of columns.
     * @param columns       columns.
     */
    public DefaultParser(
            String expression, Integer columnsNumber, List<Column> columns
    ) {

        this.expression = expression;
        this.columnsNumber = columnsNumber;
        this.columns = columns;
    }

    @Override
    public List<Column> columns() {
        return this.columns;
    }

    @Override
    public String separator() {
        return this.expression;
    }

    @Override
    public Line parse(String line) throws EzParseException {
        final String[] split = line.split(this.expression);
        final List<String> errors = new LinkedList<>();
        final List<Value> values = new LinkedList<>();
        if (split.length < this.columnsNumber) {
            errors.add(
                    "line length is " + split.length +
                            ", must be greater than " + this.columnsNumber +
                            " on line: [" + line + "]"
            );
            throw new EzParseException("parse error", errors);
        }

        this.columns.forEach(
                col -> {
                    try {
                        final String raw = split[col.order()];
                        if (col.isValid(raw)) {
                            values.add(col.value(raw));
                        } else {
                            errors.add(
                                    "column " + col.order() + " does not " +
                                            "accept value '" + raw + "'"
                            );
                        }
                    } catch (Exception e) {
                        errors.add(
                                "column " + col.order() + ": " +
                                        e.toString()
                        );
                    }
                }
        );
        if (errors.isEmpty()) {
            return new DefaultLine(values);
        } else {
            throw new EzParseException("parse errors", errors);
        }
    }
}
