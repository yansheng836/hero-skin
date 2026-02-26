package xyz.yansheng.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import xyz.yansheng.bean.Hero;

import javax.security.auth.callback.TextInputCallback;

/**
 * 爬虫工具类
 *
 * @author yansheng
 * @date 2019/09/30
 */
public class SpiderUtil {

    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String CLASS_NAME = "xyz.yansheng.util.SpiderUtil";

    /**
     * 获取英雄列表
     *
     * @param url      英雄列表页网址
     * @param encoding 编码
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
//        Elements liElements = doc.select("herolist-content.li");
//         System.out.println(liElements);

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
//                String ename = heroUrl.substring(heroUrl.lastIndexOf('/') + 1);
//                ename = ename.substring(0, 3);
//                System.out.println(ename);
//                hero.setEname(Integer.parseInt(ename));
//                Element aElement2 = liElement.selectFirst("img");
//                String heroUrl2 = aElement2.attr("src");
////                System.out.println(heroUrl2.lastIndexOf('/'));
//                String ename = heroUrl2.substring(heroUrl2.lastIndexOf('/'));
////                System.out.println(ename);
//                ename = ename.substring(1, 4);
//                System.out.println(ename);
//                hero.setEname(Integer.parseInt(ename));
//                2026年2月25日16:09:57 因界面没有该值了，不再统计ename，设置默认值0
                hero.setEname(0);

                // 英雄cname
                String cname = aElement.text();
                hero.setCname(cname);
//                System.out.println(hero);
            }
            heros.add(hero);
        }
        return heros;
    }

    /**
     * 获取英雄列表-从json的请求获取
     *
     * @param url      英雄列表页网址
     * @param encoding 编码
     * @return 英雄list信息
     */
    public static ArrayList<Hero> getHerosFromJSON(String url, String encoding) {

        Document doc = null;
        String jsonString = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), encoding, url);
//            System.out.println(doc);
            jsonString = doc.text();
