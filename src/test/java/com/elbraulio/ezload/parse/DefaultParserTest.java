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

package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.column.Column;
import com.elbraulio.ezload.column.GenericColumn;
import com.elbraulio.ezload.constrain.NoConstrain;
import com.elbraulio.ezload.transform.ToInt;
import com.elbraulio.ezload.value.IntValue;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for {@link DefaultParser}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultParserTest {
    @Test
    public void singleColumn() {
        final List<Column> list = new LinkedList<>();
        list.add(
                new GenericColumn<Integer>(
                        0, "col", new NoConstrain<>(),
                        new ToInt(), IntValue::new
                )
        );
        MatcherAssert.assertThat(
                "parser with one column",
                new DefaultParser(
                        ",", 0, list
                ).columns().get(0).parse("1"),
                CoreMatchers.is(1)
        );
    }
}