package com.elbraulio.ezload.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A Column represent the format that a value from a column should check.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Column<T> {

    /**
     * Column's value.
     *
     * @param value original value.
     * @return transformed value.
     */
    T value(String value);

    /**
     * Position from left to right of the column on a line separated by a
     * expression. From 0 to n.
     *
     * @return position number.
     */
    int order();

    /**
     * Column's name.
     *
     * @return name.
     */
    String name();

    /**
     * @todo this method should throw an exception
     * @body it is common that a validation check returns false, so what happens
     * @body if it is so? that is why it should throw an exception. Not sure
     * @body where, maybe when the File is loading.
     */
    /**
     * Checks if the value is valid or not.
     *
     * @param value raw value to check.
     * @return true if it is valid and false if it is not.
     */
    boolean isValid(String value);

    /**
     * Add a value to the {@link PreparedStatement} then returns it.
     *
     * @param ps    {@link PreparedStatement}
     * @param index index to add to {@link PreparedStatement}.
     * @param value value to add.
     * @return {@link PreparedStatement}
     * @throws SQLException {@link PreparedStatement} error.
     */
    PreparedStatement addToPreparedStatement(
            PreparedStatement ps, int index, T value
    ) throws SQLException;
}
