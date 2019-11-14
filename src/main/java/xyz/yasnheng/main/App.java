package xyz.yasnheng.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import xyz.yansheng.util.SpiderUtil;
import xyz.yasnheng.bean.Hero;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        // 1.从英雄列表页爬取英雄基本数据（id,ename,cname,heroUrl）
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
            hero.setTitle(hero.getSkins().get(0));
            System.out.println(hero.toString());
            count++;
            if (count == 2) {
                break;
            }
        }

        // 3.将数据写到json中
        // JSON,JSONArray也可以
        // String jsonString = JSON.toJSONString(heros);
        // 保留空值
        String jsonString = JSON.toJSONString(heros, SerializerFeature.WriteMapNullValue,
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

    }

}
