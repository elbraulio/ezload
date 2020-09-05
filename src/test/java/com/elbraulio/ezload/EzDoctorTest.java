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

import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.error.EzError;
import com.elbraulio.ezload.parse.Parser;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link EzDoctor}.
 *
 * @author Braulio Lopez (elbraulio274@gmail.com)
 */
public class EzDoctorTest {

    private static final String[] DATA = new String[]{"speed,km/h", "weight,kg"};

    @Test
    public void hasNoErrors() {
        Parser parser = EzLoad.parse(",", 2)
                .withCol(EzCol.string(0, "name", new NoConstraint<>()))
                .withCol(EzCol.string(1, "value", new NoConstraint<>()))
                .parser();

        List<EzError> errors = EzDoctor.check(parser, Stream.of(DATA));
        assertEquals(0, errors.size());
    }

    @Test
    public void hasError() {
        Parser parser = EzLoad.parse(",", 2)
                .withCol(EzCol.string(0, "name", new NoConstraint<>()))
                .withCol(EzCol.string(1, "value", String::isEmpty))
                .parser();
        List<EzError> errors = EzDoctor.check(parser, Stream.of(DATA));
        assertEquals(2, errors.size());
    }

    @Test
    public void errorsSizeCantBeMoreThanMax() {
        Parser parser = EzLoad.parse(",", 2)
                .withCol(EzCol.string(0, "name", new NoConstraint<>()))
                .withCol(EzCol.string(1, "value", String::isEmpty))
                .parser();
        List<EzError> errors = EzDoctor.check(parser, Stream.of(DATA), 1);
        assertEquals(1, errors.size());
    }

    @Test
    public void messageErrorShouldIncludeDetails() {
        Parser parser = EzLoad.parse(",", 2)
                .withCol(EzCol.string(0, "name", new NoConstraint<>()))
                .withCol(EzCol.string(1, "value", s -> s.equals("kg")))
                .parser();
        List<EzError> errors = EzDoctor.check(parser, Stream.of(DATA));
        assertEquals(1, errors.size());
        EzError error = errors.get(0);
        assertEquals("error at row 0: [column 1 does not accept value 'km/h']", error.toString());
    }
}
