 package xyz.yansheng.util;

 /**
 * @author yansheng
 * @date 2019/11/13
 */
public class TestString {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        String string = "幻纱之灵|归虚梦演";
        String[] strings = string.split("\\|");
        for (String string2 : strings) {
            System.out.println(string2);
        }
        

    }

}
