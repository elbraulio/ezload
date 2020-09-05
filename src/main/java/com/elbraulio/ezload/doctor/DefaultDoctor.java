package com.elbraulio.ezload.doctor;

import com.elbraulio.ezload.error.EzError;
import com.elbraulio.ezload.error.RowError;
import com.elbraulio.ezload.exception.EzParseException;
import com.elbraulio.ezload.parse.Parser;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Validates data model using a given Parser.
 *
 * @author elbraulio274@gmail.com
 * @since --
 */
public final class DefaultDoctor {

    private final Parser parser;

    public DefaultDoctor(Parser parser) {
        this.parser = parser;
    }

    /**
     * Checks if a stream of data fits the parser's conditions. Includes a limit for number of errors.
     *
     * @param data        stream of data.
     * @param errorsLimit maximum number of errors to return.
     * @return list of errors. With a maximum of <code>errorsLimit</code> errors.
     */
    public List<EzError> check(Stream<String> data, int errorsLimit) {
        AtomicInteger row = new AtomicInteger();
        return data.flatMap(line -> checkLineErrors(line, row.getAndIncrement()))
                .limit(errorsLimit)
                .collect(Collectors.toList());
    }

    private Stream<EzError> checkLineErrors(String line, int row) {
        try {
            this.parser.parse(line);
        } catch (EzParseException e) {
            return Stream.of(new RowError(row, e.errors()));
        }
        return Stream.empty();
    }
}
