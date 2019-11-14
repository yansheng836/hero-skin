package xyz.yansheng.util;

import java.io.IOException;
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
     * 
     * @throws IOException
     */
    @Test
    public void testGetHeros() {
        ArrayList<Hero> heros = SpiderUtil.getHeros("");
        for (Hero hero : heros) {
            System.out.println(hero.toString());
        }
        System.out.println();

        // 方法2.2（java8）用lambda+ “jdk8新特性之双冒号 :: ”排序
        // heros.sort(Comparator.comparingInt(Hero::getEname));
        // for (Hero hero : heros) {
        // System.out.println(hero.toString());
        // }

    }

    @Test
    public void testGetHeroSkins(){
        Integer ename = 523;
        Hero hero = new Hero();
        hero.setHeroUrl("https://pvp.qq.com/web201605/herodetail/523.shtml");
        hero = SpiderUtil.getHeroSkins(hero);
        System.out.println(hero.toString());
    }

}
