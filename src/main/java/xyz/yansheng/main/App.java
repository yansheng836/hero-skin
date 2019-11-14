package xyz.yansheng.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import xyz.yansheng.bean.Hero;
import xyz.yansheng.util.FileUtil;
import xyz.yansheng.util.SpiderUtil;

/**
 * @author yansheng
 * @date 2019/11/13
 */
public class App {
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

        // 3.将数据写到json中
        // JSON,JSONArray也可以
        // String jsonString = JSON.toJSONString(heros);
        // 保留空值

        Map<String, Object> map = new HashMap<String, Object>(5);
        map.put("description", "该文件用于保存王者荣耀的英雄皮肤的相关信息");
        map.put("phone-smallskin-images", "头像67*67");
        map.put("phone-mobileskin-images", "小屏手机图片600*410");
        map.put("phone-bigskin-images", "大屏手机图片1200*530");
        map.put("wallpaper-mobileskin-images", "手机壁纸727*1071");
        map.put("wallpaper-bigskin-images", "电脑壁纸1920*882");
        map.put("hero-list", heros);

        String jsonString = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty);
        // System.out.println(jsonString);

        String pathname = "./heros1.json";
        File file = new File(pathname);
        try {
            FileUtils.writeStringToFile(file, jsonString, SpiderUtil.UTF8);
            System.out.println("写数据到json成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4.下载图片:sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
        int sign = 0;

        List<String> dirs = FileUtil.mkdir(sign);
        for (String dir : dirs) {
            FileUtil.downloadImages(heros, dir);
        }

    }

}
