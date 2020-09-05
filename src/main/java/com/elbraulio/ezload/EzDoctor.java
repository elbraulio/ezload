package com.elbraulio.ezload;

import com.elbraulio.ezload.doctor.DefaultDoctor;
import com.elbraulio.ezload.error.EzError;
import com.elbraulio.ezload.parse.Parser;

import java.util.List;
import java.util.stream.Stream;

/**
 * Doctor for parsed csv files.
 *
 * @author Braulio Lopez (elbraulio274@gmail.com)
 * @since --
 */
public final class EzDoctor {

    /**
     * Checks if a stream of data fits the parser's conditions.
     *
     * @param parser parser with data model.
     * @param data   stream of data.
     * @return list of errors.
     */
    public static List<EzError> check(Parser parser, Stream<String> data) {
        return check(parser, data, Integer.MAX_VALUE);
    }

    /**
     * Checks if a stream of data fits the parser's conditions. Includes a limit for number of errors.
     *
     * @param parser      parser with data model.
     * @param data        stream of data.
     * @param errorsLimit maximum number of errors to return.
     * @return list of errors. With a maximum of <code>errorsLimit</code> errors.
     */
    public static List<EzError> check(Parser parser, Stream<String> data, int errorsLimit) {
        return new DefaultDoctor(parser).check(data, errorsLimit);
    }
}
