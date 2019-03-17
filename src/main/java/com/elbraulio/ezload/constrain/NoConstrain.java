package com.elbraulio.ezload.constrain;

/**
 * Represent no constrain needed.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class NoConstrain<T> implements Constrain<T> {
    @Override
    public boolean isValid(T value) {
        return true;
    }
}
