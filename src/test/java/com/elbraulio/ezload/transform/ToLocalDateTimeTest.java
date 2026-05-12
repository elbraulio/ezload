package com.elbraulio.ezload.transform;

import java.time.LocalDateTime;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for {@link ToLocalDateTime}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ToLocalDateTimeTest {
    @Test
    public void doNotChangeTheOriginalValue() {
        MatcherAssert.assertThat(
            "transform raw value to LocalDateTime",
            new ToLocalDateTime().from("1991-04-27T23:24:25.000000"),
            CoreMatchers.is(LocalDateTime.of(1991, 4, 27, 23, 24, 25))
        );
    }
}
