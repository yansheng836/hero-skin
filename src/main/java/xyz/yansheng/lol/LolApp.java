package xyz.yansheng.lol;

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
import xyz.yansheng.util.LogUtil;
import xyz.yansheng.util.SpiderUtil;

/**
 * 爬取LOL皮肤壁纸（按照王者的规则，先爬取主页英雄列表的信息，然后根据id，爬取对应英雄json数据 （本来应该要爬某个英雄主页皮肤信息的，但是因为数据是js加载的，行不通）。）
 *
 * @author yansheng
 * @date 2019/11/13
 */
public class LolApp {

    public static final String CLASS_NAME = "xyz.yansheng.lol.LolApp";

    public static void main(String[] args) {

        String infoString;

        // 1.从英雄列表页爬取英雄基本数据（id,ename,cname,heroUrl）
        // 在线数据不实时，好像加了js，暂时不会爬取；先使用爬取下载的本地网页
        // String url = "https://pvp.qq.com/web201605/herolist.shtml";
        String localUrl = "./游戏资料-英雄联盟官方网站-腾讯游戏.html";

        ArrayList<Hero> heros = SpiderUtil.getLolHeros(localUrl, SpiderUtil.GBK);
        int size = heros.size();
        // System.out.println(heros.get(0).toString());
        // for (Hero hero : heros) {
        // System.out.println(hero.toString());
        // }

        // 2.从每个英雄主页heroUrl中获取英雄的皮肤信息（title，skinName，skins）
        int count = 0;
        for (Hero hero : heros) {
            SpiderUtil.getLolHeroSkins2(hero);
            // 生成皮肤相关信息
            hero.generateLolField();
            count++;
        }
        //      System.exit(1);
        int sum = 0;
        for (Hero hero : heros) {
            sum = sum + hero.getSkins().size();
            System.out.println("hero:" + hero);
        }

        infoString = "到目前为止，英雄联盟一共有" + heros.size() + "个英雄，" + sum + "个皮肤（含伴生皮肤）。";
        System.out.println(infoString);
        // LogUtil.writeLog(CLASS_NAME, "", LogUtil.INFO, infoString);
        LogUtil.writeLog(CLASS_NAME, null, LogUtil.INFO, infoString);

        // System.exit(1);

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

        // String jsonString =
        // JSON.toJSONString(map, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);

        // 输出格式化后的字符串 https://blog.csdn.net/bestxianfeng163/article/details/100073318
        String pretty = JSON.toJSONString(map, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);

        String pathname = "./lol-heros.json";
        File file = new File(pathname);
        try {
            FileUtils.writeStringToFile(file, pretty, SpiderUtil.UTF8);
            System.out.println("写数据到json成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.exit(1);
        // 4.下载图片:sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
        int sign = 0;

        List<String> dirs = FileUtil.mkdir(sign);
        for (String dir : dirs) {
            FileUtil.downloadImages(heros, dir);
        }

        // 临时下载某个
        // ArrayList<Hero> tempList = new ArrayList<>();
        // Hero tempHero = heros.get(16);
        // tempList.add(tempHero);
        // for (String dir : dirs) {
        //     FileUtil.downloadImages(tempList, dir);
        // }
    }

}
