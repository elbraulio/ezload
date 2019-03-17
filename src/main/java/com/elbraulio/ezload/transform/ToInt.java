package com.elbraulio.ezload.transform;

/**
 * Transform input to Integer.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class ToInt implements Transform<Integer> {
    @Override
    public Integer from(String value) {
        return Integer.parseInt(value);
    }
}
