package com.elbraulio.ezload.model.batch;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @todo do not know how to test prepared statements.
 * @body do not know how to check if the value was added.
 */

/**
 * Add {@link Integer} values to the {@link PreparedStatement}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class IntBatch implements AddBatch<Integer> {
    @Override
    public PreparedStatement addValue(
            PreparedStatement ps, int index, Integer value
    ) throws SQLException {
        ps.setInt(index, value);
        return ps;
    }
}
