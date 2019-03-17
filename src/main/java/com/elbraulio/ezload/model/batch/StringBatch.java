package com.elbraulio.ezload.model.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @todo do not know how to test prepared statements.
 * @body do not know how to check if the value was added.
 */

/**
 * Add {@link String} values to the {@link PreparedStatement}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class StringBatch implements AddBatch<String> {
    @Override
    public PreparedStatement addValue(
            PreparedStatement ps, int index, String value
    ) throws SQLException {
        ps.setString(index, value);
        return ps;
    }
}
