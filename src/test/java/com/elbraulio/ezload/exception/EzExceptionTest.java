package com.elbraulio.ezload.exception;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class EzExceptionTest {
    @Test
    public void keepMessage() {
        MatcherAssert.assertThat(
                "should keep the initial message",
                new EzException("a message").getMessage(),
                CoreMatchers.is("a message")
        );
    }

    @Test
    public void keepChainedExceptionMessage() {
        MatcherAssert.assertThat(
                "chain an exception and keep its message",
                new EzException("message 2", new Exception("message 1"))
                        .getMessage(),
                CoreMatchers.is("message 2")
        );
    }
}