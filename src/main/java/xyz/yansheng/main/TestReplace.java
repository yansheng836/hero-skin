package xyz.yansheng.main;

/**
 * @author yansheng
 * @date 2019/11/22
 */
public class TestReplace {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String[] strings = {"惊鸿之笔&0|修竹墨客&0|梁祝&53", "冷晖之枪&0|幸存者&0"};

        for (String string : strings) {
            System.out.println(string.replaceAll("&\\d+", ""));
        }

    }

}
