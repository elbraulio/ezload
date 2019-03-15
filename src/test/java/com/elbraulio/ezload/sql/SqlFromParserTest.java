package com.elbraulio.ezload.sql;

import com.elbraulio.ezload.model.GenericColumn;
import com.elbraulio.ezload.model.NoConstrain;
import com.elbraulio.ezload.model.batch.StringBatch;
import com.elbraulio.ezload.model.transform.ToString;
import com.elbraulio.ezload.parse.DefaultParseBuild;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class SqlFromParserTest {
    @Test
    public void emptyQuery() {
        MatcherAssert.assertThat(
                "empty queries doesn't make columns",
                new SqlFromParser(
                        "name", new DefaultParseBuild(null, null, 0
                ).parser()
                ).sql(),
                CoreMatchers.is("INSERT INTO name () VALUES ();")
        );
    }

    @Test
    public void singleColumn() {
        MatcherAssert.assertThat(
                "empty queries doesn't make columns",
                new SqlFromParser(
                        "name",
                        new DefaultParseBuild(
                                null, null, 0
                        ).withCol(
                                new GenericColumn<>(
                                        0, "first", new NoConstrain<>(),
                                        new ToString(), new StringBatch()
                                )
                        ).parser()
                ).sql(),
                CoreMatchers.is("INSERT INTO name (first) VALUES (?);")
        );
    }

    @Test
    public void multiColumns() {
        MatcherAssert.assertThat(
                "empty queries doesn't make columns",
                new SqlFromParser(
                        "name",
                        new DefaultParseBuild(
                                null, null, 0
                        ).withCol(
                                new GenericColumn<>(
                                        0, "first", new NoConstrain<>(),
                                        new ToString(), new StringBatch()
                                )
                        ).withCol(
                                new GenericColumn<>(
                                        1, "second", new NoConstrain<>(),
                                        new ToString(), new StringBatch()
                                )
                        ).parser()
                ).sql(),
                CoreMatchers.is(
                        "INSERT INTO name (first,second) VALUES (?,?);"
                )
        );
    }
}