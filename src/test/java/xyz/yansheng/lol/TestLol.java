package xyz.yansheng.lol;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import xyz.yansheng.util.FileUtil;

/**
 * 测试爬取lol皮肤中遇到的问题
 *
 * @author yansheng
 * @date 2021/6/20
 */
public class TestLol {

    /**
     * 测试匹配数字
     */
    @Test
    public void testMatcherNumber() {
        // 正则匹配
        // String str = "1phone-smallskin-images/74离群之刺-9-刺身之拳 阿卡丽.jpg";
        String str = "74离群之刺-9-刺身之拳 阿卡丽.jpg";

        String pattern = "\\d+";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(str);
        // 注意这里使用了的话，会影响下面的if，每次find值都会不一样（默认只匹配一次）
        // assertTrue(m.find());
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            assertEquals("74", m.group(0));
            // System.out.println("Found value: " + m.group(1));
        } else {
            System.out.println("NO MATCH");
        }

    }

    /*
    *  针对lol的情况，如果图片链接有问题，重新进行拼接
     * Test method for {@link xyz.yansheng.util.FileUtil#downloadImage()}.
     */
    @Test
    public void testDownloadImage() {

        // 正则匹配
        String pattern = "\\d+";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m;

        String imgUrl = "https://game.gtimg.cn/images/lol/act/img/skin/small1014.jpg";
        String pathname = "1phone-smallskin-images/0黑暗之女-15-福牛守护者 安妮 贺岁.jpg";

        String skinId = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.lastIndexOf('.'));
        skinId = skinId.replace("small", "").replace("big", "");

        String id = pathname.substring(pathname.lastIndexOf('/') + 1, pathname.length());
        System.out.println("skinId:" + skinId + ", id:" + id);

        m = r.matcher(id);
        if (m.find()) {
            id = m.group(0);
        } else {
            System.out.println("NO MATCH");
        }

        // imgUrl = "https://game.gtimg.cn/images/lol/act/img/chromas/" + id + "/" + skinId + ".png";
        int index = Integer.parseInt(id) + 1;
        imgUrl = "https://game.gtimg.cn/images/lol/act/img/chromas/" + index + "/" + skinId + ".png";

        System.out.println("imgUrl:" + imgUrl + ", pathname:" + pathname);


        assertEquals("https://game.gtimg.cn/images/lol/act/img/chromas/1/1014.png", imgUrl);
        assertEquals("1phone-smallskin-images/0黑暗之女-15-福牛守护者 安妮 贺岁.jpg", pathname);
        // downloadImage(String imgUrl, String pathname)
//        FileUtil.downloadImage(imgUrl, pathname);

    }
}
