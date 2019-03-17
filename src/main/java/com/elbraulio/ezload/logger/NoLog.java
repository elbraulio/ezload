package com.elbraulio.ezload.logger;

/**
 * Empty implementation for production release.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class NoLog implements EzLogger {
    @Override
    public void info(String msg, String clazz) {
        // no logs
    }

    @Override
    public void warning(String msg, String clazz) {
        // no logs
    }

    @Override
    public void error(String msg, String clazz) {
        // no logs
    }
}
