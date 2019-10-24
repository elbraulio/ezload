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

package com.elbraulio.ezload.value;

import com.elbraulio.ezload.action.Action;
import com.elbraulio.ezload.exception.EzException;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit test for {@link com.elbraulio.ezload.value.DoubleValue}.
 *
 * @author Braulio Lopez(brauliop.3@gmail.com)
 */

public class DoubleValueTest {
    @Test
    public void matchDouble() {
        try {
            new DoubleValue(4.0).accept(
                    new Action() {
                        @Override
                        public void execute(Integer value) throws EzException {
                            fail();
                        }

                        @Override
                        public void execute(Double value) throws EzException {
                            assertTrue(true);
                        }

                        @Override
                        public void execute(String value) throws EzException {
                            fail();
                        }

                        @Override
                        public void execute(LocalDateTime value) throws EzException {
                            fail();
                        }
                    }
            );
        } catch (EzException e) {
            e.printStackTrace();
        }
    }
}