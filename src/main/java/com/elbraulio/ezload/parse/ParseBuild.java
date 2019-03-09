package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;

/**
 * Parser builder.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface ParseBuild {

    /**
     * Add new column to the set of columns.
     *
     * @param col {@link Column}.
     * @return builder.
     */
    ParseBuild withCol(Column col);

    /**
     * Build the parser.
     *
     * @return {@link Parser}
     */
    Parser parser();
}
