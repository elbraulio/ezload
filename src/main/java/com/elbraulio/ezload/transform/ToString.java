package com.elbraulio.ezload.transform;

/**
 * Represents transform input to string.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class ToString implements Transform<String> {
    @Override
    public String from(String value) {
        return value;
    }
}
