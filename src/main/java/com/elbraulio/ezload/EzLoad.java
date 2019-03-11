package com.elbraulio.ezload;

import com.elbraulio.ezload.parse.DefaultParseBuild;
import com.elbraulio.ezload.parse.ParseBuild;
import com.elbraulio.ezload.parse.Parser;

import java.io.File;
/**
 * @todo EzLoad needs test
 * @body Just for sake of time it can be included at this time but it is
 * @body necessary to do that.
 */

/**
 * Main API to use Ezload tool.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class EzLoad {
    /**
     * Create a {@link ParseBuild} to build a {@link Parser}.
     *
     * @param file       file to read.
     * @param expression separator expression.
     * @param cols       number of columns.
     * @return {@link ParseBuild}.
     */
    static public ParseBuild parse(File file, String expression, int cols) {
        return new DefaultParseBuild(file, expression, cols);
    }
}