//            System.out.println(jsonString);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        转成对象
        JSONObject rootObj = JSONObject.parseObject(jsonString);
        JSONArray skinArray = rootObj.getJSONArray("pflb20_3469");
        JSONArray heroArray = rootObj.getJSONArray("yxlb20_2489");
        ArrayList<Hero> heros = new ArrayList<>();
        if (heroArray != null && !heroArray.isEmpty()) {
            // 4. 遍历数组，提取字段并封装
            System.out.println("heroArray.size():" + heroArray.size());
            for (int i = 0; i < heroArray.size(); i++) {
                // 创建对象并添加到列表
                Hero hero = new Hero();

                JSONObject heroObj = heroArray.getJSONObject(i);
                // 提取指定字段
                hero.setId(i);
                // 英雄cname
                String cname = heroObj.getString("yxmclb_9965");
                hero.setCname(cname);
//                System.out.println("cname:" + cname);

                String baseSkinImage = heroObj.getString("fmlb_4536");
//                System.out.println("baseSkinImage:"+baseSkinImage);
                String skinName = heroObj.getString("pfmclb_5571");
                skinName = skinName.replaceAll("&\\d+", "");
//                System.out.println("skinName:" + skinName);
                hero.setSkinName(skinName);
//                System.exit(1);

//                // 英雄ename
                String ename = heroObj.getString("yxpymc_4614");
//                hero.setEname(Integer.parseInt(ename));
//                https://pvp.qq.com/web201605/herodetail/fuluolun.shtml
                hero.setHeroUrl("https://pvp.qq.com/web201605/herodetail/" + ename + ".shtml");

//                处理皮肤
                String title = "";
                List<String> skins = new ArrayList<>();
//                skins.add(skinArr[0]);
//                    hero.setSkins(skins);

                List<String> phoneSmallskinUrl = new ArrayList<>();
                phoneSmallskinUrl.add(baseSkinImage + "?imageMogr2/crop/67x67/gravity/center");
//                    hero.setPhoneSmallskinUrl(phoneSmallskinUrl);
                List<String> phoneMobileskinUrl = new ArrayList<>();
                phoneMobileskinUrl.add(baseSkinImage + "?imageMogr2/crop/600x410/gravity/center");
//                    hero.setPhoneMobileskinUrl(phoneMobileskinUrl);
                List<String> phoneBigskinUrl = new ArrayList<>();
                phoneBigskinUrl.add(baseSkinImage + "?imageMogr2/crop/1200x530/gravity/center");
//                    hero.setPhoneBigskinUrl(phoneBigskinUrl);

                List<String> wallpaperMobileskinUrl = new ArrayList<>();
                wallpaperMobileskinUrl.add(baseSkinImage + "?imageMogr2/crop/727x1070/gravity/center");
//                    hero.setWallpaperMobileskinUrl(wallpaperMobileskinUrl);
                List<String> wallpaperBigskinUrl = new ArrayList<>();
                wallpaperBigskinUrl.add(baseSkinImage + "?imageMogr2/crop/1920x882/gravity/center");
//                    hero.setWallpaperBigskinUrl(wallpaperBigskinUrl);

//                只有一个皮肤的情况
                if (!skinName.contains("|")) {
                    title = skinName;

                    skins.add(skinName);
//                    hero.setSkins(skins);
//                    System.out.println(hero);
                } else {
                    // 处理皮肤，注意：如果是原皮，这里是没有的
//                    System.out.println("cname:" + cname);
//                    System.out.println("skinName:" + skinName);
                    String[] skinArr = skinName.split("\\|");
                    title = skinArr[0];
                    skins.add(title);

                    for (String tempSkin : skinArr) {
                        tempSkin = tempSkin.trim();
//                        System.out.println("tempSkin:" + tempSkin);

                        for (int j = 0; j < skinArray.size(); j++) {
                            JSONObject skinObj = skinArray.getJSONObject(j);
//                        System.out.println(skinObj);
                            String heroName = skinObj.getString("yxmclb_9965").trim();
                            String skinName2 = skinObj.getString("pfmclb_7523").trim();
//                            找到了（如果英雄名和皮肤名都一样）
                            if (cname.equals(heroName) && tempSkin.equals(skinName2)) {
//                            System.out.println("111111111");
//                                System.out.println("skinObj:" + skinObj);
                                skins.add(tempSkin);

                                baseSkinImage = skinObj.getString("fmlb_4536");
                                phoneSmallskinUrl.add(baseSkinImage + "?imageMogr2/crop/67x67/gravity/center");
                                phoneMobileskinUrl.add(baseSkinImage + "?imageMogr2/crop/600x410/gravity/center");
                                phoneBigskinUrl.add(baseSkinImage + "?imageMogr2/crop/1200x530/gravity/center");
                                wallpaperMobileskinUrl.add(baseSkinImage + "?imageMogr2/crop/727x1070/gravity/center");
                                wallpaperBigskinUrl.add(baseSkinImage + "?imageMogr2/crop/1920x882/gravity/center");
                            } else {
//                            System.out.println("2222222222");
//                        continue;
                            }
                        }
                    }// end for
                }
                hero.setTitle(title);
                hero.setSkins(skins);
                hero.setPhoneSmallskinUrl(phoneSmallskinUrl);
                hero.setPhoneMobileskinUrl(phoneMobileskinUrl);
                hero.setPhoneBigskinUrl(phoneBigskinUrl);
                hero.setWallpaperMobileskinUrl(wallpaperMobileskinUrl);
                hero.setWallpaperBigskinUrl(wallpaperBigskinUrl);
                heros.add(hero);
//                System.out.println("获取皮肤后："+hero);
//                System.exit(1);
            }
//            System.exit(1);
        }
        return heros;
    }

    /**
     * 获取英雄列表
     *
     * @param url      英雄列表页网址
     * @param encoding 编码
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
     * @param hero 含有HeroUrl的英雄。
     * @return Hero 在原有基础上，添加了skinName和skins字段的英雄。
     */
    public static Hero getHeroSkins(Hero hero) {

        String url = hero.getHeroUrl();

        List<String> skins1 = new ArrayList<String>(5);

        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), GBK, url);
