package xyz.yansheng.util;

import java.util.ArrayList;
import java.util.Collections;

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

        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        Collections.reverse(list);
        System.out.println(list.get(0));

        System.out.println("K/DA ALL OUT 伊芙琳".replaceAll("/", "-"));

        // 2021年7月10日14:35:20 测试分割 68000|68001|68002|68003|68004|68013
        String title = "68000|68001|68002|68003|68004|68013";
        String[] skinidArray = title.split("\\|");
        System.out.println("skinidArray:" + skinidArray);
        for (String skinid : skinidArray) {
            System.out.println("skinid:" + skinid);
            System.out.println("skinid:" + skinid.substring(skinid.length() - 2, skinid.length()));
            Integer id = Integer.parseInt(skinid.substring(skinid.length() - 2, skinid.length())) + 1;
            System.out.println("skinid:" + id);
        }

    }

}
