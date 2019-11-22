package xyz.yansheng.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author yansheng
 * @date 2019/11/13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Hero implements Serializable{
    // {
    // "ename": 522,
    // "cname": "曜",
    // "title": "星辰之子",
    // "new_type": 0,
    // "hero_type": 1,
    // "skin_name": "归虚梦演"
    // },
    // {
    // "ename": 518,
    // "cname": "马超",
    // "title": "冷晖之枪",
    // "new_type": 1,
    // "hero_type": 1,
    // "hero_type2": 4
    // }

    private static final long serialVersionUID = 1L;

    /**
     * 英雄id，用于统计英雄数量
     */
    private Integer id;

    /**
     * 类似英雄id
     */
    private Integer ename;

    /**
     * 英雄名
     */
    private String cname;

    /**
     * 英雄介绍页
     */
    private String heroUrl;

    /**
     * 即默认皮肤名称，title = skin.get(0)
     */
    private String title;
    private Integer newType;
    private Integer heroType;
    /**
     * 皮肤名字符串，如：幻纱之灵|归虚梦演
     */
    private String skinName;
    /**
     * 皮肤名列表，如：[归虚梦演, 幻纱之灵]，按照推出顺序，先出的在前面
     */
    private List<String> skins;

    // 1.按照分析，创建对应文件夹
    // 1.1.https://game.gtimg.cn/images/yxzj/img201606/heroimg/518/518-smallskin-1.jpg
    // phone-smallskin-images 头像
    // phone-mobileskin-images 小屏手机图片
    // phone-bigskin-images 大屏手机图片
    // 1.2.https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/518/518-mobileskin-1.jpg
    // wallpaper-mobileskin-images 手机壁纸
    // wallpaper-bigskin-images 电脑壁纸

    private List<String> phoneSmallskinUrl;
    private List<String> phoneMobileskinUrl;
    private List<String> phoneBigskinUrl;
    private List<String> wallpaperMobileskinUrl;
    private List<String> wallpaperBigskinUrl;

    /**
     * 利用现有字段生成其他字段
     */
    public void generateField() {
        // 设置默认皮肤
        if (skins != null && !skins.isEmpty()) {
            title = skins.get(0);
        }

        // 根据英雄的ename和皮肤列表，生成各种类型皮肤图片网址
        if (ename != null && skins != null) {
            int size = skins.size();
            phoneSmallskinUrl = new ArrayList<String>(size);
            phoneMobileskinUrl = new ArrayList<String>(size);
            phoneBigskinUrl = new ArrayList<String>(size);
            wallpaperMobileskinUrl = new ArrayList<String>(size);
            wallpaperBigskinUrl = new ArrayList<String>(size);

            // https://game.gtimg.cn/images/yxzj/img201606/heroimg/518/518-smallskin-1.jpg
            // https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/518/518-mobileskin-1.jpg
            String phonePrefix = "https://game.gtimg.cn/images/yxzj/img201606/heroimg/";
            String wallpaperPrefix = "https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/";
            String smallskin = "-smallskin-";
            String mobileskin = "-mobileskin-";
            String bigskin = "-bigskin-";
            String suffix = ".jpg";
            for (int i = 1; i < size + 1; i++) {
                String psu = phonePrefix + ename + "/" + ename + smallskin + i + suffix;
                String pmu = phonePrefix + ename + "/" + ename + mobileskin + i + suffix;
                String pbu = phonePrefix + ename + "/" + ename + bigskin + i + suffix;
                String wmu = wallpaperPrefix + ename + "/" + ename + mobileskin + i + suffix;
                String wbu = wallpaperPrefix + ename + "/" + ename + bigskin + i + suffix;
                phoneSmallskinUrl.add(psu);
                phoneMobileskinUrl.add(pmu);
                phoneBigskinUrl.add(pbu);
                wallpaperMobileskinUrl.add(wmu);
                wallpaperBigskinUrl.add(wbu);
            }
        }

    }

    public String toStringSimple() {
        return "Hero [id=" + id + ", ename=" + ename + ", cname=" + cname + ", title=" + title
            + ", skinName=" + skinName + "]";
    }

}
