package com.elbraulio.ezload.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Add a value depending on the type.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public interface AddBatch<T> {
    PreparedStatement addValue(PreparedStatement ps, int index, T value)
            throws SQLException;
}
