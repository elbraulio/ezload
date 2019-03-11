package com.elbraulio.ezload.model;

/**
 * Represent no constrain needed.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class NoConstrain<T> implements Constrain<T> {
    @Override
    public boolean isValid(T value) {
        return true;
    }
}
