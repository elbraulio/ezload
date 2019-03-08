package com.elbraulio.ezload.logger;

/**
 * Represent the logger that ezload will use. Then, if the user wants to log
 * what ezload logs, the user must implement this EzLogger with any log tool.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface EzLogger {
    /**
     * Print an info message.
     *
     * @param msg   message to print.
     * @param clazz class name to identify the message.
     */
    void info(String msg, String clazz);

    /**
     * Print an warning message.
     *
     * @param msg   message to print.
     * @param clazz class name to identify the message.
     */
    void warning(String msg, String clazz);

    /**
     * Print an error message.
     *
     * @param msg   message to print.
     * @param clazz class name to identify the message.
     */
    void error(String msg, String clazz);
}
