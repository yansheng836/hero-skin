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

import xyz.yasnheng.bean.Hero;

/**
 * 文件工具类，作用：创建文件夹、下载图片。
 * 
 * @author yansheng
 * @date 2019/11/14
 */
public class FileUtil {
    
    static final String path1 = "1phone-smallskin-images";
    static final String path2 = "2phone-mobileskin-images";
    static final String path3 = "3phone-bigskin-images";
    static final String path4 = "4wallpaper-mobileskin-images";
    static final String path5 = "5wallpaper-bigskin-images";

    /**
     * 下载图片（先判断下载哪种尺寸的图片，然后调用实际函数downloadImage(String, String)去下载）
     * 
     * @param hero
     * @param sign
     *            sign 标志：0全部，1只下载手机小屏，2手机中，3手机大，4电脑中，5电脑大
     */
    public static void downloadImages(Hero hero, int sign) {

        // 获取需要用到的数据：英雄id，英雄名，英雄皮肤列表；英雄皮肤图片网址
        String id = hero.getId().toString();
        String cname = hero.getCname();
        List<String> skins = hero.getSkins();
        List<String> urls = null;

        String dir = null;

        switch (sign) {
            case 0:
                downloadImages(hero, 1);
                downloadImages(hero, 2);
                downloadImages(hero, 3);
                downloadImages(hero, 4);
                downloadImages(hero, 5);
                return;
            case 1:
                urls = hero.getPhoneSmallskinUrl();
                dir = "1phone-smallskin-images";
                break;
            case 2:
                urls = hero.getPhoneMobileskinUrl();
                dir = "2phone-mobileskin-images";
                break;
            case 3:
                urls = hero.getPhoneBigskinUrl();
                dir = "3phone-bigskin-images";
                break;
            case 4:
                urls = hero.getWallpaperMobileskinUrl();
                dir = "4wallpaper-mobileskin-images";
                break;
            case 5:
                urls = hero.getWallpaperBigskinUrl();
                dir = "5wallpaper-bigskin-images";
                break;
            default:
                System.err.println("标志sign错误，要求：只能是0-5之间的6个数");
                break;
        }
        // 创建目录
        mkdir(dir);

        // 下载图片
        int size = urls.size();
        for (int i = 0; i < size; i++) {
            String skin = skins.get(i);
            String imgUrl = urls.get(i);

            // phone-smallskin-images/96西施-0-归虚梦演.jpg
            String pathname = dir + "/" + id + cname + "-" + i + "-" + skin + ".jpg";
            // System.out.println("pathname:" + pathname);

            downloadImage(imgUrl, pathname);
        }

    }

    public static List<String> mkdir(int sign) {
        List<String> dirs = new ArrayList<String>(5);
        
        switch (sign) {
            case 0:
                dirs.add(path1);
                dirs.add(path2);
                dirs.add(path3);
                dirs.add(path4);
                dirs.add(path5);
                break;
            case 1:
                dirs.add(path1);
                break;
            case 2:
                dirs.add(path2);
                break;
            case 3:
                dirs.add(path3);
                break;
            case 4:
                dirs.add(path4);
                break;
            case 5:
                dirs.add(path5);
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

    public static void downloadImages(ArrayList<Hero> heros,String dir) {

        for (Hero hero : heros) {
            // 获取需要用到的数据：英雄id，英雄名，英雄皮肤列表；英雄皮肤图片网址
            String id = hero.getId().toString();
            String cname = hero.getCname();
            List<String> skins = hero.getSkins();
            List<String> urls = null;
            
            switch (dir) {
                case path1:
                    urls = hero.getPhoneSmallskinUrl();
                    break;
                case path2:
                    urls = hero.getPhoneMobileskinUrl();
                    break;
                case path3:
                    urls = hero.getPhoneBigskinUrl();
                    break;
                case path4:
                    urls = hero.getWallpaperMobileskinUrl();
                    break;
                case path5:
                    urls = hero.getWallpaperBigskinUrl();
                    break;
            }

            // 下载图片
            int size = urls.size();
            for (int i = 0; i < size; i++) {
                String skin = skins.get(i);
                String imgUrl = urls.get(i);

                // phone-smallskin-images/96西施-0-归虚梦演.jpg
                String pathname = dir + "/" + id + cname + "-" + i + "-" + skin + ".jpg";
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
            System.out.println(" -图片：" + pathname + " 已存在，故不再下载。");
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

}
