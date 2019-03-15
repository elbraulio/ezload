package com.elbraulio.ezload;

import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.parse.Parser;
import com.elbraulio.ezload.sql.InsertFromParser;

import java.io.BufferedReader;
import java.sql.Connection;
/*
@todo test EzInsert
@body this API should be tested.
 */

/**
 * Factory to insert to a data base from EzLoad tools.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class EzInsert {

    /**
     * Insert a source to a data base.
     *
     * @param connection     connection to data base.
     * @param table          table name to insert.
     * @param parser         source format.
     * @param bufferedReader read the source.
     * @return an array of update counts containing one element for each
     * command in the batch.  The elements of the array are ordered according
     * to the order in which commands were added to the batch.
     * @throws EzException EzLoad error.
     */
    public static int[] fromParser(
            Connection connection, String table, Parser parser,
            BufferedReader bufferedReader
    ) throws EzException {
        return new InsertFromParser(table, parser)
                .execute(connection, bufferedReader);
    }
}
