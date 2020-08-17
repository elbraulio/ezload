package com.elbraulio.ezload.transform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents transform input to string.
 *
 * @author Braulio Lopez (elbraulio274@gmail.com)
 * @since --
 */
public final class ToLocalDateTime implements Transform<LocalDateTime> {
    @Override
    public LocalDateTime from(String value) {
        return LocalDateTime.parse(
                value,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        );
    }
}
