package xyz.yansheng.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xyz.yansheng.bean.Hero;
import xyz.yansheng.util.FileUtil;
import xyz.yansheng.util.SpiderUtil;

/**
 * 下载最大的桌面壁纸
 * 
 * @author yansheng
 * @date 2019/11/22
 */
public class DownloadBigskinWallpaper {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 1.从英雄列表页爬取英雄基本数据（id,ename,cname,heroUrl）
        // 在线数据不实时，好像加了js，暂时不会爬取；先使用爬取下载的本地网页
        String url = "https://pvp.qq.com/web201605/herolist.shtml";
        String localUrl = "./英雄资料列表页-英雄介绍-王者荣耀官方网站-腾讯游戏.html";

        ArrayList<Hero> heros = SpiderUtil.getHeros(localUrl, SpiderUtil.GBK);
        int size = heros.size();
        // for (Hero hero : heros) {
        // System.out.println(hero.toString());
        // }

        // 2.从每个英雄主页heroUrl中获取英雄的皮肤信息（title，skinName，skins）
        int count = 0;
        for (Hero hero : heros) {
            SpiderUtil.getHeroSkins(hero);
            hero.generateField();
            // System.out.println(hero.toString());
            System.out.println(hero.toStringSimple());
            count++;
            if (count == 2) {
                // break;
            }
        }
        int sum = 0;
        for (Hero hero : heros) {
            sum = sum + hero.getSkins().size();
        }
        System.out.println("到目前为止，王者荣耀一共有" + heros.size() + "个英雄，" + sum + "个皮肤（含伴生皮肤）。");

        // 3.下载图片:sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
        int sign = 5;

        // 方式1：下载图片
        String dir = "./wzry";
        FileUtil.mkdir(dir);
        int count1 = 0;
        for (Hero hero : heros) {
            List<String> urls = hero.getWallpaperBigskinUrl();
            for (String imgUrl : urls) {
                count1++;
                if (count1 == 10) {
//                    return;
                }
                String pathname = dir + "/" + count1 + ".jpg";
                // FileUtil.downloadImage(imgUrl, pathname);
            }
        }

        // 方式2：保存json文件
        count1 = 0;
        Map<String, String> imgMap = new HashMap<String, String>(365);
        for (Hero hero : heros) {
            List<String> urls = hero.getWallpaperBigskinUrl();
            for (String imgUrl : urls) {
                count1++;
                if (count1 == 10) {
                    return;
                }
                imgMap.put(Integer.toString(count1), imgUrl);
            }
        }

        Iterator<Map.Entry<String, String>> iterator = imgMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

    }

}
