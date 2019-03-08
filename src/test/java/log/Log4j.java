package log;

import com.elbraulio.ezload.logger.EzLogger;
import org.apache.log4j.Logger;

/**
 * Implementation for Log4j logs.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class Log4j implements EzLogger {
    @Override
    public void info(String msg, String clazz) {
        Logger.getLogger(clazz).info(msg);
    }

    @Override
    public void warning(String msg, String clazz) {
        Logger.getLogger(clazz).warn(msg);
    }

    @Override
    public void error(String msg, String clazz) {
        Logger.getLogger(clazz).error(msg);
    }
}
