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

package com.elbraulio.ezload.doctor;

import com.elbraulio.ezload.error.EzError;
import com.elbraulio.ezload.error.RowError;
import com.elbraulio.ezload.exception.EzParseException;
import com.elbraulio.ezload.parse.Parser;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
        return data.flatMap(line -> getLineErrors(line, row.getAndIncrement()))
                .limit(errorsLimit)
                .collect(toList());
    }

    private Stream<EzError> getLineErrors(String line, int row) {
        try {
            this.parser.parse(line);
        } catch (EzParseException e) {
            return Stream.of(new RowError(row, e.errors()));
        }
        return Stream.empty();
    }
}
