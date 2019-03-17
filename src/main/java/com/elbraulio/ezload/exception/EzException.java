package com.elbraulio.ezload.exception;

/**
 * Exception for ezload tool.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class EzException extends Exception {

    /**
     * Ctor.
     *
     * @param msg exception message.
     */
    public EzException(String msg) {
        super(msg);
    }

    /**
     * Ctor.
     *
     * @param msg       exception message.
     * @param throwable chained exception.
     */
    public EzException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
