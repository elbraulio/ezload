package com.elbraulio.ezload.sql;

import com.elbraulio.ezload.exception.EzException;
import com.elbraulio.ezload.model.Column;
import com.elbraulio.ezload.parse.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*
@todo how to test prepared statements on InsertFromParser
@body how can it be tested?
 */

/**
 * This implementation build a SQL query from a {@link Parser}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class InsertFromParser implements Insert {

    private final Parser parser;
    private final BuildSql buildSql;

    /**
     * Ctor.
     *
     * @param name   table name.
     * @param parser parser with format.
     */
    public InsertFromParser(String name, Parser parser) {
        this(parser, new SqlFromParser(name, parser));
    }

    /**
     * Ctor.
     *
     * @param parser   parser with format.
     * @param buildSql sql builder.
     */
    public InsertFromParser(Parser parser, BuildSql buildSql) {
        this.parser = parser;
        this.buildSql = buildSql;
    }

    /*
    @todo change Insert:execute buffered reader by a EzReader
    @body giving the BufferedReader implies that it always reads by line. It
    @body will be much better if the user can decide the way that the source
    @body is read.
     */
    @Override
    public int[] execute(Connection connection, BufferedReader bufferedReader)
            throws EzException {
        try (
                PreparedStatement psmt = connection.prepareStatement(
                        this.buildSql.sql()
                )
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] split = line.split(this.parser.separator());
                int index = 1;
                for (Column col : this.parser.columns()) {
                    /*
                    @todo check if order is on bounds
                    @body this is not checking if the index actually exists.
                     */
                    final String raw = split[col.order()];
                    if (col.isValid(raw)) {
                        col.addToPreparedStatement(psmt, index++, raw);
                    }
                }
                psmt.addBatch();
            }
            /*
            @todo unique executeBatch can produce memory issues
            @body what if the source file is giant? It can be stored on memory
            @body until all the data goes together to the data base. It will
            @body useful that the user could customize the chunk size to save
            @body to data base according his available memory.
             */
            return psmt.executeBatch();
            /*
            @todo add logs for testing
            @body no classes has logs, for instance this exception will never
            @body be thrown.
             */
        } catch (SQLException e) {
            throw new EzException("Sql error", e);
        } catch (IOException e) {
            throw new EzException("IO error", e);
        }
    }
}
