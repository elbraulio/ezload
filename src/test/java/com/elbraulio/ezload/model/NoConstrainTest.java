package com.elbraulio.ezload.model;

import com.elbraulio.ezload.constrain.NoConstrain;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class NoConstrainTest {
    @Test
    public void returnTrue() {
        MatcherAssert.assertThat(
                "always return true",
                new NoConstrain<>().isValid(false),
                CoreMatchers.is(true)
        );
    }

    @Test
    public void supportDifferentTypes() {
        MatcherAssert.assertThat(
                "No matters the type, it is the same result",
                new NoConstrain<>().isValid(1),
                CoreMatchers.is(new NoConstrain<>().isValid(false))
        );
    }
}