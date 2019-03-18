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

package com.elbraulio.ezload.model;

import com.elbraulio.ezload.batch.AddBatch;
import com.elbraulio.ezload.constrain.Constrain;
import com.elbraulio.ezload.transform.Transform;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This implementation works as a generic representation of the value and
 * format that a column (or cell) stores.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class GenericColumn<T> implements Column<T> {
    private final int order;
    private final String name;
    private final Constrain<T> constrain;
    private final Transform<T> transform;
    private final AddBatch<T> addBatch;

    /**
     * Ctor.
     *
     * @param order     position on file, from left to right starting from 0.
     * @param name      column name.
     * @param constrain value constrain.
     * @param transform value transform.
     * @param addBatch  batch operation to add value.
     */
    public GenericColumn(
            int order, String name, Constrain<T> constrain,
            Transform<T> transform, AddBatch<T> addBatch
    ) {

        this.order = order;
        this.name = name;
        this.constrain = constrain;
        this.transform = transform;
        this.addBatch = addBatch;
    }

    @Override
    public T value(String value) {
        return this.transform.from(value);
    }

    @Override
    public int order() {
        return this.order;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean isValid(String value) {
        return this.constrain.isValid(value(value));
    }

    @Override
    public PreparedStatement addToPreparedStatement(
            PreparedStatement ps, int index, String value
    ) throws SQLException {
        /*
         * @todo value method on column is not necessary
         * @body is too much information to the user. The only thing that care about
         * @body the transformed value is the this column. So in this example
         * @body it can just be used `this.transform.from(value)`.
         */
        return this.addBatch.addValue(ps, index, value(value));
    }
}
