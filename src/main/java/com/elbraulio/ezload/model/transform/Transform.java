package com.elbraulio.ezload.model.transform;

/**
 * Transform a value depending on its type.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public interface Transform<T> {

    /**
     * Transform a value.
     *
     * @param value value to transform.
     * @return transformed value.
     */
    T from(String value);
}
