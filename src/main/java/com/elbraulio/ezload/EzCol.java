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

package com.elbraulio.ezload;

import com.elbraulio.ezload.column.Column;
import com.elbraulio.ezload.column.GenericColumn;
import com.elbraulio.ezload.constrain.Constrain;
import com.elbraulio.ezload.transform.Transform;
import com.elbraulio.ezload.value.DoubleValue;
import com.elbraulio.ezload.value.IntValue;
import com.elbraulio.ezload.value.StringValue;
import com.elbraulio.ezload.value.ValueFactory;

/**
 * {@link Column} factory. This design come from the fact that we need to
 * know what is the type of <code>value</code>.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.1.0
 */
public final class EzCol {

    /**
     * create a new Integer column.
     *
     * @param position  column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @return {@link Column}
     */
    public static Column<Integer> integer(
            int position, String name, Constrain<Integer> constrain,
            Transform<Integer> transform
    ) {
        return EzCol.newCol(
                position, name, constrain, transform, IntValue::new
        );
    }

    /**
     * create a new Double column.
     *
     * @param position  column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @return {@link Column}
     */
    public static Column<Double> doublee(
            int position, String name, Constrain<Double> constrain,
            Transform<Double> transform
    ) {
        return EzCol.newCol(
                position, name, constrain, transform, DoubleValue::new
        );
    }

    /**
     * create a new String column.
     *
     * @param position  column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @return {@link Column}
     */
    public static Column<String> string(
            int position, String name, Constrain<String> constrain,
            Transform<String> transform
    ) {
        return EzCol.newCol(
                position, name, constrain, transform, StringValue::new
        );
    }

    /**
     * Generic column creation.
     *
     * @param position     column position from left to right, starting from 0.
     * @param name         column name
     * @param constrain    column constrain.
     * @param transform    column transform.
     * @param valueFactory value factory.
     * @param <T>          depends on value type.
     * @return {@link Column}
     */
    private static <T> Column<T> newCol(
            int position, String name, Constrain<T> constrain,
            Transform<T> transform, ValueFactory<T> valueFactory
    ) {
        return new GenericColumn<>(
                position, name, constrain, transform, valueFactory
        );
    }
}
