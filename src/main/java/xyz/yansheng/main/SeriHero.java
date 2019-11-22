package xyz.yansheng.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import xyz.yansheng.bean.Hero;
import xyz.yansheng.util.SpiderUtil;

/**
 * @author yansheng
 * @date 2019/11/22
 */
public class SeriHero {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String pathname = "seri_hero.txt";
//        seriHero(pathname);
        deSeriHero(pathname);

    }

    public static void seriHero(String pathname) {
        // 1.从英雄列表页爬取英雄基本数据（id,ename,cname,heroUrl）
        // 在线数据不实时，好像加了js，暂时不会爬取；先使用爬取下载的本地网页
        String url = "https://pvp.qq.com/web201605/herolist.shtml";
        String localUrl = "./英雄资料列表页-英雄介绍-王者荣耀官方网站-腾讯游戏.html";

        ArrayList<Hero> heros = SpiderUtil.getHeros(localUrl, SpiderUtil.GBK);

        // 2.从每个英雄主页heroUrl中获取英雄的皮肤信息（title，skinName，skins）
        int count = 0;
        for (Hero hero : heros) {
            SpiderUtil.getHeroSkins(hero);
            hero.generateField();
            // System.out.println(hero.toString());
//            System.out.println(hero.toStringSimple());
            count++;
            if (count == 2) {
                // break;
            }
        }

        // 序列化
        try (ObjectOutputStream oos2 =
            new ObjectOutputStream(new FileOutputStream(new File(pathname)));) {
            oos2.writeObject(heros);
            System.out.println("序列化成功");
            oos2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Hero> deSeriHero(String pathname) {
        // 反序列化
        ArrayList<Hero> heros = null;
        try (ObjectInputStream oiStream2 =
            new ObjectInputStream(new FileInputStream(new File(pathname)));) {
            heros = (ArrayList<Hero>)oiStream2.readObject();
//            for (Hero hero : heros) {
//                System.out.println(hero.toString());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return heros;
    }

}
