package com.elbraulio.ezload.model.transform;

/**
 * Transform input to Integer.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class ToInt implements Transform<Integer> {
    @Override
    public Integer from(String value) {
        return Integer.parseInt(value);
    }
}
