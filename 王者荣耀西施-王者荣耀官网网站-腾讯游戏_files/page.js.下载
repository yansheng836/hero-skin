var G_biz = 18;
var G_Source = 'web';
var Type = "all";//初始化搜索类型
var TypeId = 0;//初始化搜索类型ID
var order = "sIdxTime"//初始化排序类型;
var Page = 1;//初始化页数
var PageSize = 6;//初始化每页显示多少条
var NewsSize = 9;//初始化每页显示多少条
var dataList = new Array(), suggData = new Array();
if ($('#history_content').html() !== 'undefined') {
    $('.history').show();
}
(function () {
    $.ajax({
        type: "get",
        url: "//pvp.qq.com/web201605/js/item.json",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            RunItem(data);
        },
        error: function (err) {
            // alert(err)
        }
    });
    $.ajax({
        type: "get",
        url: "//pvp.qq.com/web201605/js/summoner.json",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            RunSkill(data);
        },
        error: function (err) {
            // alert(err)
        }
    });
    $.ajax({
        type: "get",
        url: "//pvp.qq.com/web201605/js/ming.json",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            RunSugg(data);
        },
        error: function (err) {
            // alert(err)
        }
    });
})();
var Runbrowser = function () {
    var browser = navigator.appName,
        b_version = navigator.appVersion,
        version = parseFloat(b_version);
    if (browser == "Microsoft Internet Explorer" && version == "4") {
        return 'ie7';
    }
};
var RunSkin = function () {
    var skinlist = $('.pic-pf-list'),
        allname = skinlist.data('imgname').split('|'),
        skinbg = $('.zk-con1'),
        herotitle = $('.cover-title'), t = true, pf = $(".pic-pf .pf"),
        surl = '//game.gtimg.cn/images/yxzj/img201606/heroimg/' + ename + '/' + ename,
        burl = "//game.gtimg.cn/images/yxzj/img201606/skin/hero-info/" + ename + '/' + ename,
        shtml = '';
    function wrap (a, b) {
        for (var i = a; i < b; i++) {
            var bskin = burl + "-bigskin-" + (i + 1) + ".jpg";
            shtml += '<li><i' + (i == 0 ? ' class="curr"' : '') + '><img src="' + surl + '-smallskin-' + (i + 1) + '.jpg" alt="" data-imgname="' + bskin + '" data-title="' + allname[i] + '"></i><p>' + allname[i] + '</p></li>';
        };
    }
    wrap(0, allname.length);
    skinlist.html(shtml);
    skinlist.on('mouseover', 'li', function () {
        $(this).find("i").addClass("curr");
        $(this).siblings().find("i").removeClass("curr");
        var imgname = $(this).find("img").data('imgname');
        var bImg = new Image();
        bImg.src = imgname;
        bImg.onload = function () {
            skinbg.css('background', 'url(' + imgname + ') center 0');
        }
        herotitle.html($(this).find("img").data('title'));
    });
    pf.html('皮<br />肤');
    var n1 = $('.sugg-skill img').eq(0).attr('src')[$('.sugg-skill img').eq(0).attr('src').length - 6],
        n2 = $('.sugg-skill img').eq(1).attr('src')[$('.sugg-skill img').eq(1).attr('src').length - 6];
    $('.sugg-name span').eq(0).text($('.skill-show .skill-name b').eq(n1).text());
    $('.sugg-name span').eq(1).text($('.skill-show .skill-name b').eq(n2).text());


}();
//tab切换 S
$(function () {
    $(".skill-u1 li").hover(function () {
        $(this).addClass("curr").siblings().removeClass("curr");
        $(".show-list").eq($(".skill-u1 li").index(this)).show().siblings().hide();
        var n = $(this).index() + 1;
        PTTSendClick('skill', 'nav' + n, '技能' + n);
    });
    $(".hero-hd li").hover(function () {
        $(this).addClass("curr").siblings().removeClass("curr");
        $(".hero-info").eq($(".hero-hd li").index(this)).show().siblings().hide();
        var $e = $(".hero-list-desc").eq($(".hero-hd li").index(this));
        $e.css('margin-top', ("-" + $e.height() / 2 + "px"));
    });
    var $e = $(".hero-list-desc").eq(0);
    $e.css('margin-top', ("-" + $e.height() / 2 + "px"));
    $(".equip-hd li").hover(function () {
        var n = $(this).index();
        $(this).addClass("curr").siblings().removeClass("curr");
        $(".equip-info").hide().eq($(".equip-hd li").index(this)).show();
        PTTSendClick('equipment', 'nav' + (n + 1), '出装' + (n + 1));
    });
    $('.hero-info-box').on('mouseover', 'li', function () {
        var n = $(this).index();
        $(this).addClass('cur').siblings('li').removeClass('cur');
        var $p = $(this).parent().parent().siblings('.hero-list-desc').find('p');
        $p.eq(n).show().siblings('p').hide();
        var $e = $p.eq(n).parent();
        $e.css('margin-top', ("-" + $e.height() / 2 + "px"));
        PTTSendClick('hero', 'relate' + (n + 1), '英雄关系' + (n + 1));
    });
});
//tab切换 E

