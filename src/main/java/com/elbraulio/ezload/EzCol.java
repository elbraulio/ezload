package com.elbraulio.ezload;

import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.model.Constrain;
import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.model.batch.AddBatch;
import com.elbraulio.ezload.model.batch.IntBatch;
import com.elbraulio.ezload.model.batch.StringBatch;
import com.elbraulio.ezload.model.transform.Transform;

/**
 * {@link Column} factory. This design come from the fact that we need to
 * know what is the type of <code>value</code>.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
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
