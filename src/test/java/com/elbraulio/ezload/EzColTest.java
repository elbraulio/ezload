package com.elbraulio.ezload;

import com.elbraulio.ezload.model.NoConstrain;
import com.elbraulio.ezload.model.transform.ToInt;
import com.elbraulio.ezload.model.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class EzColTest {
    @Test
    public void intCol() {
        MatcherAssert.assertThat(
                "integer as integer",
                EzCol.integer(
                        0, "name", new NoConstrain<>(), new ToInt()
                ).value("1"),
                CoreMatchers.is(1)
        );
    }

    @Test
    public void stringCol() {
        MatcherAssert.assertThat(
                "string as string",
                EzCol.string(
                        0, "name", new NoConstrain<>(), new ToString()
                ).value("1"),
                CoreMatchers.is("1")
        );
    }
}