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

package com.elbraulio.ezload;

import com.elbraulio.ezload.column.Column;
import com.elbraulio.ezload.column.GenericColumn;
import com.elbraulio.ezload.column.Nullable;
import com.elbraulio.ezload.constraint.Constraint;
import com.elbraulio.ezload.transform.*;
import com.elbraulio.ezload.value.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * {@link Column} factory. This design come from the fact that we need to
 * know what is the type of <code>value</code>.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 0.1.0
 */
public final class EzCol {

    /**
     * Create a new Integer column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @return {@link Column}
     */
    public static Column<Integer> integer(
            int position, String name, Constraint<Integer> constraint
    ) {
        return EzCol.newCol(
                position, name, constraint, new ToInt(), IntValue::new
        );
    }

    /**
     * Create a new Integer column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @param transform  column transform.
     * @return {@link Column}
     */
    public static Column<Integer> integer(
            int position, String name, Constraint<Integer> constraint,
            Transform<Integer> transform
    ) {
        return EzCol.newCol(
                position, name, constraint, transform, IntValue::new
        );
    }

    /**
     * Create a new Double column.
     *
     * @param position  column position from left to right, starting from 0.
     * @param name      column name.
     * @param constrain column constrain.
     * @return {@link Column}
     */
    public static Column<Double> doublee(
            int position, String name, Constraint<Double> constrain
    ) {
        return EzCol.newCol(
                position, name, constrain, new ToDouble(), DoubleValue::new
        );
    }

    /**
     * Create a new Double column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @param transform  column transform.
     * @return {@link Column}
     */
    public static Column<Double> doublee(
            int position, String name, Constraint<Double> constraint,
            Transform<Double> transform
    ) {
        return EzCol.newCol(
                position, name, constraint, transform, DoubleValue::new
        );
    }

    /**
     * Create a new String column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @return {@link Column}
     */
    public static Column<String> string(
            int position, String name, Constraint<String> constraint
    ) {
        return EzCol.newCol(
                position, name, constraint, new ToString(), StringValue::new
        );
    }

    /**
     * Create a new String column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @param transform  column transform.
     * @return {@link Column}
     */
    public static Column<String> string(
            int position, String name, Constraint<String> constraint,
            Transform<String> transform
    ) {
        return EzCol.newCol(
                position, name, constraint, transform, StringValue::new
        );
    }

    /**
     * Create a new DateTime column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @return {@link Column}
     */
    public static Column<LocalDateTime> dateTime(
            int position, String name, Constraint<LocalDateTime> constraint
    ) {
        return EzCol.newCol(
                position, name, constraint, new ToLocalDateTime(), DateTimeValue::new
        );
    }

    /**
     * Create a new DateTime column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @param transform  column transform.
     * @return {@link Column}
     */
    public static Column<LocalDateTime> dateTime(
            int position, String name, Constraint<LocalDateTime> constraint,
            Transform<LocalDateTime> transform
    ) {
        return EzCol.newCol(
                position, name, constraint, transform, DateTimeValue::new
        );
    }

    /**
     * Create a new Date column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @return {@link Column}
     */
    public static Column<LocalDate> date(
            int position, String name, Constraint<LocalDate> constraint
    ) {
        return EzCol.newCol(
                position, name, constraint, new ToLocalDate(), DateValue::new
        );
    }

    /**
     * Create a new Date column.
     *
     * @param position   column position from left to right, starting from 0.
     * @param name       column name.
     * @param constraint column constraint.
     * @param transform  column transform.
     * @return {@link Column}
     */
    public static Column<LocalDate> date(
            int position, String name, Constraint<LocalDate> constraint,
            Transform<LocalDate> transform
    ) {
        return EzCol.newCol(
                position, name, constraint, transform, DateValue::new
        );
    }

    /**
     * Make a column nullable. It means that if the given expression is
     * found,  it will return a null.
     *
     * @param <T>            column type.
     * @param nullExpression null identifier.
     * @param column         nullable column.
     * @return column that can return null values.
     * @since 0.4.0
     */
    public static <T> Column<T> nullable(String nullExpression, Column<T> column) {
        return new Nullable<>(nullExpression, column);
    }

    /**
     * Generic column creation.
     *
     * @param position     column position from left to right, starting from 0.
     * @param name         column name.
     * @param constraint   column constraint.
     * @param transform    column transform.
     * @param valueFactory value factory.
     * @param <T>          depends on value type.
     * @return {@link Column}
     */
    private static <T> Column<T> newCol(
            int position, String name, Constraint<T> constraint,
            Transform<T> transform, ValueFactory<T> valueFactory
    ) {
        return new GenericColumn<>(
                position, name, constraint, transform, valueFactory
        );
    }
}
