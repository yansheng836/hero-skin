package xyz.yansheng.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志工具类
 *
 * @author yansheng
 * @date 2021/6/27
 */
public class LogUtil {

    /**
     * 日志级别
     */
    public static final String SEVERE = "SEVERE";
    public static final String WARNING = "WARNING";
    public static final String INFO = "INFO";
    public static final String CONFIG = "CONFIG";
    public static final String FINE = "FINE";
    public static final String FINER = "FINER";
    public static final String FINEST = "FINEST";

    public static void writeLog(String className, String logFileName, String level, String logString) {
        Logger log = Logger.getLogger(className);
        log.setLevel(Level.ALL);
        // 不向 控制台 输出日志，默认为true
        log.setUseParentHandlers(false);
        FileHandler fileHandler = null;
        try {
            // append : 默认false，如果是同一个文件，覆盖而不是添加；这里设为 true 添加
            fileHandler = new FileHandler(logFileName, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new MyLogFormatter());
            log.addHandler(fileHandler);
            /*
             * @see java.util.logging.Level
             * 
             * <li>SEVERE (highest value)
             * <li>WARNING
             * <li>INFO
             * <li>CONFIG
             * <li>FINE
             * <li>FINER
             * <li>FINEST  (lowest value)
             */
            switch (level) {
                case "SEVERE":
                    log.severe(logString);
                    break;
                case "WARNING":
                    log.warning(logString);
                    break;
                case "INFO":
                    log.info(logString);
                    break;
                case "CONFIG":
                    log.config(logString);
                    break;
                case "FINE":
                    log.fine(logString);
                    break;
                case "FINER":
                    log.finer(logString);
                    break;
                case "FINEST":
                    log.finest(logString);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + level);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 如果不关闭，会产生 .LCK 文件（可能会很多）
            if (fileHandler != null) {
                fileHandler.close();
            }
        }
    }
}