// 生成召唤师技能
var RunSkill = function (skillList) {
    var skillArray = new Array(), shtml = '',
        dataskill = $("#skill3").data('skill'),
        skillArray = dataskill.length > 1 ? dataskill.split('|') : dataskill;
    hName = [];
    for (var i = 0; i < skillArray.length; i++) {
        $.each(skillList, function () {
            if (this.summoner_id == skillArray[i]) {
                var bskin = "//game.gtimg.cn/images/yxzj/img201606/summoner/" + this.summoner_id + ".jpg";
                shtml += '<p class="icon sugg-skill"><img src="' + bskin + '" alt="" class="jn-pic1" width="47" height="47" alt="技能3"></p>';
                hName.push(this.summoner_name);
            }
        })
    };
    $('.sugg-name3 span').text(hName.join('/'));
    $("#skill3").html(shtml);
};
// 生成制约关系
var RunRelate = function () {
    var relatelist = $('.hero-relate-list'),
        surl = '//game.gtimg.cn/images/yxzj/img201606/heroimg/';

    for (var i = 0; i < relatelist.length; i++) {
        var shtml = '';
        (function (idx) {
            var relatename = $(relatelist[idx]).data('relatename').split('|');
            for (var j = 0; j < relatename.length; j++) {
                shtml += '<li><a href="' + relatename[j] + '.shtml"><img src="' + surl + relatename[j] + '/' + relatename[j] + '.jpg" alt=""></a></li>';
            }
            $(relatelist[idx]).html(shtml);
        })(i);
    }
};
// 生成出装
var RunItem = function (data) {
    var equiplist = $('.equip-list'),
        surl = '//game.gtimg.cn/images/yxzj/img201606/itemimg/';
    for (var i = 0; i < equiplist.length; i++) {
        var shtml = '';
        (function (idx) {
            var dataItem = $(equiplist[idx]).data('item'), equipname = new Array(),
                len = dataItem.length;
            if (len > 1) {
                equipname = dataItem.split('|');
            } else {
                equipname.push(dataItem);
            }
            for (var j = 0; j < equipname.length; j++) {
                $.each(data, function () {
                    if (this.item_id == equipname[j]) {
                        var item_name = this.item_name;
                        shtml += '<li><a href="javascript:;"><img src="' + surl + equipname[j] + '.jpg" alt="">';
                        shtml += '<div class="itemFromTop"><div class="item-title clearfix"><img src="' + surl + equipname[j] + '.jpg" alt="" id="Jpic"><div class="cons"><h4 id="Jname">' + this.item_name + '</h4><p id="Jprice">售价：' + this.price + '</p><p id="Jtprice">总价：' + this.total_price + '</p></div></div><div class="item-desc">' + (this.des1 || '') + (this.des2 || '') + '</div></div></a></li>'
                    }
                })
            }
            $(equiplist[idx]).html(shtml);
        })(i);
    }
};
// 生成铭文
var RunSugg = function (suggData) {
    var sugglist = $('.sugg-u1'),
        surl = '//game.gtimg.cn/images/yxzj/img201606/mingwen/';
    for (var i = 0; i < sugglist.length; i++) {
        var shtml = '';
        (function (idx) {
            var suggname = $(sugglist[idx]).data('ming').split('|');

            for (var j = 0; j < suggname.length; j++) {
                $.each(suggData, function () {
                    if (this.ming_id == suggname[j]) {
                        var ming_name = this.ming_name;
                        var ming_des = this.ming_des;
                        shtml += '<li><img src="' + surl + suggname[j] + '.png" class="sugg-pic1" width="45" height="53" alt="" /><p><em>' + ming_name + '</em></p>' + ming_des + '</li>';
                    }
                })
            }
            $(sugglist[idx]).html(shtml);
        })(i);
    }
};



