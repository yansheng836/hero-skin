package xyz.yansheng.lol;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;

/**
 * 尝试通过爬取json数据的形式爬取相关信息，应该是可行的。
 * 
 * @author yansheng
 * @date 2020/06/15
 */
public class LolForJson {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String url = "https://game.gtimg.cn/images/lol/act/img/js/heroList/hero_list.js";
        // String url = "http://game.gtimg.cn/images/lol/act/img/js/hero/1.js";

        Document document = null;
        try {
            Connection con = Jsoup.connect(url).userAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml")
                .header("Content-Type", "application/json;charset=UTF-8").ignoreContentType(true)
                // 设置连接超时时间
                .timeout(30000);
            Response response = con.execute();

            int sucessStatus = 200;
            if (response.statusCode() == sucessStatus) {
                document = con.get();
            } else {
                 System.err.println("解析出错，状态码不是200，而是："+response.statusCode());
                return;
            }

            String textString = response.body();
            // 这个json默认情况下含有Unicode字符，需要进行转义
            //"name":"\u9ed1\u6697\u4e4b\u5973" --> "name":"黑暗之女"
            System.out.println(textString);
            // parseObject 自动转义Unicode
            Object object = JSONObject.parseObject(textString);
            System.out.println(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
