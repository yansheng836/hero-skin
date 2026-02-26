[TOC]

---

# Hero-Skin

Java爬取王者荣耀和英雄联盟的英雄皮肤。

注意该项目仅仅是为了爬取，不进行存储，存储放到另外的项目：

王者荣耀：

- <https://gitee.com/yansheng0083/hero-skin-image>
- <https://github.com/yansheng836/hero-skin-image>

英雄联盟：

- <https://gitee.com/yansheng0083/hero-skin-lol>
- <https://github.com/yansheng836/hero-skin-lol>

## 王者荣耀

爬取方式：

1. 先将英雄介绍主页下载到本地（项目跟目录）；
2. 爬取该网页，获取英雄列表数据（包含单个英雄主页URL）；
3. 遍历爬取每一个英雄主页，获得英雄皮肤ID，拼接URL；
4. 下载图片。

### 相关信息

官网英雄介绍主页：<https://pvp.qq.com/web201605/herolist.shtml>，如果出英雄需要先下载该页面（放到脚本目录），然后执行脚本才有效。

爬取王者荣耀的英雄皮肤图片，详情请看对应python仓库：<https://github.com/yansheng836/hero-skin-images>。

游戏壁纸：<https://pvp.qq.com/web201605/wallpaper.shtml>

### 统计

到目前为止，王者荣耀一共有96个英雄，366个皮肤（含伴生皮肤）。

|         时间          | 英雄数量 | 皮肤数量 |
| :-------------------: | :------: | :------: |
|      2019-11-14       |    96    |   366    |
| 2021年1月30日23:41:04 |    96    |   422    |
| 2021年1月30日23:51:42 |   104    |   439    |
| 2021年5月12日18:26:00 |   104    |   463    |
| 2021年6月12日22:25:16 |   105    |   465    |
| 2023年4月30日01:09:57 |   114    |   617    |

### 存在问题

#### 1.只能爬取本地网页

爬取的网页数据不是最新的，如直接爬取最新的英雄为93，506，云中君；但是将该网页下载后再爬取，最新,96，523，西施。

处理方法：可以将网页先下载下来，爬取本地文件。

#### 2.2026年2月25日16:30:34网页变化

英雄没有了ename信息，各种类型壁纸url需要用新方式获取，而且好像凑不齐5种类型的壁纸了，后面发现可以自行调整尺寸。

##### 原各类型壁纸尺寸

参考：https://github.com/yansheng836/hero-skin-image/blob/main/wzry-heros.json

苍

phoneBigskinUrl，https://game.gtimg.cn/images/yxzj/img201606/heroimg/177/177-bigskin-1.jpg，1200-530

phoneMobileskinUrl，https://game.gtimg.cn/images/yxzj/img201606/heroimg/177/177-mobileskin-1.jpg，600-410

phoneSmallskinUrl，https://game.gtimg.cn/images/yxzj/img201606/heroimg/558/558-smallskin-1.jpg，67-67

wallpaperBigskinUrl，https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/558/558-bigskin-1.jpg，1920-882

wallpaperMobileskinUrl，https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/558/558-mobileskin-1.jpg，727-1070

##### 新壁纸-弗洛伦

https://pvp.qq.com/web201605/herodetail/fuluolun.shtml

后面发现这个是动态加载的，直接获取不了！

```html
<img src="https://game-1255653016.file.myqcloud.com/manage/custom_wzry_E1/bec9e5ca9cec82677f55a0c5c7af99a6.png?imageMogr2/crop/120x120/gravity/center" alt="" data-imgname="https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/1920x882/gravity/center" data-title="百花剑豪" data-icon="0">
```

小图：https://game-1255653016.file.myqcloud.com/manage/custom_wzry_E1/bec9e5ca9cec82677f55a0c5c7af99a6.png?imageMogr2/crop/120x120/gravity/center

原图：https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg

全屏wallpaperBigskinUrl：https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/1920x882/gravity/center

phoneBigskinUrl，https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/1200x530/gravity/center，1200-530

phoneMobileskinUrl，https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/600x410/gravity/center，600-410

phoneSmallskinUrl，https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/67x67/gravity/center，67-67

wallpaperBigskinUrl，https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/1920x882/gravity/center，1920-882

wallpaperMobileskinUrl，https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg?imageMogr2/crop/727x1070/gravity/center，727-1070

#### 3.从json获取数据

https://pvp.qq.com/zlkdatasys/heroskinlist.json

详见：[wzry-heros-zlkdatasys-heroskinlist.json](./wzry-heros-zlkdatasys-heroskinlist.json)

其中**yxlb20_2489**是英雄信息。

