package com.elbraulio.ezload.model;

import com.elbraulio.ezload.model.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
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