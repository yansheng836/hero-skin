package xyz.yansheng.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xyz.yasnheng.bean.Hero;

/**
 * 工具类
 * 
 * @author yansheng
 * @date 2019/09/30
 */
public class SpiderUtil {

    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";

    /**
     * 获取英雄列表
     * 
     * @param username
     * @return
     */
    public static ArrayList<Hero> getHeros(String url, String encoding) {

        Document doc = null;

        // 判断是读取本地HTML还是在线爬取
        if (url.contains("./")) {
            // 本地HTML
            String html = null;
            try {
                html = FileUtils.readFileToString(new File(url), encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc = Jsoup.parse(html);
        } else {
            try {
                doc = Jsoup.parse(new URL(url).openStream(), encoding, url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // System.out.println(doc);

        // div herolist-content ul.herolist.clearfix li
        Elements liElements = doc.select("ul.herolist.clearfix li");
        // System.out.println(liElements);

        int size = liElements.size();

        ArrayList<Hero> heros = new ArrayList<Hero>(size);
        // 后面出的英雄在最前面，进行逆序统计id
        for (int i = 0; i < size; i++) {

            Hero hero = new Hero();
            hero.setId(size - i);
            Element liElement = liElements.get(i);
            Element aElement = liElement.selectFirst("a");
            if (liElement != null) {
                // 英雄主页网址heroUrl
                String heroUrl = aElement.attr("href");
                hero.setHeroUrl(heroUrl);

                // 英雄ename
                String ename = heroUrl.substring(heroUrl.lastIndexOf('/') + 1);
                ename = ename.substring(0, 3);
                hero.setEname(Integer.parseInt(ename));

                // 英雄cname
                Element imgElement = aElement.selectFirst("img");
                String cname = aElement.text();
                hero.setCname(cname);
            }
            heros.add(hero);
        }
        return heros;
    }

    /**
     * 根据英雄的信息（主要是英雄介绍页网址），获得皮肤名称字符串（逆序）和皮肤列表（有序）。
     * 
     * @param Hero
     *            含有HeroUrl的英雄。
     * @return Hero 在原有基础上，添加了skinName和skins字段的英雄。
     */
    public static Hero getHeroSkins(Hero hero) {

        String url = hero.getHeroUrl();

        List<String> skins = new ArrayList<String>(5);

        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), GBK, url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 皮肤列表
        // ul pic-pf-list pic-pf-list3
        Element liElements = doc.selectFirst("ul.pic-pf-list");
        // System.out.println(liElements);
        String skinName = liElements.attr("data-imgname");

        // 皮肤列表，逆序
        hero.setSkinName(skinName);
//        System.out.println(skinName);

        String[] skinsArray = skinName.split("\\|");
        skins = Arrays.asList(skinsArray);

        // 遍历
        // System.out.println(skins.size());
        // System.out.println("skins:" + skins);

        // skins.forEach((String skin) -> {
        // System.out.println(skin);
        // });

        // 因为后面的才是默认皮肤，最新皮肤在前面，这里进行逆序
        Collections.reverse(skins);
        // System.out.println("\n逆序后");
        // skins.forEach((String skin) -> {
        // System.out.println(skin);
        // });

        hero.setSkins(skins);
        // System.out.println(hero.toString());

        return hero;
    }

    
}
