package com.elbraulio.ezload;

import com.elbraulio.ezload.parse.DefaultParseBuild;
import com.elbraulio.ezload.parse.ParseBuild;
import com.elbraulio.ezload.parse.Parser;
/**
 * @todo EzLoad needs test
 * @body Just for sake of time it can be included at this time but it is
 * @body necessary to do that.
 */

/**
 * Main API to use Ezload tool.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @since 1.0.0
 */
public final class EzLoad {
    /**
     * Create a {@link ParseBuild} to build a {@link Parser}.
     *
     * @param expression separator expression.
     * @param cols       number of columns.
     * @return {@link ParseBuild}.
     */
    static public ParseBuild parse(String expression, int cols) {
        return new DefaultParseBuild(expression, cols);
    }
}
