package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.model.NoConstrain;
import com.elbraulio.ezload.model.batch.IntBatch;
import com.elbraulio.ezload.model.transform.ToInt;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultParserTest {
    @Test
    public void singleColumn() {
        final List<Column> list = new LinkedList<>();
        list.add(
                new GenericColumn<Integer>(
                        0, "col", new NoConstrain<>(),
                        new ToInt(), new IntBatch()
                )
        );
        MatcherAssert.assertThat(
                "parser with one column",
                new DefaultParser(
                        ",", 0, list
                ).columns().get(0).value("1"),
                CoreMatchers.is(1)
        );
    }
}