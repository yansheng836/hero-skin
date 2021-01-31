package xyz.yansheng.lol;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author yansheng
 * @date 2020/06/15
 */
public class Lol {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String url = "https://game.gtimg.cn/images/lol/act/img/js/heroList/hero_list.js";

        Document document = null;
        try {

            Connection con = Jsoup.connect(url)
                .userAgent(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml")
                .header("Content-Type", "application/json;charset=UTF-8")
                .ignoreContentType(true)
                .timeout(30000); // 设置连接超时时间

            Response response = con.execute();

            if (response.statusCode() == 200) {
                document = con.get();
            } else {
                // System.out.println(response.statusCode());
                return;
            }

//            System.out.println(document);
//            String textString = document.getElementsByTag("body").text();
            String textString = response.body();
            System.out.println(textString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
