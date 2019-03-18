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

package com.elbraulio.ezload.sql;

import com.elbraulio.ezload.batch.StringBatch;
import com.elbraulio.ezload.constrain.NoConstrain;
import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.parse.DefaultParseBuild;
import com.elbraulio.ezload.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for {@link SqlFromParser}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class SqlFromParserTest {
    @Test
    public void emptyQuery() {
        MatcherAssert.assertThat(
                "empty queries doesn't make columns",
                new SqlFromParser(
                        "name",
                        new DefaultParseBuild(null, 0
                        ).parser()
                ).sql(),
                CoreMatchers.is("INSERT INTO name () VALUES ();")
        );
    }

    @Test
    public void singleColumn() {
        MatcherAssert.assertThat(
                "empty queries doesn't make columns",
                new SqlFromParser(
                        "name",
                        new DefaultParseBuild(
                                null, 0
                        ).withCol(
                                new GenericColumn<>(
                                        0, "first", new NoConstrain<>(),
                                        new ToString(), new StringBatch()
                                )
                        ).parser()
                ).sql(),
                CoreMatchers.is("INSERT INTO name (first) VALUES (?);")
        );
    }

    @Test
    public void multiColumns() {
        MatcherAssert.assertThat(
                "empty queries doesn't make columns",
                new SqlFromParser(
                        "name",
                        new DefaultParseBuild(
                                null, 0
                        ).withCol(
                                new GenericColumn<>(
                                        0, "first", new NoConstrain<>(),
                                        new ToString(), new StringBatch()
                                )
                        ).withCol(
                                new GenericColumn<>(
                                        1, "second", new NoConstrain<>(),
                                        new ToString(), new StringBatch()
                                )
                        ).parser()
                ).sql(),
                CoreMatchers.is(
                        "INSERT INTO name (first,second) VALUES (?,?);"
                )
        );
    }
}