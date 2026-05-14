package com.elbraulio.ezload.transform;

import java.time.LocalDate;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for {@link ToLocalDate}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ToLocalDateTest {
    @Test
    public void doNotChangeTheOriginalValue() {
        MatcherAssert.assertThat(
            "transform raw value to LocalDate",
            new ToLocalDate().from("1991-04-27"),
            CoreMatchers.is(LocalDate.of(1991, 4, 27))
        );
    }
}
