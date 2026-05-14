package com.elbraulio.ezload.value;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.elbraulio.ezload.action.Action;
import com.elbraulio.ezload.exception.EzException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;


/**
 * Unit test for {@link com.elbraulio.ezload.value.DateValue}.
 *
 * @author Braulio Lopez(brauliop.3@gmail.com)
 */
public class DateValueTest {
    @Test
    public void matchDate() {
        try {
            new DateValue(LocalDate.now()).accept(
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
                        fail();
                    }

                    @Override
                    public void execute(LocalDate value) {
                        assertTrue(true);
                    }
                }
            );
        } catch (EzException e) {
            fail();
        }
    }
}
