package com.elbraulio.ezload.value;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.elbraulio.ezload.action.Action;
import com.elbraulio.ezload.exception.EzException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;


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
                    public void execute(Integer value) {
                        fail();
                    }

                    @Override
                    public void execute(Double value) {
                        fail();
                    }

                    @Override
                    public void execute(String value) {
                        fail();
                    }

                    @Override
                    public void execute(LocalDateTime value) {
                        assertTrue(true);
                    }

                    @Override
                    public void execute(LocalDate value) {
                        fail();
                    }
                }
            );
        } catch (EzException e) {
            e.printStackTrace();
        }
    }
}
