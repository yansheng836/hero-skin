package xyz.yansheng.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import xyz.yansheng.bean.Hero;
import xyz.yansheng.util.FileUtil;

/**
 * 下载最大的手机壁纸或者获取需要的json文件，用于hexo博客切换背景图片。
 * 
 * @author yansheng
 * @date 2019/11/22
 */
public class DownloadMobileskinWallpaper {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String seriPath = "seri_hero.txt";
        ArrayList<Hero> heros = SeriHero.deSeriHero(seriPath);
        int sum = 0;
        for (Hero hero : heros) {
            sum = sum + hero.getSkins().size();
        }
        System.out.println("到目前为止，王者荣耀一共有" + heros.size() + "个英雄，" + sum + "个皮肤（含伴生皮肤）。");

        // 3.下载图片:sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
        int sign = 5;

        // 方式1：下载图片
        String dir = "./wzry-mobile";
        FileUtil.mkdir(dir);
        int count1 = 0;
        for (Hero hero : heros) {
            List<String> urls = hero.getWallpaperMobileskinUrl();
            for (String imgUrl : urls) {
                count1++;
                String pathname = dir + "/" + count1 + ".jpg";
                 FileUtil.downloadImage(imgUrl, pathname);
            }
            if (count1 >= 10) {
                break;
            }
        }

        // 方式2：保存json文件
        count1 = 0;
        Map<String, String> imgMap = new LinkedHashMap<String, String>(365);
        for (Hero hero : heros) {
            List<String> urls = hero.getWallpaperMobileskinUrl();
            for (String imgUrl : urls) {
                count1++;
                imgMap.put(Integer.toString(count1), imgUrl);
            }
//            if (count1 >= 10) {
//                break;
//            }
        }

        // 遍历
        Iterator<Map.Entry<String, String>> iterator = imgMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        String jsonString = JSON.toJSONString(imgMap);
        System.out.println(jsonString);

        String pathname = "./wzry_mobile367.json";
        try {
            FileUtils.writeStringToFile(new File(pathname), jsonString, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
