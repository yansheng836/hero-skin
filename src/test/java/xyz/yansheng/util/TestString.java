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

    }

}