```json
{
    "pflb20_3469": [],
    "yxlb20_2489": [
        {
            "fllb_2105": "对抗路",
            "yxtxlb_8443": "https://game-1255653016.file.myqcloud.com/manage/custom_wzry_E1/bec9e5ca9cec82677f55a0c5c7af99a6.png",
            "fmb1lb_5300": "https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_B1/546989deab305f9631c98c85f67fb74d.jpg",
            "yxid_a7": "631",
            "yxpymc_4614": "fuluolun",
            "yxmclb_9965": "弗洛伦",
            "zzy_2397": "1",
            "fzy_8576": "",
            "sxsjlb_1516": "20260205",
            "yjhjsl_5003": "",
            "yxbq_3755": "3",
            "fmlb_4536": "https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_A1/fe0a932e1012ef5173ee0ceeb9f5a7b2.jpg",
            "pfmclb_5571": "百花剑豪&0",
            "mossid_6573": "",
            "spvidl_6663": "",
            "ztlb_6806": "",
            "fmc1lb_6498": "https://game-1255653016.file.myqcloud.com/manage/compress/custom_wzry_C1/53e2d5987b176a1bd040af2604ae0d3d.jpg"
        }
    ]
}
```



### bug

小图有一张有问题：

```
图片链接(https://game.gtimg.cn/images/yxzj/img201606/heroimg/142/142-bigskin-5.jpg)无效！响应状态码为：404
```

直接访问也是404。

### 辅助功能

统计英雄皮肤图片数量，拼接成json数据，为hexo博客提供每日切换背景图片的效果（现在有367张图片）。

主要程序：

- `DownloadBigskinWallpaper.java`：电脑壁纸，对应json：`wzry_mobile_367.json`
- `DownloadMobileskinWallpaper.java`：手机壁纸，对应json：`wzry_wallpaper367.json`

## 英雄联盟(LOL)

爬取方式：

1. 先将英雄介绍主页下载到本地（项目跟目录）；
2. 爬取该网页，获取英雄列表数据（包含单个英雄主页URL）；
3. 遍历爬取每一个英雄资料JSON数据，获得英雄皮肤ID，拼接URL；（因为单个英雄主页皮肤信息使用了js动态加载不能直接爬取，只能换个方式）
4. 下载图片。

### 相关信息

官网英雄介绍主页：<http://lol.qq.com/data/info-heros.shtml>

全部数据：<https://game.gtimg.cn/images/lol/act/img/js/heroList/hero_list.js>

单个英雄数据：`'//game.gtimg.cn/images/lol/act/img/js/hero/' + heroid + '.js'`

如：<http://game.gtimg.cn/images/lol/act/img/js/hero/1.js>

### 存在问题

#### 问题1：透明图片问题

单个英雄json后面皮肤几个皮肤好像只是简单的透明的模型，并没有完整的皮肤信息。

例如：<http://game.gtimg.cn/images/lol/act/img/js/hero/1.js>，完整的皮肤json应该是这样的

```json
{
    "skinId": "1013",
    "heroId": "1",
    "heroName": "黑暗之女",
    "heroTitle": "安妮",
    "name": "福牛守护者 安妮",
    "chromas": "0",
    "chromasBelongId": "0",
    "isBase": "0",
    "emblemsName": "Year of Ox",
    "description": "当安妮被选为福牛守护者的技术特工时，所有人都震惊了。安妮是一个早熟的神童，职责是团队的侦查战略家，确保巡游路线上没有平民。",
    "mainImg": "https://game.gtimg.cn/images/lol/act/img/skin/big1013.jpg",
    "iconImg": "https://game.gtimg.cn/images/lol/act/img/skin/small1013.jpg",
    "loadingImg": "https://game.gtimg.cn/images/lol/act/img/skinloading/1013.jpg",
    "videoImg": "https://game.gtimg.cn/images/lol/act/img/skinvideo/sp1013.jpg",
    "sourceImg": "https://game.gtimg.cn/images/lol/act/img/guidetop/guide1013.jpg",
    "vedioPath": "",
    "suitType": "",
    "publishTime": "",
    "chromaImg": ""
}
```

但是后面有这样的：不完整的

```json
{
    "skinId": "1014",
    "heroId": "1",
    "heroName": "黑暗之女",
    "heroTitle": "安妮",
    "name": "福牛守护者 安妮 贺岁",
    "chromas": "1",
    "chromasBelongId": "1013",
    "isBase": "0",
    "emblemsName": "",
    "description": "",
    "mainImg": "",
    "iconImg": "",
    "loadingImg": "",
    "videoImg": "",
    "sourceImg": "",
    "vedioPath": "",
    "suitType": "",
    "publishTime": "",
    "chromaImg": "https://game.gtimg.cn/images/lol/act/img/chromas/1/1014.png"
}
```

分析：简单研究下这两个json的区别，就会发现：

