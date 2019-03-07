package com.elbraulio.ezload;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;


/**
 * Initial Test.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class HelloWorldTest {
    @Test
    public void greetingSaysHelloWorld() {
        MatcherAssert.assertThat(
                new HelloWorld().greeting(),
                CoreMatchers.is("Hello world!")
        );
    }
}