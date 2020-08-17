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

package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.column.GenericColumn;
import com.elbraulio.ezload.constraint.NoConstraint;
import com.elbraulio.ezload.transform.ToInt;
import com.elbraulio.ezload.value.IntValue;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for {@link DefaultParseBuild}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultParseBuildTest {
    @Test
    public void noColumns() {
        MatcherAssert.assertThat(
                "no columns, has 0 info about columns",
                new DefaultParseBuild("", 0)
                        .parser().columns().size(),
                CoreMatchers.is(0)
        );
    }

    @Test
    public void withCols() {
        MatcherAssert.assertThat(
                "2 columns, has 2 info about columns",
                new DefaultParseBuild("", 2)
                        .withCol(
                                new GenericColumn<>(
                                        0, "", new NoConstraint<>(),
                                        new ToInt(), IntValue::new
                                )
                        )
                        .withCol(
                                new GenericColumn<>(
                                        0, "", new NoConstraint<>(),
                                        new ToInt(), IntValue::new
                                )
                        )
                        .parser().columns().size(),
                CoreMatchers.is(2)
        );
    }
}
