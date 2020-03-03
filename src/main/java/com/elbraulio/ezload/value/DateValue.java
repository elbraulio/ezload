package com.elbraulio.ezload.value;

import com.elbraulio.ezload.action.Action;
import com.elbraulio.ezload.exception.EzException;

import java.time.LocalDate;

/**
 * Date values.
 *
 * @author Braulio Lopez (elbraulio274@gmail.com)
 * @since 0.5.0
 */
public final class DateValue implements Value {

    private final LocalDate value;

    public DateValue(LocalDate value) {
        this.value = value;
    }

    @Override
    public void accept(Action action) throws EzException {
        action.execute(this.value);
    }
}
