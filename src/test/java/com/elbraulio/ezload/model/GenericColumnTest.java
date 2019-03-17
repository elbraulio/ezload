package com.elbraulio.ezload.model;

import com.elbraulio.ezload.batch.StringBatch;
import com.elbraulio.ezload.constrain.NoConstrain;
import com.elbraulio.ezload.transform.ToString;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GenericColumnTest {

    private final Column<String> col = new GenericColumn<>(
            0, "column_name",
            new NoConstrain<>(), new ToString(),
            new StringBatch()
    );

    @Test
    public void plainTextValueWithNoConstrains() {
        MatcherAssert.assertThat(
                "a plain text with no constrains and transformations" +
                        "should keep the same",
                col.value("plain text"),
                CoreMatchers.is("plain text")
        );
    }

    @Test
    public void orderNotChange() {
        MatcherAssert.assertThat(
                "order always is the same",
                col.order(),
                CoreMatchers.is(0)
        );
    }

    @Test
    public void nameNotChange() {
        MatcherAssert.assertThat(
                "name always is the same",
                col.name(),
                CoreMatchers.is("column_name")
        );
    }

    @Test
    public void validationTrueWithNoConstrains() {
        MatcherAssert.assertThat(
                "always is valid",
                col.isValid("some input"),
                CoreMatchers.is(true)
        );
    }
}