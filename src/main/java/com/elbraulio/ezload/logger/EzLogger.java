/*
 * MIT License
 *
 * Copyright (c) 2019 Braulio López
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.elbraulio.ezload.logger;

/**
 * Represent the logger that ezload will use. Then, if the user wants to log
 * what ezload logs, the user must implement this EzLogger with any log tool.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
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
