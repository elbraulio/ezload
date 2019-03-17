package com.elbraulio.ezload.sql;

import com.elbraulio.ezload.exception.EzException;

import java.io.BufferedReader;
import java.sql.Connection;

/**
 * Represent the action of executing the store process.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public interface Insert {

    /*
    @todo Insert method can execute large batch
    @body it is possible to execute large batch and it returns `long[]` array.
    @body But this does not allow to do that. It it fixed to `int[]`.
     */

    /**
     * Executes the insert statement to the given <code>connection</code>
     * form the <code>bufferedReader</code>.
     *
     * @param connection     connection to data base.
     * @param bufferedReader source reader.
     * @return an array of update counts containing one element for each
     * command in the batch.  The elements of the array are ordered according
     * to the order in which commands were added to the batch.
     * @throws EzException EzLoad error.
     */
    int[] execute(Connection connection, BufferedReader bufferedReader)
            throws EzException;
}
