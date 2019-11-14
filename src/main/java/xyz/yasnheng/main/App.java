package xyz.yasnheng.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import xyz.yansheng.util.FileUtil;
import xyz.yansheng.util.SpiderUtil;
import xyz.yasnheng.bean.Hero;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        // 1.从英雄列表页爬取英雄基本数据（id,ename,cname,heroUrl）
        // 在线数据不实时，好像加了js，暂时不会爬取；先使用爬取下载的本地网页
        String url = "https://pvp.qq.com/web201605/herolist.shtml";
        String localUrl = "./英雄资料列表页-英雄介绍-王者荣耀官方网站-腾讯游戏.html";

        ArrayList<Hero> heros = SpiderUtil.getHeros(localUrl, SpiderUtil.GBK);
        int size = heros.size();
        System.out.println("英雄数量：" + size);
        // for (Hero hero : heros) {
        // System.out.println(hero.toString());
        // }
        // for (int i = 0; i < size; i++) {
        //
        // }

        // 2.从每个英雄主页heroUrl中获取英雄的皮肤信息（title，skinName，skins）
        int count = 0;
        for (Hero hero : heros) {
            SpiderUtil.getHeroSkins(hero);
            hero.generateField();
            System.out.println(hero.toString());
            count++;
            if (count == 2) {
//                break;
            }
        }

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
        map.put("hero-list", heros.toString());

        String jsonString = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty);
        System.out.println(jsonString);

        String pathname = "./heros1.json";
        File file = new File(pathname);
        try {
            FileUtils.writeStringToFile(file, jsonString, SpiderUtil.UTF8);
            System.out.println("写数据到json成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4.下载图片
        int sign = 0;
        // for (Hero hero : heros) {
        // FileUtil.downloadImages(hero, sign);
        // }
        List<String> dirs = FileUtil.mkdir(sign);
        for (String dir : dirs) {
            FileUtil.downloadImages(heros,dir);
        }

    }

}
