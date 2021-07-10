package xyz.yansheng.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.*;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.junit.Test;

/**
 * description
 *
 * @author yansheng
 * @date 2021/6/27
 */
public class TestLog {

    @Test
    public void testLog() throws IOException {
//        Logger log = Logger.getLogger("tesglog");
        Logger log = Logger.getLogger(this.toString());
        log.setLevel(Level.ALL);
        FileHandler fileHandler = new FileHandler("testlog.log");
        fileHandler.setLevel(Level.ALL);
        // fileHandler.setFormatter(new LogFormatter());
        fileHandler.setFormatter(new Formatter() {// 定义一个匿名类
            @Override
            public String format(LogRecord record) {
//                return record.getLevel() + ":" + record.getMessage() + "\n";
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                String dateString =sdf.format(date);
                System.out.println(dateString);
//                return "[" + (new Date()).toString() + "]" + "[" + record.getLevel() + "]"
//                    + record.getClass() + record.getMessage() + "\n";
                return  dateString + " [" + record.getLevel() + "] "
                    + record.getLoggerName() + " : " + record.getMessage() + "\n"  ;
            }
        });
        fileHandler.setFormatter(new MyLogFormatter());
        log.addHandler(fileHandler);
        log.setUseParentHandlers(false);
        log.info("This is test java util log");
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        String logFileName = "";
        // string.isEmpty() 只能用于判断空字符串，不能用于判断null
        System.out.println(logFileName.isEmpty());
    }



}
