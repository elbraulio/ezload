package com.elbraulio.ezload.model;

/**
 * Checks if a value is valid or not depending on the type.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Constrain<T> {
    /**
     * Check if the value is valid or not.
     *
     * @param value value to check.
     * @return true if it is valid, false if it is not.
     */
    boolean isValid(T value);
}
