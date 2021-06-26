package xyz.yansheng.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * description
 *
 * @author yansheng
 * @date 2021/6/27
 */
public class MyLogFormatter extends Formatter {
    /**
     * Format the given log record and return the formatted string.
     * <p>
     * The resulting formatted String will normally include a localized and formatted version of the LogRecord's message
     * field. It is recommended to use the {@link Formatter#formatMessage} convenience method to localize and format the
     * message field.
     *
     * @param record
     *            the log record to be formatted.
     * @return the formatted log record
     */
    @Override
    public String format(LogRecord record) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        // System.out.println(dateString);
        return LocalDateTime.now() + " [" + record.getLevel() + "] " + record.getLoggerName() + " : " + record.getMessage()
            + "\n";
    }
}
