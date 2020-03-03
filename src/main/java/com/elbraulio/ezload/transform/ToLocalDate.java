package com.elbraulio.ezload.transform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents transform input to string.
 *
 * @author Braulio Lopez (elbraulio274@gmail.com)
 * @since 0.6.0
 */
public final class ToLocalDate implements Transform<LocalDate> {
    @Override
    public LocalDate from(String value) {
        return LocalDate.parse(
                value,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }
}
