package xyz.yansheng.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import xyz.yasnheng.bean.Hero;

/**
 * 文件工具类，作用：将爬取分类专栏的博客的数据以特定的格式写到文件中。
 * 
 * @author yansheng
 * @date 2019/10/12
 */
public class FileUtil {

    /**
     * 生成博客目录的列表文件（markdown格式）。遍历分类专栏，将每个分类专栏下的博客进行格式化输出，用StringBuffer进行拼接，最后转为String，写到文件中。
     * 
     * @param pathname
     *            文件名
     * @param categoryList
     *            分类专栏列表
     * @return 成功返回true,失败返回false
     */
    public static boolean generateCsdnList(String pathname, ArrayList<Hero> heros) {

        // JSONArray也可以
        // String jsonString = JSON.toJSONString(heros);
        // 保留空值
        String jsonString = JSON.toJSONString(heros, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty);
        System.out.println(jsonString);

        // String pathname = "./heros.json";
        File file = new File(pathname);
        try {
            FileUtils.writeStringToFile(file, jsonString, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 网址，文件名
    public static void downloadImage(String url,String pathname) {
        

    }
    // sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
    public static void downloadImages(Hero hero,int sign) {
        
     // 1.按照分析，创建对应文件夹
        // 1.1.https://game.gtimg.cn/images/yxzj/img201606/heroimg/518/518-smallskin-1.jpg
        // phone-smallskin-images 头像
        // phone-mobileskin-images 小屏手机图片
        // phone-bigskin-images 大屏手机图片
        // 1.2.https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/518/518-mobileskin-1.jpg
        // wallpaper-mobileskin-images 手机壁纸
        // wallpaper-bigskin-images 电脑壁纸
        switch (sign) {
            case 0:
                mkdir("phone-smallskin-images");
                mkdir("phone-smallskin-images");
                mkdir("phone-smallskin-images");
                mkdir("phone-smallskin-images");
                mkdir("phone-smallskin-images");
                break;

            default:
                break;
        }
        
        int id = hero.getEname();
        int count = hero.getSkins().size();
        
        String phonePrefix = "https://game.gtimg.cn/images/yxzj/img201606/heroimg/";
        String wallpaperPrefix = "https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/";

        for (int i = 0; i < count; i++) {
            // https://game.gtimg.cn/images/yxzj/img201606/heroimg/518/518-smallskin-1.jpg

            for (int j = 0; j < count; j++) {
                psi[j] = phonePrefix + id + "/" + id + "-smallskin-" + j + ".jpg";
            }
            for (int j = 0; j < count; j++) {
                psi[j] = phonePrefix + id + "/" + id + "-mobileskin-" + j + ".jpg";
            }
            for (int j = 0; j < count; j++) {
                psi[j] = phonePrefix + id + "/" + id + "-bigskin-" + j + ".jpg";
            }

            // https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/518/518-mobileskin-1.jpg
            for (int j = 0; j < count; j++) {
                psi[j] = wallpaperPrefix + id + "/" + id + "-mobileskin-" + j + ".jpg";
            }
            for (int j = 0; j < count; j++) {
                psi[j] = wallpaperPrefix + id + "/" + id + "-bigskin-" + j + ".jpg";
            }

        }

    }
    
    public static void mkdir(String pathname) {
        File file = new File(pathname);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * 生成当时的格式化的时间，用于拼接生成文件的文件名。
     * 
     * @return 格式化的时间字符串
     */
    public static String getDateString() {

        Date date = new Date();
        // 设置时间的显示格式
        String pattern = "yyyy-MM-dd.HH-mm-ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateString = sdf.format(date);

        return dateString;
    }

    /**
     * 将毫秒转为'需要的时间'，取决于毫秒转化后比较接近哪个单位（秒、分钟）。
     * 
     * @param time
     *            毫秒数
     * @return 转换的秒的字符串
     */
    public static String getSecondString(long time) {

        // 判断时间是否超过1分钟，如果没有超过显示为秒钟，如2000ms转为：02.000s；如果超过显示分钟60000ms转为：01.00m
        Long oneMinute = 60000L;
        // 设置时间的显示格式
        String pattern = null;
        boolean isMoreThenOneMinute = false;
        if (time < oneMinute) {
            pattern = "s.SSS";
        } else {
            pattern = "m.ss";
            isMoreThenOneMinute = true;
        }

        Date date = new Date(time);

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateString = sdf.format(date);

        // return dateString;
        return !isMoreThenOneMinute ? (dateString + "秒") : (dateString + "分钟");
    }

    /**
     * 打印ASCII的Goodbye，用于程序结尾。（来源：http://www.network-science.de/ascii/ ：Font: big Reflection: no
     * Adjustment: left Stretch: no Width: 80 Text: Goodbye!）
     * 
     * @return
     */
    public static String sayGoodbye() {

        String goodbye = "  _____                 _ _                _ \n"
            + " / ____|               | | |              | |\n"
            + "| |  __  ___   ___   __| | |__  _   _  ___| |\n"
            + "| | |_ |/ _ \\ / _ \\ / _` | '_ \\| | | |/ _ \\ |\n"
            + "| |__| | (_) | (_) | (_| | |_) | |_| |  __/_|\n"
            + " \\_____|\\___/ \\___/ \\__,_|_.__/ \\__, |\\___(_)\n"
            + "                                 __/ |       \n"
            + "                                |___/        ";

        return goodbye;
    }

    /**
     * 打印ASCII的Welcome，用于程序开头。
     * 
     * @return
     */
    public static String sayWelcome() {

        String welcome = "__          __  _                          _ \n"
            + "\\ \\        / / | |                        | |\n"
            + " \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___| |\n"
            + "  \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ |\n"
            + "   \\  /\\  /  __/ | (_| (_) | | | | | |  __/_|\n"
            + "    \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___(_)\n"
            + "                                             ";

        return welcome;
    }

}
