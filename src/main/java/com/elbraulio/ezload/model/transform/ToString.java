package com.elbraulio.ezload.model.transform;

/**
 * Represents transform input to string.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class ToString implements Transform<String> {
    @Override
    public String from(String value) {
        return value;
    }
}
