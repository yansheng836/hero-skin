 package xyz.yansheng.bean;

 import org.junit.Test;
 import xyz.yansheng.util.SpiderUtil;

/**
 * @author yansheng
 * @date 2019/11/13
 */
public class HeroTest {

    /**
     * Test method for {@link xyz.yansheng.bean.Hero#toString()}.
     */
    @Test
    public void testToString() {
        
        Hero hero = new Hero();
        hero.setId(96);
        hero.setEname(523);
        hero.setHeroUrl("https://pvp.qq.com/web201605/herodetail/523.shtml");
        
        hero = SpiderUtil.getHeroSkins(hero);
        hero.generateField();
        System.out.println(hero.toString());
    }

}
