package com.elbraulio.ezload.value;

import com.elbraulio.ezload.action.Action;
import com.elbraulio.ezload.exception.EzException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Unit test for {@link com.elbraulio.ezload.value.DateTimeValue}.
 *
 * @author Braulio Lopez(brauliop.3@gmail.com)
 */
public class DateTimeValueTest {
    @Test
    public void matchDateTime() {
        try {
            new DateTimeValue(LocalDateTime.now()).accept(
                    new Action() {
                        @Override
                        public void execute(Integer value) throws EzException {
                            fail();
                        }

                        @Override
                        public void execute(Double value) throws EzException {
                            fail();
                        }

                        @Override
                        public void execute(String value) throws EzException {
                            fail();
                        }

                        @Override
                        public void execute(LocalDateTime value) throws EzException {
                            assertTrue(true);
                        }

                        @Override
                        public void execute(LocalDate value) throws EzException {
                            fail();
                        }
                    }
            );
        } catch (EzException e) {
            e.printStackTrace();
        }
    }
}