//            System.out.println(doc);
            System.out.println(doc.outerHtml());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 获取ename，但是实际上图片url没有再使用这个进行生成了！
        // 使用正则表达式匹配 ename 的值
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("ename\\s*=\\s*'([^']+)'");
        java.util.regex.Matcher matcher = pattern.matcher(doc.outerHtml());
        if (matcher.find()) {
            String enameValue = matcher.group(1);
//            System.out.println("enameValue:"+enameValue); // 输出: 635
            hero.setEname(Integer.parseInt(enameValue));
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

        System.out.println("处理后：");
        System.out.println(hero);

        return hero;
    }

    /**
     * 根据英雄的信息（主要是英雄介绍页网址），获得皮肤名称字符串（逆序）和皮肤列表（有序）。
     *
     * @param hero 含有HeroUrl的英雄。
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
     * @param hero 含有HeroUrl的英雄。
     * @return Hero 在原有基础上，添加了skinName和skins字段的英雄。
     */
    public static Hero getLolHeroSkins2(Hero hero) {

        List<String> skins1 = new ArrayList<>(32);

        // String url = "http://game.gtimg.cn/images/lol/act/img/js/hero/1.js";
        String url = "http://game.gtimg.cn/images/lol/act/img/js/hero/" + hero.getEname() + ".js";
//        System.out.println("url:"+url);

        new File("./log").mkdirs();
        String logFileName = "./log/lol-per-hero-js-" + LocalDate.now() + ".log";
        LogUtil.writeLog(CLASS_NAME, logFileName, LogUtil.INFO, url);

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
            int sucessStatus = 200;
            if (response.statusCode() == sucessStatus) {
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
        //         System.out.println("skins:" + skins);
        // System.out.println("skins:" + skins.size());
        String skinName = "";
        String skinId2 = "";
        title = "";
        // 判断皮肤是否为基本皮肤 chromasBelongId = 0 只统计基本皮肤
        String chromasBelongId;
        for (int i = 0; i < skins.size(); i++) {
            //            System.out.println("skins.getJSONObject(i):" + skins.getJSONObject(i));
            chromasBelongId = skins.getJSONObject(i).getString("chromasBelongId");
            //            System.out.println("chromasBelongId:" + chromasBelongId);
            if ("0".equals(chromasBelongId)) {

                String skin = skins.getJSONObject(i).getString("name");
                skinName = skinName + skin + "|";
                String skinId = skins.getJSONObject(i).getString("skinId");
                title = title + skinId + "|";

//                2024年5月11日18:32:41 获取皮肤图片id
//                https://game.gtimg.cn/images/lol/act/img/center/0b95894e-0df2-470e-b282-6c5f5cf41955.jpg
                String skinId2Temp = skins.getJSONObject(i).getString("centerImg");
                skinId2 = skinId2 + skinId2Temp.replace("https://game.gtimg.cn/images/lol/act/img/center/", "") + "|";

                //                System.out.println("skinName:" + skinName + ",chromasBelongId:" + chromasBelongId);
            }
        }
        skinName = skinName.substring(0, skinName.lastIndexOf('|'));
        title = title.substring(0, title.lastIndexOf('|'));
        skinId2 = skinId2.substring(0, skinId2.lastIndexOf('|'));
//        System.out.println("skinId2:" +skinId2);

        // 去除特殊字符，比如 “K/DA ALL OUT 伊芙琳”
        skinName = skinName.replaceAll("/", "-");
        skinName = skinName.replaceAll(":", " ");
        skinName = skinName.replaceAll("\"", " ");
        title = title.replaceAll("/", "-");

        //        System.out.println("skinName:" + skinName);
        //        System.out.println("title:" + title);

        // Hero [id=105, ename=155, cname=艾琳, title=精灵之舞, skinName=精灵之舞|女武神]
        // Hero [id=104, ename=537, cname=司空震, title=雷霆之王, skinName=雷霆之王|启蛰]
        String[] skinsArray = skinName.split("\\|");
        skins1 = Arrays.asList(skinsArray);

        hero.setSkinName(skinName);
        hero.setSkins(skins1);
        hero.setSkinsIds(Arrays.asList(skinId2.split("\\|")));
        hero.setTitle(title);
        return hero;
    }
}
