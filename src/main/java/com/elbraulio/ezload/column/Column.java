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
 * A Column represent the format that a value from a column should check.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.3.0 moved from model package.
 */
public interface Column<T> {

    /**
     * Parsed value.
     *
     * @param value original value.
     * @return parsed value.
     */
    T parse(String value);

    /**
     * Position from left to right of the column on a line separated by a
     * expression. From 0 to n.
     *
     * @return position number.
     */
    int order();

    /**
     * Column's name.
     *
     * @return name.
     */
    String name();
    
    /**
     * Checks if the value is valid or not.
     *
     * @param value raw value to check.
     * @return true if it is valid and false if it is not.
     */
    boolean isValid(String value);

    /**
     * Return a {@link Value} to execute actions.
     *
     * @param raw raw value.
     * @return a {@link Value}.
     */
    Value value(String raw);
}
