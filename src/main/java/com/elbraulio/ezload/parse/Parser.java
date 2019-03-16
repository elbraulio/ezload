package com.elbraulio.ezload.parse;

import com.elbraulio.ezload.model.Column;

import java.util.List;

/**
 * Parse data from a given file.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Parser {

    /**
     * List of formatted columns.
     *
     * @return columns.
     */
    List<Column> columns();

    /**
     * Expression that defines how the data is separated in a single line.
     *
     * @return separation expression.
     */
    String separator();
}