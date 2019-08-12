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

package com.elbraulio.ezload.column;

import com.elbraulio.ezload.value.Value;

/**
 * Allows a given column to return <code>null</code> values.
 * This overrides parse and isValid in order to support null values.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.4.0
 */
public class Nullable<T> implements Column<T> {

    private final String expression;
    private final Column<T> decorated;

    /**
     * Ctor.
     *
     * @param expression text that represents null.
     * @param decorated  nullable {@link Column}.
     */
    public Nullable(String expression, Column<T> decorated) {
        this.expression = expression;
        this.decorated = decorated;
    }

    @Override
    public T parse(String value) {
        return this.expression.equals(value)
                ? null
                : this.decorated.parse(value);
    }

    @Override
    public int position() {
        return this.decorated.position();
    }

    @Override
    public String name() {
        return this.decorated.name();
    }

    @Override
    public boolean isValid(String value) {
        return this.expression.equals(value) || this.decorated.isValid(value);
    }

    @Override
    public Value value(String raw) {
        return this.decorated.value(raw);
    }
}