package com.elbraulio.ezload.logger;

import com.elbraulio.ezload.model.transform.ToInt;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * {@link NoLog} should not print any logs to console.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class NoLogTest {
    @Test
    public void noInfoLogs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        new NoLog().info("this should not appear", "test");
        System.out.flush();
        System.setOut(old);
        MatcherAssert.assertThat(
                "info logs should not be printed",
                baos.toString(), CoreMatchers.is("")
        );
    }

    @Test
    public void noWarningLogs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        new NoLog().warning("this should not appear", "test");
        System.out.flush();
        System.setOut(old);
        MatcherAssert.assertThat(
                "warning logs should not be printed",
                baos.toString(), CoreMatchers.is("")
        );
    }

    @Test
    public void noErrorLogs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        new NoLog().error("this should not appear", "test");
        System.out.flush();
        System.setOut(old);
        MatcherAssert.assertThat(
                "error logs should not be printed",
                baos.toString(), CoreMatchers.is("")
        );
    }
}