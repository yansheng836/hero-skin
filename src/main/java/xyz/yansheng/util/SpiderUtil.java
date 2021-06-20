package xyz.yansheng.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import xyz.yansheng.bean.Hero;

/**
 * 爬虫工具类
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
     * @param url
     *            英雄列表页网址
     * @param encoding
     *            编码
     * @return 英雄list信息
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
                String cname = aElement.text();
                hero.setCname(cname);
            }
            heros.add(hero);
        }
        return heros;
    }

    /**
     * 获取英雄列表
     *
     * @param url
     *            英雄列表页网址
     * @param encoding
     *            编码
     * @return 英雄list信息
     */
    public static ArrayList<Hero> getLolHeros(String url, String encoding) {

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
            assert html != null;
            doc = Jsoup.parse(html);
        } else {
            try {
                doc = Jsoup.parse(new URL(url).openStream(), encoding, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // System.out.println(doc);

        Elements liElements = doc.select("ul.imgtextlist li");
        int size = liElements.size();
        ArrayList<Hero> heros = new ArrayList<>(size * 2);
        // 后面出的英雄在最前面，进行逆序统计id
        for (int i = 0; i < size; i++) {

            Hero hero = new Hero();
            hero.setId(i);
            Element liElement = liElements.get(i);
            // System.out.println("liElement:"+liElement);
            Element aElement = liElement.selectFirst("a");
            if (liElement != null) {
                // 英雄主页网址heroUrl http://lol.qq.com/data/info-defail.shtml?id=1
                String heroUrl = aElement.attr("href");
                hero.setHeroUrl(heroUrl);

                // 英雄ename
                String ename = heroUrl.substring(heroUrl.lastIndexOf('=') + 1);
                hero.setEname(Integer.parseInt(ename));

                // 英雄cname
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
     * @param hero
     *            含有HeroUrl的英雄。
     * @return Hero 在原有基础上，添加了skinName和skins字段的英雄。
     */
    public static Hero getHeroSkins(Hero hero) {

        String url = hero.getHeroUrl();

        List<String> skins1 = new ArrayList<String>(5);

        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), GBK, url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 皮肤列表
        // <ul class="pic-pf-list pic-pf-list3" data-imgname="幻纱之灵|归虚梦演">
        // (2019年11月22日)发现有变化了：幻纱之灵&0|归虚梦演&0，需要去除多余的字符串
        Elements liElements = doc.select("ul.pic-pf-list");

        // 英雄skinName,skins
        String skinName = liElements.attr("data-imgname");
        // System.out.println(skinName);
        // 去除皮肤名中多余的字符串
        skinName = skinName.replaceAll("&\\d+", "");
        String[] skinsArray = skinName.split("\\|");
        skins1 = Arrays.asList(skinsArray);

        hero.setSkinName(skinName);
        hero.setSkins(skins1);

        return hero;
    }

    /**
     * 根据英雄的信息（主要是英雄介绍页网址），获得皮肤名称字符串（逆序）和皮肤列表（有序）。
     *
     * @param hero
     *            含有HeroUrl的英雄。
     * @return Hero 在原有基础上，添加了skinName和skins字段的英雄。
     * @deprecated
     */
    public static Hero getLolHeroSkins(Hero hero) {

        String url = hero.getHeroUrl();

        List<String> skins1 = new ArrayList<String>(5);

        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), GBK, url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 皮肤列表
        Elements liElements = doc.select("ul#skinNAV");
        System.out.println("liElements:" + liElements);
        // 皮肤列表由js加载走不通了！！！
        // <ul id="skinNAV">
        // 皮肤缩略图加载中...
        // </ul>

        String skinName = "";
        String title = "";
        for (int i = 0; i < liElements.size(); i++) {
            Element liElement = liElements.get(i);
            Element aElement = liElement.selectFirst("a");
            System.out.println("aElement:" + aElement);
            if (i == 0) {
                title = aElement.attr("title");
            }

            skinName = skinName + aElement.attr("title") + "|";
        }
        System.out.println("skinName:" + skinName);

        // Hero [id=105, ename=155, cname=艾琳, title=精灵之舞, skinName=精灵之舞|女武神]
        // Hero [id=104, ename=537, cname=司空震, title=雷霆之王, skinName=雷霆之王|启蛰]

        String[] skinsArray = skinName.split("\\|");
        skins1 = Arrays.asList(skinsArray);

        hero.setSkinName(skinName);
        hero.setSkins(skins1);
        hero.setTitle(title);

        return hero;
    }

    /**
     * 根据英雄的信息（主要是英雄介绍页网址），获得皮肤名称字符串（逆序）和皮肤列表（有序）。 因为皮肤列表是通过js加载的，不能直接爬取，只能通过json进行爬取
     * http://game.gtimg.cn/images/lol/act/img/js/hero/1.js
     *
     * @param hero
     *            含有HeroUrl的英雄。
     * @return Hero 在原有基础上，添加了skinName和skins字段的英雄。
     */
    public static Hero getLolHeroSkins2(Hero hero) {

        List<String> skins1 = new ArrayList<>(32);

        // String url = "http://game.gtimg.cn/images/lol/act/img/js/hero/1.js";
        String url = "http://game.gtimg.cn/images/lol/act/img/js/hero/" + hero.getEname() + ".js";

        Document document = null;
        JSONObject object = null;
        try {
            Connection con = Jsoup.connect(url).userAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml")
                .header("Content-Type", "application/json;charset=UTF-8").ignoreContentType(true)
                // 设置连接超时时间
                .timeout(30000);
            Connection.Response response = con.execute();
            if (response.statusCode() == 200) {
                document = con.get();
            } else {
                System.err.println(response.statusCode());
                return null;
            }
            object = JSONObject.parseObject(response.body());
            // System.out.println(object);
        } catch (IOException e) {
            System.out.println("发生异常，信息为：" + e);
            e.printStackTrace();
        }

        assert object != null;
        JSONObject heroObject = object.getJSONObject("hero");
        // System.out.println("heroObject:" + heroObject);
        String title = heroObject.getString("title");
        // System.out.println("title:" +title);

        JSONArray skins = object.getJSONArray("skins");
        // System.out.println("skins:" + skins);
        // System.out.println("skins:" + skins.size());
        String skinName = "";
        title = "";
        for (int i = 0; i < skins.size(); i++) {
            String skin = skins.getJSONObject(i).getString("name");
            skinName = skinName + skin + "|";
            String skinId = skins.getJSONObject(i).getString("skinId");
            title = title + skinId + "|";
        }
        skinName = skinName.substring(0, skinName.lastIndexOf('|'));
        title = title.substring(0, title.lastIndexOf('|'));

        // Hero [id=105, ename=155, cname=艾琳, title=精灵之舞, skinName=精灵之舞|女武神]
        // Hero [id=104, ename=537, cname=司空震, title=雷霆之王, skinName=雷霆之王|启蛰]
        String[] skinsArray = skinName.split("\\|");
        skins1 = Arrays.asList(skinsArray);

        hero.setSkinName(skinName);
        hero.setSkins(skins1);
        hero.setTitle(title);
        return hero;
    }

}
