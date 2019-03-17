package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.constrain.NoConstrain;
import com.elbraulio.ezload.batch.IntBatch;
import com.elbraulio.ezload.transform.ToInt;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultParseBuildTest {
    @Test
    public void noColumns() {
        MatcherAssert.assertThat(
                "no columns, has 0 info about columns",
                new DefaultParseBuild("", 0)
                        .parser().columns().size(),
                CoreMatchers.is(0)
        );
    }

    @Test
    public void withCols() {
        MatcherAssert.assertThat(
                "2 columns, has 2 info about columns",
                new DefaultParseBuild("", 2)
                        .withCol(
                                new GenericColumn<>(
                                        0, "", new NoConstrain<>(),
                                        new ToInt(), new IntBatch()
                                )
                        )
                        .withCol(
                                new GenericColumn<>(
                                        0, "", new NoConstrain<>(),
                                        new ToInt(), new IntBatch()
                                )
                        )
                        .parser().columns().size(),
                CoreMatchers.is(2)
        );
    }
}