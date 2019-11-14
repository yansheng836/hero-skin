package xyz.yansheng.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import xyz.yansheng.bean.Hero;

/**
 * 文件工具类，作用：创建文件夹、下载图片。
 * 
 * @author yansheng
 * @date 2019/11/14
 */
public class FileUtil {

    static final String PHONE_SAMLLSKIN_IMAGES = "1phone-smallskin-images";
    static final String PHONE_MOBILESKIN_IMAGES = "2phone-mobileskin-images";
    static final String PHONE_BIGSKIN_IMAGES = "3phone-bigskin-images";
    static final String WALLPAPER_MOBILESKIN_IMAGES = "4wallpaper-mobileskin-images";
    static final String WALLPAPER_BIGSKIN_IMAGES = "5wallpaper-bigskin-images";

    /**
     * 新建文件夹（不存在才创建）
     * 
     * @param pathname
     *            文件夹名
     */
    public static void mkdir(String pathname) {
        File file = new File(pathname);
        if (!file.exists()) {
            file.mkdirs();
            System.out.println("\n创建文件夹' " + pathname + "' 成功");
        } else {
            System.out.println("\n文件夹' " + pathname + "' 已存在");
        }
    }

    /**
     * 根据标志新建目录
     * 
     * @param sign
     *            sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
     */
    public static List<String> mkdir(int sign) {
        List<String> dirs = new ArrayList<String>(5);

        switch (sign) {
            case 0:
                dirs.add(PHONE_SAMLLSKIN_IMAGES);
                dirs.add(PHONE_MOBILESKIN_IMAGES);
                dirs.add(PHONE_BIGSKIN_IMAGES);
                dirs.add(WALLPAPER_MOBILESKIN_IMAGES);
                dirs.add(WALLPAPER_BIGSKIN_IMAGES);
                break;
            case 1:
                dirs.add(PHONE_SAMLLSKIN_IMAGES);
                break;
            case 2:
                dirs.add(PHONE_MOBILESKIN_IMAGES);
                break;
            case 3:
                dirs.add(PHONE_BIGSKIN_IMAGES);
                break;
            case 4:
                dirs.add(WALLPAPER_MOBILESKIN_IMAGES);
                break;
            case 5:
                dirs.add(WALLPAPER_BIGSKIN_IMAGES);
                break;
            default:
                System.err.println("标志sign错误，要求：只能是0-5之间的6个数");
                break;
        }
        // 创建目录
        for (String dir : dirs) {
            mkdir(dir);
        }

        return dirs;
    }

    /**
     * 下载图片（先判断下载哪种尺寸的图片，然后调用实际函数downloadImage(String, String)去下载）
     * 
     * @param heros
     *            英雄列表
     * @param dir
     *            目录
     */
    public static void downloadImages(ArrayList<Hero> heros, String dir) {

        for (Hero hero : heros) {
            // 获取需要用到的数据：英雄id，英雄名，英雄皮肤列表；英雄皮肤图片网址
            String id = hero.getId().toString();
            String cname = hero.getCname();
            List<String> skins = hero.getSkins();
            List<String> urls = null;

            switch (dir) {
                case PHONE_SAMLLSKIN_IMAGES:
                    urls = hero.getPhoneSmallskinUrl();
                    break;
                case PHONE_MOBILESKIN_IMAGES:
                    urls = hero.getPhoneMobileskinUrl();
                    break;
                case PHONE_BIGSKIN_IMAGES:
                    urls = hero.getPhoneBigskinUrl();
                    break;
                case WALLPAPER_MOBILESKIN_IMAGES:
                    urls = hero.getWallpaperMobileskinUrl();
                    break;
                case WALLPAPER_BIGSKIN_IMAGES:
                    urls = hero.getWallpaperBigskinUrl();
                    break;
                default:
                    break;
            }

            // 下载图片
            int size = urls.size();
            for (int i = 0; i < size; i++) {
                String skin = skins.get(i);
                String imgUrl = urls.get(i);

                // phone-smallskin-images/96西施-0-归虚梦演.jpg
                String pathname = dir + "/" + id + cname + "-" + (i + 1) + "-" + skin + ".jpg";
                // System.out.println("pathname:" + pathname);

                downloadImage(imgUrl, pathname);
            }
        }
    }

    /**
     * 下载图片（如果图片存在就不重复下载）
     * 
     * @param imgUrl
     *            网址
     * @param pathname
     *            文件名
     */
    public static void downloadImage(String imgUrl, String pathname) {
        // 取得图片文件名
        File outFile = new File(pathname);
        // 如果图片已存在，则直接跳过下载该图片，因为没有必要再下载一次
        if (outFile.exists()) {
//            System.out.println(" -图片：" + pathname + " 已存在，故不再下载。");
            return;
        }

        // 创建URL对象，将字符串解析为URL
        URL url = null;
        // 建立一个网络链接对象
        HttpURLConnection con = null;
        try {
            url = new URL(imgUrl);
            con = (HttpURLConnection)url.openConnection();
            // 设置请求方式
            con.setRequestMethod("GET");
            // 连接
            con.connect();
            // 得到响应码
            int responseCode = con.getResponseCode();
            // 这里假设只要不是4xx（请求错误）,5xx（服务器错误）都表示可以下载图片
            if (responseCode < 400) {
                // 响应成功，可以建立连接
            } else {
                System.err.println("图片链接(" + imgUrl + ")无效！响应状态码为：" + responseCode);
                return;
            }
        } catch (MalformedURLException e2) {
            System.err.println("图片链接(" + imgUrl + ")中不含有合法的网络协议或者无法解析该字符串！");
            e2.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 利用jdk1.7的新特性 ：try(resource){……} catch{……}，自动释放资源
        // 1.创建输入输出流 2.建立一个网络链接
        try (InputStream inputStream = con.getInputStream();
            OutputStream outputStream = new FileOutputStream(outFile);) {
            int n = -1;
            byte b[] = new byte[1024];
            while ((n = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
            outputStream.flush();
            System.out.println(" --下载图片:" + imgUrl + " 成功！保存位置为：" + pathname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
