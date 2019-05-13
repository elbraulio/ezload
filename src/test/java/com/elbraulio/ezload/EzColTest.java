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

import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.transform.ToDouble;
import com.elbraulio.ezload.transform.ToInt;
import com.elbraulio.ezload.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for {@link EzCol}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class EzColTest {
    @Test
    public void intCol() {
        MatcherAssert.assertThat(
                "integer as integer",
                EzCol.integer(
                        0, "name", new NoConstraint<>(), new ToInt()
                ).parse("1"),
                CoreMatchers.is(1)
        );
    }

    @Test
    public void stringCol() {
        MatcherAssert.assertThat(
                "string as string",
                EzCol.string(
                        0, "name", new NoConstraint<>(), new ToString()
                ).parse("1"),
                CoreMatchers.is("1")
        );
    }

    @Test
    public void doubleCol() {
        MatcherAssert.assertThat(
                "double as double",
                EzCol.doublee(
                        0, "name", new NoConstraint<>(), new ToDouble()
                ).parse("1.8"),
                CoreMatchers.is(1.8)
        );
    }

    @Test
    public void nullableCol() {
        MatcherAssert.assertThat(
                "null col double as double",
                EzCol.nullable(
                        "NULL",
                        EzCol.doublee(
                                0, "name", new NoConstraint<>(), new ToDouble()
                        )
                ).parse("1.8"),
                CoreMatchers.is(1.8)
        );
    }

    @Test
    public void optionalIntCol() {
        MatcherAssert.assertThat(
                "integer as integer",
                EzCol.integer(
                        0, "name", new NoConstraint<>()
                ).parse("1"),
                CoreMatchers.is(1)
        );
    }

    @Test
    public void optionalStringCol() {
        MatcherAssert.assertThat(
                "string as string",
                EzCol.string(
                        0, "name", new NoConstraint<>()
                ).parse("1"),
                CoreMatchers.is("1")
        );
    }

    @Test
    public void optionalDoubleCol() {
        MatcherAssert.assertThat(
                "double as double",
                EzCol.doublee(
                        0, "name", new NoConstraint<>()
                ).parse("1.8"),
                CoreMatchers.is(1.8)
        );
    }
}