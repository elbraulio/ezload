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

import com.elbraulio.ezload.batch.AddBatch;
import com.elbraulio.ezload.batch.DoubleBatch;
import com.elbraulio.ezload.batch.IntBatch;
import com.elbraulio.ezload.batch.StringBatch;
import com.elbraulio.ezload.constrain.Constrain;
import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.transform.Transform;

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
     * @param order     column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @return {@link Column}
     */
    public static Column<Integer> integer(
            int order, String name, Constrain<Integer> constrain,
            Transform<Integer> transform
    ) {
        return EzCol.newCol(
                order, name, constrain, transform, new IntBatch()
        );
    }

    /**
     * create a new Double column.
     *
     * @param order     column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @return {@link Column}
     */
    public static Column<Double> doublee(
            int order, String name, Constrain<Double> constrain,
            Transform<Double> transform
    ) {
        return EzCol.newCol(
                order, name, constrain, transform, new DoubleBatch()
        );
    }

    /**
     * create a new String column.
     *
     * @param order     column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @return {@link Column}
     */
    public static Column<String> string(
            int order, String name, Constrain<String> constrain,
            Transform<String> transform
    ) {
        return EzCol.newCol(
                order, name, constrain, transform, new StringBatch()
        );
    }

    /**
     * Generic column creation.
     *
     * @param order     column position from left to right, starting from 0.
     * @param name      column name
     * @param constrain column constrain.
     * @param transform column transform.
     * @param addBatch  addBatch depending on value type.
     * @param <T>       depends on value type.
     * @return {@link Column}
     */
    private static <T> Column<T> newCol(
            int order, String name, Constrain<T> constrain,
            Transform<T> transform, AddBatch<T> addBatch
    ) {
        /**
         * @todo The order and column match are unique combination
         * @body EzCol:newCol does not validate the fact that the order and
         * @body column match are unique combination.
         */
        return new GenericColumn<>(
                order, name, constrain, transform, addBatch
        );
    }
}
