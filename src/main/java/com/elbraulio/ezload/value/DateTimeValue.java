package com.elbraulio.ezload.value;

import com.elbraulio.ezload.action.Action;
import com.elbraulio.ezload.exception.EzException;

import java.time.LocalDateTime;

/**
 * DateTime values.
 *
 * @author Braulio Lopez (elbraulio274@gmail.com)
 * @since 0.5.0
 */
public final class DateTimeValue implements Value {

    private final LocalDateTime value;

    public DateTimeValue(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public void accept(Action action) throws EzException {
        action.execute(this.value);
    }
}
