package xyz.yansheng.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * description
 *
 * @author yansheng
 * @date 2021/07/10
 */
public class RenameFileTest {
    @Test
    public void testRenameFile() {
        String dir = "./1phone-smallskin-lol";
        File file = new File(dir);
        String[] files = file.list();
        System.out.println("files:" + files);
//        for (String fileName : files) {
//            System.out.println("fileName:" + fileName);
//        }

        for (int i = 0; i < files.length; i++) {
//        for (int i = 0; i < 10; i++) {
            String fileName = files[i];
            System.out.println("fileName:" + files[i]);

//            String pattern = "-[1-100]-";
//            String pattern = "^[0-9]\\d{0,1}$";
            String pattern = "-\\d+-";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);

            // 现在创建 matcher 对象
            Matcher m = r.matcher(fileName);
            // 注意这里使用了的话，会影响下面的if，每次find值都会不一样（默认只匹配一次）
            // assertTrue(m.find());
            if (m.find()) {
                System.out.println("Found value: " + m.group(0));
                String value = m.group(0);
                Integer index = Integer.parseInt(value.replaceAll("-", "")) + 1;
                value = "-" + index.toString() + "-";
                String newFileName = dir + "/" + fileName;
                System.out.println("newFileName:" + newFileName);
                System.out.println("newFileName:" + newFileName.replaceAll(pattern, value));
                // 重命名
                File oldFile = new File(newFileName);
                File newFile = new File(newFileName.replaceAll(pattern, value));
//                oldFile.renameTo(newFile);
            } else {
                System.out.println("NO MATCH");
            }

        }

    }

    @Test
    public void testRenameFile2() {
        String dir = "./5wallpaper-bigskin-lol";
        File file = new File(dir);
        String[] files = file.list();
        System.out.println("files:" + files);
//        for (String fileName : files) {
//            System.out.println("fileName:" + fileName);
//        }

        for (int i = 0; i < files.length; i++) {
//        for (int i = 0; i < 33; i++) {
//        for (int i = 0; i < 10; i++) {
            String fileName = files[i];
            System.out.println("fileName:" + files[i]);

//            String pattern = "-[1-100]-";
//            String pattern = "^[0-9]\\d{0,1}$";
            String pattern = "\\d+";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);

            // 现在创建 matcher 对象
            Matcher m = r.matcher(fileName);
            // 注意这里使用了的话，会影响下面的if，每次find值都会不一样（默认只匹配一次）
            // assertTrue(m.find());
            if (m.find()) {
                String value = m.group(0);
                System.out.println("Found value: " + value);
                Integer index = Integer.parseInt(value) + 1;
                value = index.toString();
                String newFileName = dir + "/" + fileName;
                System.out.println("oldFileName:" + newFileName);
                System.out.println("newFileName:" + newFileName.replace(m.group(0), value));
                // 重命名
                File oldFile = new File(newFileName);
                File newFile = new File(newFileName.replace(m.group(0), value));
                System.out.println();
//                oldFile.renameTo(newFile);
            } else {
                System.out.println("NO MATCH");
            }

        }

    }
}