|      属性       | 完整 |   不完整   |
| :-------------: | :--: | :--------: |
|     chromas     |  0   |     1      |
| chromasBelongId |  0   | 对应皮肤id |
|   ~~isBase~~~    |  1   |     0      |
|    chromaImg    |  空  |     “”     |

查了下这个单词chroma：n. （色彩的）浓度，[光] 色度

因此我猜想 这个是基于某个皮肤的不同色彩的一个模型。

![1624775364069](assets/1624775364069.png)

##### 处理方法

这样处理的时候就需要特殊处理了，是把这种当做一个新的皮肤呢，还是不当？我考虑了两种方法：

1.（一开始的处理方法）当做新皮肤，爬取全量数据，遇到皮肤数据不全的，按照png图片进行特殊处理。

2.（后期可能会使用的处理方法）不当做新皮肤，通过那几个特殊属性来判断。

#### 问题2：图片404问题

完整皮肤信息的英雄的图片URL仍有可能会访问不了，404也只能简单跳过。

#### 问题3：mainImg值不规范

后面的数据mainImg这些值不规范了，会找不到照片。

暂时没有修复。

![image-20230709171619494](assets/image-20230709171619494.png)

### 统计

注意：统计仅以实际统计时间为准（不保证完全准确）。

2021年6月27日15:09:44的即为没有统计透明图片的。


|          时间           | 英雄数量 | 皮肤数量 |   png    | jpg  | 无效图片 |
| :---------------------: | :------: | :------: | :------: | :--: | :------: |
| 2021-06-27T11:30:33.570 |   155    |   3907   |   748    | 1374 |   1785   |
|  2021年6月27日15:09:44  |   155    |   3752   |    -     |  -   |    -     |
|  2021年6月27日17:00:33  |   155    |   3752   |    -     | 1217 |   2535   |
|  2021年7月10日16:12:33  |   155    |          | （实际） | 1389 |          |
|  2023年4月30日01:42:53  |   163    |          |          | 1680 |          |

### 2024年5月11日17:38:51问题处理

挺长时间了（最后一次能下载的时间是2023‎年‎11‎月‎11‎日，‏‎17:16:11），都只能下载对应的json，但是下载不了图片，都是404。

#### 问题分析

##### json 图片都访问不了

https://game.gtimg.cn/images/lol/act/img/center/c314073d-ff9d-433a-81cc-5081fc93b54a.jpg
https://game.gtimg.cn/images/lol/act/img/center/7f8c8fb2-fabd-4053-8818-395a0c94b904.jpg

##### 首页有效照片

1明烛
大
https://game.gtimg.cn/images/lol/act/img/skin/big_c314073d-ff9d-433a-81cc-5081fc93b54a.jpg
小
https://game.gtimg.cn/images/lol/act/img/skin/small_c314073d-ff9d-433a-81cc-5081fc93b54a.jpg

花仙贤者 米利欧
2大
https://game.gtimg.cn/images/lol/act/img/skin/big_7f8c8fb2-fabd-4053-8818-395a0c94b904.jpg

小

https://game.gtimg.cn/images/lol/act/img/skin/small_7f8c8fb2-fabd-4053-8818-395a0c94b904.jpg

#### 具体排查-安妮

https://lol.qq.com/data/info-defail.shtml?id=1 介绍页的现在的照片

安妮

小（smallskin：https://game.gtimg.cn/images/lol/act/img/skin/small1000.jpg）

https://game.gtimg.cn/images/lol/act/img/skin/small_8ad0a2a0-8492-44d6-8837-bab6ffc7e5af.jpg

大（bigskin：https://game.gtimg.cn/images/lol/act/img/skin/big1000.jpg）

https://game.gtimg.cn/images/lol/act/img/skin/big_8ad0a2a0-8492-44d6-8837-bab6ffc7e5af.jpg

手机（phone：https://game.gtimg.cn/images/lol/act/img/skinloading/1000.jpg）

暂未找到（下面这个不生效）

https://game.gtimg.cn/images/lol/act/img/skinloading/8ad0a2a0-8492-44d6-8837-bab6ffc7e5af.jpg

#### 按照旧的模式

旧的还能访问：（魔域梦魇 安妮）

https://game.gtimg.cn/images/lol/act/img/skin/small1031.jpg

但是后面出的皮肤就访问不了了：（北极星使者 安妮）

https://game.gtimg.cn/images/lol/act/img/skin/small1040.jpg

#### 新的模式



### 扩展

web大图

https://101.qq.com/#/hero-detail?heroid=1&tab=skin

电脑端大图

https://game.gtimg.cn/images/lol/act/img/skin/big_0b95894e-0df2-470e-b282-6c5f5cf41955.jpg

手机端大图

https://game.gtimg.cn/images/lol/act/img/skinloading/0b95894e-0df2-470e-b282-6c5f5cf41955.jpg



