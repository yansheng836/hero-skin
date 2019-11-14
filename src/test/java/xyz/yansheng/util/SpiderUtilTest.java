package xyz.yansheng.util;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

import xyz.yasnheng.bean.Hero;

/**
 * @author yansheng
 * @date 2019/11/13
 */
public class SpiderUtilTest {

    /**
     * Test method for {@link xyz.yansheng.util.SpiderUtil#getHeros(java.lang.String)}.
     */
    @Test
    public void testGetHeros() {
        String localUrl = "./英雄资料列表页-英雄介绍-王者荣耀官方网站-腾讯游戏.html";
        ArrayList<Hero> heros = SpiderUtil.getHeros(localUrl, SpiderUtil.GBK);
        assertNotNull(heros);
        for (Hero hero : heros) {
            System.out.println(hero.toString());
        }

    }

    @Test
    public void testGetHeroSkins(){
        Hero hero = new Hero();
        hero.setHeroUrl("https://pvp.qq.com/web201605/herodetail/523.shtml");
        hero = SpiderUtil.getHeroSkins(hero);
        System.out.println(hero.toString());
    }

}