// 技能超过4个的特殊处理代码 add by sonic 2017-06-06
(function () {
    var skillNo5 = $(".skill-show .show-list").eq(4).find(".skill-name b").html();
    if (skillNo5 != "" && skillNo5 != "undefined" && skillNo5 != "") {
        $("ul.skill-u1").addClass("skill-u1new")
        $('.no5 img').attr('src', $('.no5').attr('data-img'));
    }
})()

$('.story').bind('click', function () {
    showDialog.show({
        id: 'hero-story'
    });
    PTTSendClick('btn', 'story', '英雄故事');
});
if ($('#history .pop-bd p').text() == 'undefined' || $('#history .pop-bd p').text() == '') {
    $('.history').hide();
} else {
    $('.history').bind('click', function () {
        showDialog.show({
            id: 'history'
        });
        PTTSendClick('btn', 'history', '英雄历史');
    });
}
$('.more').bind('click', function () {
    PTTSendClick('strategy', 'more', '更多');
});


//------------------------------------------- 通过新V4接口取英雄视频/列表 （add by sonic 2018-06-08）-----------
// LoadSubTypeLists(1,1269,'杨戬',0,200,178,1);
// newBeeHeroDataFill.pick(6,'项羽',135)

var newBeeheroData1 = {}, newBeeheroData2 = {}, newbee_hero_list_callback = function () { };
var newBeeHeroDataFill = (function () {
    var newBeeheroDataUrl1 = '//gicp.qq.com/wmp/data/js/v3/WMP_PVP_WEBSITE_NEWBEE_DATA_V1.js',
        newBeeheroDataUrl2 = '//gicp.qq.com/wmp/data/js/v3/WMP_PVP_WEBSITE_DATA_18_VIDEO_V3.js';

    var util = {},
        init = function () { };

    util.jsLoader = function (url, callback, options) {
        options = options || {};
        var head = document.getElementsByTagName('head')[0] || document.documentElement,
            script = document.createElement('script'),
            done = false;
        script.src = url;
        if (options.charset) {
            controller
            script.charset = options.charset;
        }
        script.onerror = script.onload = script.onreadystatechange = function () {
            if (!done && (!this.readyState || this.readyState == "loaded" || this.readyState == "complete")) {
                done = true;
                if (callback) {
                    callback();
                }
                script.onerror = script.onload = script.onreadystatechange = null;
                head.removeChild(script);
            }
        };
        head.insertBefore(script, head.firstChild);
    },

        // 英雄视频图文接口请求
        util.pickHeroVideo = function (dataNum, roleName, roleId) {
            if (!dataNum || !roleName || !roleId) { return }
            if (!newBeeheroData1.video) {
                // console.log("getScript newBeeheroDataUrl1")
                $.ajax({
                    type: 'GET', url: newBeeheroDataUrl1, dataType: 'jsonp', jsonpCallback: 'newbee_hero_list_callback',
                    success: function (data) {
                        newBeeheroData1 = data; //取得第一个接口数据

                        $.ajax({
                            type: 'GET', url: newBeeheroDataUrl2, dataType: 'jsonp', jsonpCallback: 'web_hero_list_v3',
                            success: function (data) {
                                newBeeheroData2 = data; //取得第二个接口数据
                                util.asetHeroVideoHtml(dataNum, roleName, roleId);
                            }, error: function () { console.log('get newBeeheroDataUrl2 error!') }
                        });
                    },
                    error: function () { console.log('get newBeeheroDataUrl1 error!') }
                });
            } else {
                util.asetHeroVideoHtml(dataNum, roleName, roleId);
            }
        },

        // 英雄视频图文取回数据填充
        util.asetHeroVideoHtml = function (dataNum, roleName, roleId) {
            // 从第一个接口取两个播放量最高的视频
            var ahtml = newBeeheroData1.video[roleName];
            ahtml.sort(function (a, b) { return b.iTotalPlay.substring(0, b.iTotalPlay.length - 1) - a.iTotalPlay.substring(0, a.iTotalPlay.length - 1); }) //按播放数重排数组
            ahtml = util.pushDataHeroVideo(ahtml, 2, 'videoindexType', 0);
            // console.log(ahtml);

            // 从第二个接口取 4条进阶功略视频
            var bhtml = newBeeheroData2[roleIdPair[roleId]].jData;
            bbhtml = util.pushDataHeroVideo(bhtml, 4);
            // console.log(bhtml);

            var chtml = ahtml + bbhtml;
            $('.video .video-u1').html(chtml);
            // console.log(chtml);

            // 从第二个接口取 10条进阶功略图文或视频(排除头4条)
            var nhtml = util.pushDataHeroNews(bhtml, 10, 4);
            $('.strategy .strategy-info').html(nhtml);
            // console.log(nhtml);
        },

        // 拼装英雄视频html
        util.pushDataHeroVideo = function (data, num) {
            var RetHTML = '',
                newArr = [];

            for (var i = 0; i < data.length; i++) {
                if ('iVideoId' in data[i]) {
                    newArr.push(data[i]);
                }
            }
            data = newArr;

            var length = data.length > num ? num : data.length;

            for (var x = 0; x < length; x++) {

                x % 4 == 0 ? RetHTML += '<li  class="li1">' : RetHTML += '<li>';
                RetHTML += '<a href="/v/detail.shtml?G_Biz=' + G_biz + '&tid=' + data[x]['iVideoId'] + '" title="' + data[x]['sTitle'] + '" onclick="PTTSendClick(\'link\',\'videoraider\',\'视频攻略\');" target="_blank">';
                RetHTML += '<img src="' + data[x]['sIMG'].replace('http://', 'https://') + '" width="172" height="104" alt="' + data[x]['sTitle'] + '" />';
                RetHTML += '<p class="p1">' + data[x]['sTitle'] + '</p>';
                RetHTML += '<div class="video-play">';
                RetHTML += '<p class="play-num fl"><i class="icon play"></i>' + data[x]['iTotalPlay'] + '</p> <p class="play-time fl">' + ReloadPubdate(data[x]['sIdxTime']) + '</p>';
                RetHTML += '</div>';
                RetHTML += '</a>';
                RetHTML += '</li>';

            }
            return RetHTML;
        },

        // 拼装英雄图文html
        util.pushDataHeroNews = function (data, num, index) {
            var RetHTML = '',
                length = (data.length - index) > num ? num + index : data.length,
                index = index || 0;

            for (var x = index; x < length; x++) {
                if (data[x].iVideoId) {
                    RetHTML += '<li><em class="subscript">视频</em><a class="title" href="/v/detail.shtml?G_Biz=' + G_biz + '&tid=' + data[x]['iVideoId'] + '"  target="_blank" title="' + data[x]['sTitle'] + '" onclick="PTTSendClick(\'link\',\'heroraiderVideo\',\'视频攻略\');">';
                } else {
                    RetHTML += '<li><em class="subscript">图文</em><a class="title" href="/web201605/newsDetail.shtml?G_Biz=' + G_biz + '&tid=' + data[x]['iNewsId'] + '"  target="_blank" title="' + data[x]['sTitle'] + '" onclick="PTTSendClick(\'link\',\'heroraiderNews\',\'图文攻略\');">';
                }
                RetHTML += '<span>' + ReloadPubdate(data[x]['sIdxTime']) + '</span><p class="p-dec">' + data[x]['sTitle'] + '</p>';
                RetHTML += '</a>';
                RetHTML += '</li>';
            }
            return RetHTML;
        },


        util.init = function (dataNum, roleName, roleId) {
            if (typeof roleIdPair != "object") {
                util.jsLoader("//game.gtimg.cn/images/yxzj/web201706/js/heroid.js", function(){util.pickHeroVideo(dataNum, roleName, roleId)})
            } else {
                util.pickHeroVideo(dataNum, roleName, roleId)
            }
        };


    return {
        init: function (dataNum, roleName, roleId) { util.init(dataNum, roleName, roleId) }
    }
})();
if (cname && ename) {
    newBeeHeroDataFill.init(6, cname, ename)
}
//------------------------------------------- 通过新V4接口取英雄视频/列表  End ---------------------------------