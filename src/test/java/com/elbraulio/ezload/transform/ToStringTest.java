package com.elbraulio.ezload.transform;

import com.elbraulio.ezload.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for {@link ToString}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ToStringTest {
    @Test
    public void doNotChangeTheOriginalValue() {
        MatcherAssert.assertThat(
                "always return the same value",
                new ToString().from("false"),
                CoreMatchers.is("false")
        );
    }
}