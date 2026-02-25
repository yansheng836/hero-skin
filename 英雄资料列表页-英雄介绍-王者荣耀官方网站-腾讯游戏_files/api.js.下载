/**
 * 王者官网公共接口的js
 * 内置了aixos组件和md5组件
 */

var SERVICE_ID = 18
var DOMAIN = 'https://pvp.qq.com'
var TX_VIDEO_BUSINESS_CONFIG = {
  appKey: '100QB0ZCgICBQlCAAcBB08GUh4KHFsdSxsZHR8cGBMdGhxGFhdSHEtCDEwMDx4QSE8HDBAFHRkCFw5ECQMOFgEQXxAPQF5DTQMSDR4MGB4RR1wHExBeHk5GHx8bEQEaARwTBRtOXx8VGgMWFxAHGhcFXxYCTgIMFk8DFUkTABAUQQ4BHxMFDElEGAQUGV8NAE4MAghYGk0ZChsZBE5YBVYVBEUEAFxHSEFbRAQAWEVIRBcRT0UBHwkDBE0KBQVNSkdaG0wHGUUJGV5HGRVSTAxPCB9MQgQaDhJfDQgUAgMAHgMBEQ8aEh9CHRwJRg8RT0ZYQR4TCAMVBhFDSh4YABUYBUBAQh1CDRQOGhpFHkIVBQUCT0QFAxQEDERJHR8WTQYRTEgVH0cWRFoZAhtaHw4ZAQEZGwgGH0EFGUAaAUBPT1o',
  platform: '8330701',
	sdtfrom: 'v7102',
}

/**
 *  动态加载js文件
 *  url: 请求js链接
 *  cb: 请求成功后的回调函数
 **/
function loadScript(url, callback) {
  var script = document.createElement('script');
  script.type = 'text/javascript';
  script.src = url;
  document.body.appendChild(script);
  script.onload = function () {
    // console.log('script loaded successfully.');
    typeof callback === 'function' && callback();
  };
}

/**
 *  jsonp请求
 *  url: 请求链接
 *  jsonpCallbackStr: jsonp回调的变量名
 *  cb: 请求成功后的回调函数（参数为请求结果数据）
 **/
function jsonpRequest(url, jsonpCallbackStr, cb) {
  window[jsonpCallbackStr] = cb
  loadScript(url+'?callback='+jsonpCallbackStr)
}

/**
 * getGicpNewsList params参数说明
 * source: 来源，web_pc pc端， web_m 移动端，【必填】
 * filter: 过滤字段，channel 默认, tag 赛事中心
 * page: 页数
 * limit: 每页数量
 * channelId: 频道id，1760 热门，1761 新闻，1762 公告，1763 活动，1764 赛事
 **/
function getGicpNewsList(params, cb){
  var limit = params.limit || 8
  var page = params.page || 1
  var start = (page - 1)*limit
  var reqParams = {
    serviceId: SERVICE_ID,
    filter: params.filter ||'channel',
    sortby: 'sIdxTime',
    source: params.source,
    limit: params.limit || 8,
    logic: 'or',
    typeids: params.typeids || '1',
    withtop: page==1 ? 'yes' : 'no',
    chanid: params.channelId,
    start
  }
  var exclusive = makeSign(reqParams.source)
  reqParams.exclusiveChannel = exclusive.exclusiveChannel
  reqParams.exclusiveChannelSign = exclusive.exclusiveChannelSign
  reqParams.time = exclusive.time

  return axios.get('https://apps.game.qq.com/cmc/cross', {
    params: reqParams
  }).then(function(res){
    typeof cb === 'function' && cb(res.data)
    return res.data
  })
}

// gicp请求的签名算法
function makeSign(source) {
  // source: web_pc / web_m
  var timestamp = parseInt(+new Date / 1000)
  var token
  var exclusiveChannel
  if(source === 'web_pc') {
    // pc
    token = '234ce0aef3020cb83887883877b64869'
    exclusiveChannel = 4
  }else{
    // m
    token = '75f25d088eb5e9ffdf2a3ef7feb2a338'
    exclusiveChannel = 5
  }
  var sign = md5(token + source + SERVICE_ID + timestamp)
  return {
    exclusiveChannel,
    exclusiveChannelSign: sign,
    time: timestamp
  }
}

var GICP_TAG_MAP = {
  top: { name: '置顶', color: '#fab338', bold: 1 },
  hot: { name: '热门', color: '#e7653e' },
  news: { name: '新闻', color: '#515c71' },
  notify: { name: '公告', color: '#9b7054' },
  act: { name: '活动', color: '#607755' },
  events: { name: '赛事', color: '#b9a67c' }
}
/**
* getNewsSpanInfo 获取标签对应的名字颜色函数 参数说明
* channelId: 频道id
* sTagIds: gicp返回的标签字段
**/
function getNewsSpanInfo(channelId, isTop) {
  var key = 'hot'
  if(parseInt(isTop, 10) ===1) {
    key = 'top'
  } else if(channelId) {
    if (channelId.indexOf('1761') !== -1) {
      key = 'news'
    } else if (channelId.indexOf('1762') !== -1) {
      key = 'notify'
    } else if (channelId.indexOf('1763') !== -1) {
      key = 'act'
    } else if (channelId.indexOf('1764') !== -1) {
      key = 'events'
    } else {
      // if(sTagIds) {
      //   if(sTagIds.indexOf('655')>-1){
      //     key = 'news'
      //   }else if(sTagIds.indexOf('656')>-1){
      //     key = 'notify'
      //   }else if(sTagIds.indexOf('1139')>-1){
      //     key = 'act'
      //   }else if(sTagIds.indexOf('658')>-1){
      //     key = 'events'
      //   }
      // }
    }
  }
  return GICP_TAG_MAP[key]
}

/**
 * getGicpNewsDetail params参数说明
 * source: 来源，web_pc pc端， web_m 移动端，【必填】
 * id: 资讯id
 * type: news / video
 **/
function getGicpNewsDetail(params, cb) {
  var api = params.type === 'video' ? 'search.php' : 'searchNews.php'
  var url = 'https://apps.game.qq.com/wmp/v3.1/public/'+ api +'?p0=' + SERVICE_ID + '&source=' + (params.source || 'web_m') + '&id=' + params.id
  loadScript(url, function(){
    typeof cb === 'function' && cb(window.searchObj)
  })
}

/**
 * 给gicp上报资讯的阅读数 params参数说明
 * source: 来源，web_pc pc端， web_m 移动端，【必填】
 * id: 资讯id
 **/
function reportGicpPlayData(params, cb) {
  var url = '//apps.game.qq.com/wmp/v3.1/?p0='+ SERVICE_ID +'&p1=updateTotalPlay&p2='+params.id+'&p3=1&p5=1&source=' + (params.source || 'web_m')
  loadScript(url, function(){
    typeof cb === 'function' && cb()
  })
}

/**
* getMossNewsList params参数说明
* page: 页数
* limit: 每页数量
* type: 0 图文和视频，1 图文，2 视频
* channelId: moss频道id，3491 最新，5010 峡谷大气层，3543 马菠萝奇闻录，3560 狄仁杰封神榜，4925 游走基本法，4942 野王修炼手册，4959 法王的荣耀
**/
function getMossNewsList(params, cb){
  var limit = params.limit || 8
  var start = params.page ? (params.page - 1)*limit : 0
  var type = params.type || 0
  var reqParams = {
    game: 'smoba',
    order: 1,
    src: 0,
    type,
    start,
    num: limit,
    ingame_channel_id: params.channelId
  }
  return axios.get('https://app.ingame.qq.com/gingame/content/content_list_by_channel', {
    params: reqParams
  }).then(function(res){
    typeof cb === 'function' && cb(res.data)
    return res.data
  })
}

function getHeroSkinList(cb) {
  return axios.get('/zlkdatasys/heroskinlist.json').then(function(res){
    var HERO_TYPE_NAME = {
      1: '战士', 2: '法师', 3: '坦克', 4: '刺客', 5: '射手', 6: '辅助'
    }
    var data = res.data
    var skinList = data.pflb20_3469
    var sLength = skinList.length
    var heroList = data.yxlb20_2489
    var hLength = heroList.length
    var skinListResult = []
    var heroListResult = []
    for (var i = 0; i < sLength; i++) {
      var skin = skinList[i]
      skinListResult.push({
        id: skin.pfidlb_3934,
        title: skin.pfmclb_7523,
        hero: skin.yxmclb_9965,
        intro: skin.yjhjsl_5003,
        avatar: skin.yxtxlb_8443,
        date: skin.sxsjlb_1516,
        vid: skin.spvidl_6663,
        quality: skin.pfpzlb_3289,
        buy: skin.hqfs_8609,
        gif: skin.pfgift_4455,
        cover: skin.fmlb_4536,
        coverb1: skin.fmb1lb_5300,
        tag: 'https://game.gtimg.cn/images/yxzj/ingame/skin/icon/'+skin.pfbqlb_1811+'.png',
        mlink: skin.mdljlb_1924,
        pclink: skin.pcljlb_9272
      })
    }
    var ylzzIdName = 'yuanliuzhizi'
    for (var j = 0; j < hLength; j++) {
      var hero = heroList[j]
      var id = hero.yxid_a7;
      var ename = hero.yxpymc_4614
      var tag = hero.yxbq_3755 // 默认不填 新英雄，1 重塑英雄，2 品质升级
      var blLinkKey = tag == 1 ? 'rebuild/' :'heros/'
      var blMLinkKey = tag == 1 ? 'rebuild/' :'newheros/'
      // pc端爆料站 需兼容元流之子（爆料页只有一个，制胜宝典有两个），
      var blIdName = ename.indexOf(ylzzIdName)!==-1 ? ylzzIdName : ename
      var skin = hero.pfmclb_5571
      var type = hero.zzy_2397
      var type2 = hero.fzy_8576
      heroListResult.push({
        id,
        hero: hero.yxmclb_9965,
        ename,
        intro: hero.yjhjsl_5003,
        // avatar: 'https://game.gtimg.cn/images/yxzj/img201606/heroimg/'+id+'/'+id+'.jpg',
        avatar: hero.yxtxlb_8443,
        details: '/zlkdatasys/zsbd_herolist/'+id+'.json',
        date: hero.sxsjlb_1516,
        vid: hero.spvidl_6663,
        mossid: hero.mossid_6573,
        cover: hero.fmlb_4536,
        coverb1: hero.fmb1lb_5300,
        coverc1: hero.fmc1lb_6498,
        tag,
        type, // 主职业
        typeName: HERO_TYPE_NAME[type],
        type2,// 副职业
        typeName2: type2 ? HERO_TYPE_NAME[type2] : '',
        skin,
        title: skin ? skin.split('|')[0].split('&')[0] : '',
        status: hero.ztlb_6806, // 10 每周限免，11 新手推荐
        mlink: DOMAIN + '/ingame/all/tobe/'+blMLinkKey+blIdName+'.html',
        pclink: DOMAIN + '/coming/v2/'+blLinkKey+blIdName+'.shtml',
        pcLatestCover: 'https://game.gtimg.cn/images/yxzj/img201606/herolatest/pc/'+ename+'.jpg'
      })
    }
    var result = {
      skin: skinListResult,
      hero: heroListResult
    }
    typeof cb === 'function' && cb(result)
    return result
  });
}

// 将YYYYMMDD格式转成YYYY{连接符}MM{连接符}DD
function transformDate(dateStr, joinStr, afterFix) {
  try {
    if(dateStr === '即将上线') {
      return dateStr
    }else{
      if(joinStr==='MD') {
        // 返回 M月D日 格式
        return dateStr.slice(4, 6) + '月' + dateStr.slice(6, 8) + '日' + (afterFix||'')
      }else{
        return dateStr.slice(0, 4) + joinStr + dateStr.slice(4, 6) + joinStr + dateStr.slice(6, 8)
      }
    }  
  } catch (error) {
    return '未知'
  }
}

function sortByDate(a, b) {
  if(a.date === '即将上线') {
    return -1
  }else if(b.date === '即将上线') {
    return 1
  }else {
    return b.date - a.date
  }
}

// 获取全部/最新/周免英雄函数【TODO 待废弃】
// hero_type 和 hero_type2 的职业对应关系
    // var typeMap = {
    //   3: '坦克',
    //   1: '战士',
    //   2: '法师',
    //   4: '刺客',
    //   5: '射手',
    //   6: '辅助',
    //   10: '限免',
    //   11: '新手'
    // }
// upgrade: 1 为品质升级英雄
function getHeroList(cb){
  return axios.get('/web201605/js/herolist.json').then(function(res){
    var arr = res.data
    var length = arr.length
    var freeHero = []
    var ylzzIdName = 'yuanliuzhizi'
    for(var i=0; i < length; i++) {
      var item = arr[i]
      var ename = item.ename
      var id_name = item.id_name
      item.avatar = 'https://game.gtimg.cn/images/yxzj/img201606/heroimg/'+ename+'/'+ename+'.jpg'
      item.details = '/zlkdatasys/zsbd_herolist/'+ename+'.json'
      var blLinkKey = item.upgrade ? 'rebuild/' :'heros/'
      // pc端爆料站 需兼容元流之子（爆料页只有一个，制胜宝典有两个），
      var blIdName = id_name.indexOf(ylzzIdName)!==-1 ? ylzzIdName : id_name
      item.pcLink = DOMAIN + '/coming/v2/'+blLinkKey+blIdName+'.shtml'
      item.blJson = '/zlkdatasys/data_zlk_'+blIdName+'.json'
      // item.mLink = DOMAIN + '/web201605/herodetail/m/'+ename+'.html' // m爆料地址无规律，在herolist.json录入m_bl_link字段
      item.pcFreeLink = DOMAIN + '/web201605/herodetail/'+id_name+'.shtml'
      item.mFreeLink = DOMAIN + '/web201605/herodetail/m/'+ename+'.html'
      item.mLatestCover = 'https://game.gtimg.cn/images/yxzj/img201606/herolatest/m/'+id_name+'.jpg'
      item.pcLatestCover = 'https://game.gtimg.cn/images/yxzj/img201606/herolatest/pc/'+id_name+'.jpg'
      item.mFreeCover = 'https://game.gtimg.cn/images/yxzj/img201606/herofree/m/'+id_name+'.jpg'
      item.pcFreeCover = 'https://game.gtimg.cn/images/yxzj/img201606/herofree/pc/'+id_name+'.jpg'
      if(item.pay_type == 10){
        freeHero.push(item);
      }
    }
    arr.reverse().sort(function(a, b) {
      var aT = strToDate(a.time), 
          bT = strToDate(b.time)
      if (aT && bT) {
        return bT.getTime() - aT.getTime()
      } else {
        return false
      }
    })
    var result = {
      all: arr,
      free: freeHero,
      latest: arr.slice(0,7)
    }
    typeof cb === 'function' && cb(result)
    return result
  })
}

// 获取最新皮肤【TODO 待废弃】
function getLatestSkin(cb){
  try {
    return axios.get('/zlkdatasys/syzxpf.json').then(function(res){
      var data = res.data.syblpf_3942
      typeof cb === 'function' && cb(data)
      return data
    })
  }catch(error) {
    console.log('getLatestSkin error:', error)
  }
}

// 获取当前服务器时间
function getServerTime(callback) {
  loadScript('//apps.game.qq.com/CommArticle/app/reg/gdate.php?t=' + new Date().getTime(), function () {
    var serverDate = json_curdate,
      date = new Date(serverDate.replace(/-/g, "/"));
    callback && callback(date);
  });
}

// 获取当前星期的日期
function getWeeklyDate(cb){
  getServerTime(function(time){
    var d = time || new Date();
		var day = d.getDay();
    var dayOffset = {
      0: [1,7],
      1: [0,6],
      2: [-1, 5],
      3: [-2, 4],
      4: [-3, 3],
      5: [-4, 2],
      6: [-5, 1]
    }
    var d1 = getDateByOffset(d, dayOffset[day][0])
    var d2 = getDateByOffset(d, dayOffset[day][1])
    typeof cb==='function' && cb({d1, d2})
  })
}

function getDateByOffset(date, offset) {
  var dateSet = date || new Date(),
      offset = offset || 0;
  var h = new Date();
  h.setDate(dateSet.getDate() + offset);
  return h;
}

function strToDate(str, splitStr) {
  if(!str) {
    return new Date('2015/01/01')
  }
  if(str==='即将上线') {
    return new Date()
  }
  var strArr = str.split(splitStr || '.')
  return new Date(strArr.join("/"))
}

// 获取IP英雄数据
function getIpHero(cb) {
  try {
    return axios.get('/zlkdatasys/yuzhouzhan/list/heroList.json').then(function(res){
      typeof cb === 'function' && cb(res.data.yzzyxs_4880)
      return res.data.yzzyxs_4880
    })
  }catch(error) {
    console.log('getIpHero error:', error)
  }
}

// 获取IP皮肤数据
function getIpSkin(cb){
  try {
    return axios.get('/zlkdatasys/yuzhouzhan/pfjs.json').then(function(res){
      typeof cb === 'function' && cb(res.data.pflbzt_8051)
      return res.data.pflbzt_8051
    })
  }catch(error) {
    console.log('getIpSkin error:', error)
  }
}

// 获取IP动画数据
function getIpAnimation(cb){
  try {
    return axios.get('/zlkdatasys/yuzhouzhan/video/list.json').then(function(res){
      typeof cb === 'function' && cb(res.data.yzzdhs_6406)
      return res.data.yzzdhs_6406
    })
  }catch(error) {
    console.log('getIpSkin error:', error)
  }
}

// 获取所有英雄攻略信息
function getHeroStrategy(cb) {
  var result = {}
  try {
    var nameUrl = '//gicp.qq.com/wmp/data/js/v3/WMP_PVP_WEBSITE_NEWBEE_DATA_CH_V1.js' // 根据英雄名获取攻略列表
    //【该数据源 新英雄基本没数据】
    var idUrl = '//gicp.qq.com/wmp/data/js/v3/WMP_PVP_WEBSITE_DATA_18_VIDEO_CH_V3.js' // 根据英雄id获取攻略列表
    
    jsonpRequest(nameUrl, 'newbee_hero_list_callback', function(data){
      // console.log(data)
      result.nameArr = data ? data.video : data
      jsonpRequest(idUrl, 'web_hero_list_v3', function(data){
        // console.log(data)
        result.idArr = data
        typeof cb === 'function' && cb(result)
      })
    })
    
  }catch(error) {
    console.log('getHeroStrategy error:', error)
    typeof cb === 'function' && cb(result)
  }
}

/**
 * 获取 idata 广告位数据
 * @param {object} params 传参
 * @param {string} params.ggID 填写轮播广告所属的频道ID（"频道管理"-"频道ID"，比如：‘15191’）
 * @param {array} params.needAds 需要的广告位id（字符串数组，比如：['16040', '16041', '16042', '16043', '16044']，不传值则返回全部广告位数据）
 * @param {object} cb 回调函数
 */
function getIdataAds(params, cb) {
  try {
    ggID = (params && params.ggID) ? params.ggID : '';
    return axios.get('//ossweb-img.qq.com/images/clientpop/idata_ad/idata_ad_'+ ggID +'.js').then(function(res){
      // console.log('getIdataAds res=', res);
      var subBegin = ('var gAds' + ggID + "=").length; // 截断开头
      var jsonData = JSON.parse(res.data.substring(subBegin, res.data.length)).common; // 转为 json

      // 筛选出需要的广告位
      var resultList = [];
      Object.keys(jsonData).forEach(function(key){
        if(!params.needAds || params.needAds.indexOf(key) !== -1){
          resultList.push(jsonData[key]);
        }
      });
      typeof cb === 'function' && cb(resultList);
    })
  }catch(error) {
    console.log('getIdataAds error:', error);
  }
}

/**
 * 获取七彩石数据
 * @param {boolean} homepageDev 是否为官网测试环境
 * @param {object} cb 回调函数
 */
function getRainbowAds (homepageDev, cb) {
  try {
    var list = {banner_list: [], source_list: []};
    // var url = homepageDev ? 'https://test.jsonschema.qpic.cn/fac750077bebec088ac5d7e897b18673/8ddba98138baa618abeb770129e2b9aa/pvp_swipers' : 'https://jsonschema.qpic.cn/fac750077bebec088ac5d7e897b18673/8ddba98138baa618abeb770129e2b9aa/pvp_swipers'; // 地址1
	var url = 'https://wuji-1254960240.file.myqcloud.com/5d8330973f9b5266a16db3d6f889238a/8213/m1293/1.json'; // 地址2
    getServerTime((date) => {
      var maxPosition = 0;
      var now = date.getTime();
      axios.get(url).then(function(res){
        list.source_list = res.data.banner_list; // 原始数据
        var temp = null;
        var position = 1;
        for (var i = 0; i < res.data.banner_list.length; i++) {
          temp = res.data.banner_list[i];
          position = temp.position ? temp.position : 1;
          maxPosition = Math.max(maxPosition, position);
          if (now >= new Date(temp.start_time).getTime() && now <= new Date(temp.end_time).getTime()) {
            // 在上架时间内
            if(list.banner_list[position - 1]) {
              // 放入开始时间最早的一个
              if(new Date(temp.start_time).getTime() < new Date(list.banner_list[position - 1].start_time).getTime()) {
                list.banner_list[position - 1] = temp;
              }
            } else {
              list.banner_list[position - 1] = temp;
            }
          }
        }

        // 补齐空数据（有分配广告位但没有实际广告内容的情况）
        for(var j = 0; j < maxPosition; j++) {
          if(!list.banner_list[j]) {
            list.banner_list[j] = {
              title: '（无数据或未在有效上架时间内）',
              start_time: '',
              end_time: '',
              img_name: '',
              link: '',
              material_img: '',
              position: j + 1,
            };
          }
        }
        // console.log('七彩石数据：', list);

        typeof cb === 'function' && cb(list);
      });
    });
  }catch(error){
    console.log('getRainbowAds error:', error);
  }
}


// https://axios-http.com/zh/docs/intro
!function(e,t){"object"==typeof exports&&"undefined"!=typeof module?module.exports=t():"function"==typeof define&&define.amd?define(t):(e="undefined"!=typeof globalThis?globalThis:e||self).axios=t()}(this,(function(){"use strict";function e(t){return e="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},e(t)}function t(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function n(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function r(e,t,r){return t&&n(e.prototype,t),r&&n(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function o(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null==n)return;var r,o,i=[],a=!0,s=!1;try{for(n=n.call(e);!(a=(r=n.next()).done)&&(i.push(r.value),!t||i.length!==t);a=!0);}catch(e){s=!0,o=e}finally{try{a||null==n.return||n.return()}finally{if(s)throw o}}return i}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return i(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);"Object"===n&&e.constructor&&(n=e.constructor.name);if("Map"===n||"Set"===n)return Array.from(e);if("Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n))return i(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function i(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}function a(e,t){return function(){return e.apply(t,arguments)}}var s,u=Object.prototype.toString,c=Object.getPrototypeOf,f=(s=Object.create(null),function(e){var t=u.call(e);return s[t]||(s[t]=t.slice(8,-1).toLowerCase())}),l=function(e){return e=e.toLowerCase(),function(t){return f(t)===e}},d=function(t){return function(n){return e(n)===t}},p=Array.isArray,h=d("undefined");var m=l("ArrayBuffer");var v=d("string"),y=d("function"),b=d("number"),g=function(t){return null!==t&&"object"===e(t)},w=function(e){if("object"!==f(e))return!1;var t=c(e);return!(null!==t&&t!==Object.prototype&&null!==Object.getPrototypeOf(t)||Symbol.toStringTag in e||Symbol.iterator in e)},E=l("Date"),O=l("File"),S=l("Blob"),R=l("FileList"),A=l("URLSearchParams");function T(t,n){var r,o,i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},a=i.allOwnKeys,s=void 0!==a&&a;if(null!=t)if("object"!==e(t)&&(t=[t]),p(t))for(r=0,o=t.length;r<o;r++)n.call(null,t[r],r,t);else{var u,c=s?Object.getOwnPropertyNames(t):Object.keys(t),f=c.length;for(r=0;r<f;r++)u=c[r],n.call(null,t[u],u,t)}}function j(e,t){t=t.toLowerCase();for(var n,r=Object.keys(e),o=r.length;o-- >0;)if(t===(n=r[o]).toLowerCase())return n;return null}var C="undefined"!=typeof globalThis?globalThis:"undefined"!=typeof self?self:"undefined"!=typeof window?window:global,N=function(e){return!h(e)&&e!==C};var x,P=(x="undefined"!=typeof Uint8Array&&c(Uint8Array),function(e){return x&&e instanceof x}),k=l("HTMLFormElement"),U=function(e){var t=Object.prototype.hasOwnProperty;return function(e,n){return t.call(e,n)}}(),_=l("RegExp"),F=function(e,t){var n=Object.getOwnPropertyDescriptors(e),r={};T(n,(function(n,o){var i;!1!==(i=t(n,o,e))&&(r[o]=i||n)})),Object.defineProperties(e,r)},B="abcdefghijklmnopqrstuvwxyz",L="0123456789",D={DIGIT:L,ALPHA:B,ALPHA_DIGIT:B+B.toUpperCase()+L};var I=l("AsyncFunction"),q={isArray:p,isArrayBuffer:m,isBuffer:function(e){return null!==e&&!h(e)&&null!==e.constructor&&!h(e.constructor)&&y(e.constructor.isBuffer)&&e.constructor.isBuffer(e)},isFormData:function(e){var t;return e&&("function"==typeof FormData&&e instanceof FormData||y(e.append)&&("formdata"===(t=f(e))||"object"===t&&y(e.toString)&&"[object FormData]"===e.toString()))},isArrayBufferView:function(e){return"undefined"!=typeof ArrayBuffer&&ArrayBuffer.isView?ArrayBuffer.isView(e):e&&e.buffer&&m(e.buffer)},isString:v,isNumber:b,isBoolean:function(e){return!0===e||!1===e},isObject:g,isPlainObject:w,isUndefined:h,isDate:E,isFile:O,isBlob:S,isRegExp:_,isFunction:y,isStream:function(e){return g(e)&&y(e.pipe)},isURLSearchParams:A,isTypedArray:P,isFileList:R,forEach:T,merge:function e(){for(var t=N(this)&&this||{},n=t.caseless,r={},o=function(t,o){var i=n&&j(r,o)||o;w(r[i])&&w(t)?r[i]=e(r[i],t):w(t)?r[i]=e({},t):p(t)?r[i]=t.slice():r[i]=t},i=0,a=arguments.length;i<a;i++)arguments[i]&&T(arguments[i],o);return r},extend:function(e,t,n){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{},o=r.allOwnKeys;return T(t,(function(t,r){n&&y(t)?e[r]=a(t,n):e[r]=t}),{allOwnKeys:o}),e},trim:function(e){return e.trim?e.trim():e.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g,"")},stripBOM:function(e){return 65279===e.charCodeAt(0)&&(e=e.slice(1)),e},inherits:function(e,t,n,r){e.prototype=Object.create(t.prototype,r),e.prototype.constructor=e,Object.defineProperty(e,"super",{value:t.prototype}),n&&Object.assign(e.prototype,n)},toFlatObject:function(e,t,n,r){var o,i,a,s={};if(t=t||{},null==e)return t;do{for(i=(o=Object.getOwnPropertyNames(e)).length;i-- >0;)a=o[i],r&&!r(a,e,t)||s[a]||(t[a]=e[a],s[a]=!0);e=!1!==n&&c(e)}while(e&&(!n||n(e,t))&&e!==Object.prototype);return t},kindOf:f,kindOfTest:l,endsWith:function(e,t,n){e=String(e),(void 0===n||n>e.length)&&(n=e.length),n-=t.length;var r=e.indexOf(t,n);return-1!==r&&r===n},toArray:function(e){if(!e)return null;if(p(e))return e;var t=e.length;if(!b(t))return null;for(var n=new Array(t);t-- >0;)n[t]=e[t];return n},forEachEntry:function(e,t){for(var n,r=(e&&e[Symbol.iterator]).call(e);(n=r.next())&&!n.done;){var o=n.value;t.call(e,o[0],o[1])}},matchAll:function(e,t){for(var n,r=[];null!==(n=e.exec(t));)r.push(n);return r},isHTMLForm:k,hasOwnProperty:U,hasOwnProp:U,reduceDescriptors:F,freezeMethods:function(e){F(e,(function(t,n){if(y(e)&&-1!==["arguments","caller","callee"].indexOf(n))return!1;var r=e[n];y(r)&&(t.enumerable=!1,"writable"in t?t.writable=!1:t.set||(t.set=function(){throw Error("Can not rewrite read-only method '"+n+"'")}))}))},toObjectSet:function(e,t){var n={},r=function(e){e.forEach((function(e){n[e]=!0}))};return p(e)?r(e):r(String(e).split(t)),n},toCamelCase:function(e){return e.toLowerCase().replace(/[-_\s]([a-z\d])(\w*)/g,(function(e,t,n){return t.toUpperCase()+n}))},noop:function(){},toFiniteNumber:function(e,t){return e=+e,Number.isFinite(e)?e:t},findKey:j,global:C,isContextDefined:N,ALPHABET:D,generateString:function(){for(var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:16,t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:D.ALPHA_DIGIT,n="",r=t.length;e--;)n+=t[Math.random()*r|0];return n},isSpecCompliantForm:function(e){return!!(e&&y(e.append)&&"FormData"===e[Symbol.toStringTag]&&e[Symbol.iterator])},toJSONObject:function(e){var t=new Array(10);return function e(n,r){if(g(n)){if(t.indexOf(n)>=0)return;if(!("toJSON"in n)){t[r]=n;var o=p(n)?[]:{};return T(n,(function(t,n){var i=e(t,r+1);!h(i)&&(o[n]=i)})),t[r]=void 0,o}}return n}(e,0)},isAsyncFn:I,isThenable:function(e){return e&&(g(e)||y(e))&&y(e.then)&&y(e.catch)}};function M(e,t,n,r,o){Error.call(this),Error.captureStackTrace?Error.captureStackTrace(this,this.constructor):this.stack=(new Error).stack,this.message=e,this.name="AxiosError",t&&(this.code=t),n&&(this.config=n),r&&(this.request=r),o&&(this.response=o)}q.inherits(M,Error,{toJSON:function(){return{message:this.message,name:this.name,description:this.description,number:this.number,fileName:this.fileName,lineNumber:this.lineNumber,columnNumber:this.columnNumber,stack:this.stack,config:q.toJSONObject(this.config),code:this.code,status:this.response&&this.response.status?this.response.status:null}}});var z=M.prototype,H={};["ERR_BAD_OPTION_VALUE","ERR_BAD_OPTION","ECONNABORTED","ETIMEDOUT","ERR_NETWORK","ERR_FR_TOO_MANY_REDIRECTS","ERR_DEPRECATED","ERR_BAD_RESPONSE","ERR_BAD_REQUEST","ERR_CANCELED","ERR_NOT_SUPPORT","ERR_INVALID_URL"].forEach((function(e){H[e]={value:e}})),Object.defineProperties(M,H),Object.defineProperty(z,"isAxiosError",{value:!0}),M.from=function(e,t,n,r,o,i){var a=Object.create(z);return q.toFlatObject(e,a,(function(e){return e!==Error.prototype}),(function(e){return"isAxiosError"!==e})),M.call(a,e.message,t,n,r,o),a.cause=e,a.name=e.name,i&&Object.assign(a,i),a};function J(e){return q.isPlainObject(e)||q.isArray(e)}function W(e){return q.endsWith(e,"[]")?e.slice(0,-2):e}function K(e,t,n){return e?e.concat(t).map((function(e,t){return e=W(e),!n&&t?"["+e+"]":e})).join(n?".":""):t}var V=q.toFlatObject(q,{},null,(function(e){return/^is[A-Z]/.test(e)}));function G(t,n,r){if(!q.isObject(t))throw new TypeError("target must be an object");n=n||new FormData;var o=(r=q.toFlatObject(r,{metaTokens:!0,dots:!1,indexes:!1},!1,(function(e,t){return!q.isUndefined(t[e])}))).metaTokens,i=r.visitor||f,a=r.dots,s=r.indexes,u=(r.Blob||"undefined"!=typeof Blob&&Blob)&&q.isSpecCompliantForm(n);if(!q.isFunction(i))throw new TypeError("visitor must be a function");function c(e){if(null===e)return"";if(q.isDate(e))return e.toISOString();if(!u&&q.isBlob(e))throw new M("Blob is not supported. Use a Buffer instead.");return q.isArrayBuffer(e)||q.isTypedArray(e)?u&&"function"==typeof Blob?new Blob([e]):Buffer.from(e):e}function f(t,r,i){var u=t;if(t&&!i&&"object"===e(t))if(q.endsWith(r,"{}"))r=o?r:r.slice(0,-2),t=JSON.stringify(t);else if(q.isArray(t)&&function(e){return q.isArray(e)&&!e.some(J)}(t)||(q.isFileList(t)||q.endsWith(r,"[]"))&&(u=q.toArray(t)))return r=W(r),u.forEach((function(e,t){!q.isUndefined(e)&&null!==e&&n.append(!0===s?K([r],t,a):null===s?r:r+"[]",c(e))})),!1;return!!J(t)||(n.append(K(i,r,a),c(t)),!1)}var l=[],d=Object.assign(V,{defaultVisitor:f,convertValue:c,isVisitable:J});if(!q.isObject(t))throw new TypeError("data must be an object");return function e(t,r){if(!q.isUndefined(t)){if(-1!==l.indexOf(t))throw Error("Circular reference detected in "+r.join("."));l.push(t),q.forEach(t,(function(t,o){!0===(!(q.isUndefined(t)||null===t)&&i.call(n,t,q.isString(o)?o.trim():o,r,d))&&e(t,r?r.concat(o):[o])})),l.pop()}}(t),n}function $(e){var t={"!":"%21","'":"%27","(":"%28",")":"%29","~":"%7E","%20":"+","%00":"\0"};return encodeURIComponent(e).replace(/[!'()~]|%20|%00/g,(function(e){return t[e]}))}function X(e,t){this._pairs=[],e&&G(e,this,t)}var Q=X.prototype;function Z(e){return encodeURIComponent(e).replace(/%3A/gi,":").replace(/%24/g,"$").replace(/%2C/gi,",").replace(/%20/g,"+").replace(/%5B/gi,"[").replace(/%5D/gi,"]")}function Y(e,t,n){if(!t)return e;var r,o=n&&n.encode||Z,i=n&&n.serialize;if(r=i?i(t,n):q.isURLSearchParams(t)?t.toString():new X(t,n).toString(o)){var a=e.indexOf("#");-1!==a&&(e=e.slice(0,a)),e+=(-1===e.indexOf("?")?"?":"&")+r}return e}Q.append=function(e,t){this._pairs.push([e,t])},Q.toString=function(e){var t=e?function(t){return e.call(this,t,$)}:$;return this._pairs.map((function(e){return t(e[0])+"="+t(e[1])}),"").join("&")};var ee,te=function(){function e(){t(this,e),this.handlers=[]}return r(e,[{key:"use",value:function(e,t,n){return this.handlers.push({fulfilled:e,rejected:t,synchronous:!!n&&n.synchronous,runWhen:n?n.runWhen:null}),this.handlers.length-1}},{key:"eject",value:function(e){this.handlers[e]&&(this.handlers[e]=null)}},{key:"clear",value:function(){this.handlers&&(this.handlers=[])}},{key:"forEach",value:function(e){q.forEach(this.handlers,(function(t){null!==t&&e(t)}))}}]),e}(),ne={silentJSONParsing:!0,forcedJSONParsing:!0,clarifyTimeoutError:!1},re={isBrowser:!0,classes:{URLSearchParams:"undefined"!=typeof URLSearchParams?URLSearchParams:X,FormData:"undefined"!=typeof FormData?FormData:null,Blob:"undefined"!=typeof Blob?Blob:null},isStandardBrowserEnv:("undefined"==typeof navigator||"ReactNative"!==(ee=navigator.product)&&"NativeScript"!==ee&&"NS"!==ee)&&"undefined"!=typeof window&&"undefined"!=typeof document,isStandardBrowserWebWorkerEnv:"undefined"!=typeof WorkerGlobalScope&&self instanceof WorkerGlobalScope&&"function"==typeof self.importScripts,protocols:["http","https","file","blob","url","data"]};function oe(e){function t(e,n,r,o){var i=e[o++],a=Number.isFinite(+i),s=o>=e.length;return i=!i&&q.isArray(r)?r.length:i,s?(q.hasOwnProp(r,i)?r[i]=[r[i],n]:r[i]=n,!a):(r[i]&&q.isObject(r[i])||(r[i]=[]),t(e,n,r[i],o)&&q.isArray(r[i])&&(r[i]=function(e){var t,n,r={},o=Object.keys(e),i=o.length;for(t=0;t<i;t++)r[n=o[t]]=e[n];return r}(r[i])),!a)}if(q.isFormData(e)&&q.isFunction(e.entries)){var n={};return q.forEachEntry(e,(function(e,r){t(function(e){return q.matchAll(/\w+|\[(\w*)]/g,e).map((function(e){return"[]"===e[0]?"":e[1]||e[0]}))}(e),r,n,0)})),n}return null}var ie={transitional:ne,adapter:["xhr","http"],transformRequest:[function(e,t){var n,r=t.getContentType()||"",o=r.indexOf("application/json")>-1,i=q.isObject(e);if(i&&q.isHTMLForm(e)&&(e=new FormData(e)),q.isFormData(e))return o&&o?JSON.stringify(oe(e)):e;if(q.isArrayBuffer(e)||q.isBuffer(e)||q.isStream(e)||q.isFile(e)||q.isBlob(e))return e;if(q.isArrayBufferView(e))return e.buffer;if(q.isURLSearchParams(e))return t.setContentType("application/x-www-form-urlencoded;charset=utf-8",!1),e.toString();if(i){if(r.indexOf("application/x-www-form-urlencoded")>-1)return function(e,t){return G(e,new re.classes.URLSearchParams,Object.assign({visitor:function(e,t,n,r){return re.isNode&&q.isBuffer(e)?(this.append(t,e.toString("base64")),!1):r.defaultVisitor.apply(this,arguments)}},t))}(e,this.formSerializer).toString();if((n=q.isFileList(e))||r.indexOf("multipart/form-data")>-1){var a=this.env&&this.env.FormData;return G(n?{"files[]":e}:e,a&&new a,this.formSerializer)}}return i||o?(t.setContentType("application/json",!1),function(e,t,n){if(q.isString(e))try{return(t||JSON.parse)(e),q.trim(e)}catch(e){if("SyntaxError"!==e.name)throw e}return(n||JSON.stringify)(e)}(e)):e}],transformResponse:[function(e){var t=this.transitional||ie.transitional,n=t&&t.forcedJSONParsing,r="json"===this.responseType;if(e&&q.isString(e)&&(n&&!this.responseType||r)){var o=!(t&&t.silentJSONParsing)&&r;try{return JSON.parse(e)}catch(e){if(o){if("SyntaxError"===e.name)throw M.from(e,M.ERR_BAD_RESPONSE,this,null,this.response);throw e}}}return e}],timeout:0,xsrfCookieName:"XSRF-TOKEN",xsrfHeaderName:"X-XSRF-TOKEN",maxContentLength:-1,maxBodyLength:-1,env:{FormData:re.classes.FormData,Blob:re.classes.Blob},validateStatus:function(e){return e>=200&&e<300},headers:{common:{Accept:"application/json, text/plain, */*","Content-Type":void 0}}};q.forEach(["delete","get","head","post","put","patch"],(function(e){ie.headers[e]={}}));var ae=ie,se=q.toObjectSet(["age","authorization","content-length","content-type","etag","expires","from","host","if-modified-since","if-unmodified-since","last-modified","location","max-forwards","proxy-authorization","referer","retry-after","user-agent"]),ue=Symbol("internals");function ce(e){return e&&String(e).trim().toLowerCase()}function fe(e){return!1===e||null==e?e:q.isArray(e)?e.map(fe):String(e)}function le(e,t,n,r,o){return q.isFunction(r)?r.call(this,t,n):(o&&(t=n),q.isString(t)?q.isString(r)?-1!==t.indexOf(r):q.isRegExp(r)?r.test(t):void 0:void 0)}var de=function(e,n){function i(e){t(this,i),e&&this.set(e)}return r(i,[{key:"set",value:function(e,t,n){var r=this;function o(e,t,n){var o=ce(t);if(!o)throw new Error("header name must be a non-empty string");var i=q.findKey(r,o);(!i||void 0===r[i]||!0===n||void 0===n&&!1!==r[i])&&(r[i||t]=fe(e))}var i,a,s,u,c,f=function(e,t){return q.forEach(e,(function(e,n){return o(e,n,t)}))};return q.isPlainObject(e)||e instanceof this.constructor?f(e,t):q.isString(e)&&(e=e.trim())&&!/^[-_a-zA-Z0-9^`|~,!#$%&'*+.]+$/.test(e.trim())?f((c={},(i=e)&&i.split("\n").forEach((function(e){u=e.indexOf(":"),a=e.substring(0,u).trim().toLowerCase(),s=e.substring(u+1).trim(),!a||c[a]&&se[a]||("set-cookie"===a?c[a]?c[a].push(s):c[a]=[s]:c[a]=c[a]?c[a]+", "+s:s)})),c),t):null!=e&&o(t,e,n),this}},{key:"get",value:function(e,t){if(e=ce(e)){var n=q.findKey(this,e);if(n){var r=this[n];if(!t)return r;if(!0===t)return function(e){for(var t,n=Object.create(null),r=/([^\s,;=]+)\s*(?:=\s*([^,;]+))?/g;t=r.exec(e);)n[t[1]]=t[2];return n}(r);if(q.isFunction(t))return t.call(this,r,n);if(q.isRegExp(t))return t.exec(r);throw new TypeError("parser must be boolean|regexp|function")}}}},{key:"has",value:function(e,t){if(e=ce(e)){var n=q.findKey(this,e);return!(!n||void 0===this[n]||t&&!le(0,this[n],n,t))}return!1}},{key:"delete",value:function(e,t){var n=this,r=!1;function o(e){if(e=ce(e)){var o=q.findKey(n,e);!o||t&&!le(0,n[o],o,t)||(delete n[o],r=!0)}}return q.isArray(e)?e.forEach(o):o(e),r}},{key:"clear",value:function(e){for(var t=Object.keys(this),n=t.length,r=!1;n--;){var o=t[n];e&&!le(0,this[o],o,e,!0)||(delete this[o],r=!0)}return r}},{key:"normalize",value:function(e){var t=this,n={};return q.forEach(this,(function(r,o){var i=q.findKey(n,o);if(i)return t[i]=fe(r),void delete t[o];var a=e?function(e){return e.trim().toLowerCase().replace(/([a-z\d])(\w*)/g,(function(e,t,n){return t.toUpperCase()+n}))}(o):String(o).trim();a!==o&&delete t[o],t[a]=fe(r),n[a]=!0})),this}},{key:"concat",value:function(){for(var e,t=arguments.length,n=new Array(t),r=0;r<t;r++)n[r]=arguments[r];return(e=this.constructor).concat.apply(e,[this].concat(n))}},{key:"toJSON",value:function(e){var t=Object.create(null);return q.forEach(this,(function(n,r){null!=n&&!1!==n&&(t[r]=e&&q.isArray(n)?n.join(", "):n)})),t}},{key:Symbol.iterator,value:function(){return Object.entries(this.toJSON())[Symbol.iterator]()}},{key:"toString",value:function(){return Object.entries(this.toJSON()).map((function(e){var t=o(e,2);return t[0]+": "+t[1]})).join("\n")}},{key:Symbol.toStringTag,get:function(){return"AxiosHeaders"}}],[{key:"from",value:function(e){return e instanceof this?e:new this(e)}},{key:"concat",value:function(e){for(var t=new this(e),n=arguments.length,r=new Array(n>1?n-1:0),o=1;o<n;o++)r[o-1]=arguments[o];return r.forEach((function(e){return t.set(e)})),t}},{key:"accessor",value:function(e){var t=(this[ue]=this[ue]={accessors:{}}).accessors,n=this.prototype;function r(e){var r=ce(e);t[r]||(!function(e,t){var n=q.toCamelCase(" "+t);["get","set","has"].forEach((function(r){Object.defineProperty(e,r+n,{value:function(e,n,o){return this[r].call(this,t,e,n,o)},configurable:!0})}))}(n,e),t[r]=!0)}return q.isArray(e)?e.forEach(r):r(e),this}}]),i}();de.accessor(["Content-Type","Content-Length","Accept","Accept-Encoding","User-Agent","Authorization"]),q.reduceDescriptors(de.prototype,(function(e,t){var n=e.value,r=t[0].toUpperCase()+t.slice(1);return{get:function(){return n},set:function(e){this[r]=e}}})),q.freezeMethods(de);var pe=de;function he(e,t){var n=this||ae,r=t||n,o=pe.from(r.headers),i=r.data;return q.forEach(e,(function(e){i=e.call(n,i,o.normalize(),t?t.status:void 0)})),o.normalize(),i}function me(e){return!(!e||!e.__CANCEL__)}function ve(e,t,n){M.call(this,null==e?"canceled":e,M.ERR_CANCELED,t,n),this.name="CanceledError"}q.inherits(ve,M,{__CANCEL__:!0});var ye=re.isStandardBrowserEnv?{write:function(e,t,n,r,o,i){var a=[];a.push(e+"="+encodeURIComponent(t)),q.isNumber(n)&&a.push("expires="+new Date(n).toGMTString()),q.isString(r)&&a.push("path="+r),q.isString(o)&&a.push("domain="+o),!0===i&&a.push("secure"),document.cookie=a.join("; ")},read:function(e){var t=document.cookie.match(new RegExp("(^|;\\s*)("+e+")=([^;]*)"));return t?decodeURIComponent(t[3]):null},remove:function(e){this.write(e,"",Date.now()-864e5)}}:{write:function(){},read:function(){return null},remove:function(){}};function be(e,t){return e&&!/^([a-z][a-z\d+\-.]*:)?\/\//i.test(t)?function(e,t){return t?e.replace(/\/+$/,"")+"/"+t.replace(/^\/+/,""):e}(e,t):t}var ge=re.isStandardBrowserEnv?function(){var e,t=/(msie|trident)/i.test(navigator.userAgent),n=document.createElement("a");function r(e){var r=e;return t&&(n.setAttribute("href",r),r=n.href),n.setAttribute("href",r),{href:n.href,protocol:n.protocol?n.protocol.replace(/:$/,""):"",host:n.host,search:n.search?n.search.replace(/^\?/,""):"",hash:n.hash?n.hash.replace(/^#/,""):"",hostname:n.hostname,port:n.port,pathname:"/"===n.pathname.charAt(0)?n.pathname:"/"+n.pathname}}return e=r(window.location.href),function(t){var n=q.isString(t)?r(t):t;return n.protocol===e.protocol&&n.host===e.host}}():function(){return!0};function we(e,t){var n=0,r=function(e,t){e=e||10;var n,r=new Array(e),o=new Array(e),i=0,a=0;return t=void 0!==t?t:1e3,function(s){var u=Date.now(),c=o[a];n||(n=u),r[i]=s,o[i]=u;for(var f=a,l=0;f!==i;)l+=r[f++],f%=e;if((i=(i+1)%e)===a&&(a=(a+1)%e),!(u-n<t)){var d=c&&u-c;return d?Math.round(1e3*l/d):void 0}}}(50,250);return function(o){var i=o.loaded,a=o.lengthComputable?o.total:void 0,s=i-n,u=r(s);n=i;var c={loaded:i,total:a,progress:a?i/a:void 0,bytes:s,rate:u||void 0,estimated:u&&a&&i<=a?(a-i)/u:void 0,event:o};c[t?"download":"upload"]=!0,e(c)}}var Ee={http:null,xhr:"undefined"!=typeof XMLHttpRequest&&function(e){return new Promise((function(t,n){var r,o,i=e.data,a=pe.from(e.headers).normalize(),s=e.responseType;function u(){e.cancelToken&&e.cancelToken.unsubscribe(r),e.signal&&e.signal.removeEventListener("abort",r)}q.isFormData(i)&&(re.isStandardBrowserEnv||re.isStandardBrowserWebWorkerEnv?a.setContentType(!1):a.getContentType(/^\s*multipart\/form-data/)?q.isString(o=a.getContentType())&&a.setContentType(o.replace(/^\s*(multipart\/form-data);+/,"$1")):a.setContentType("multipart/form-data"));var c=new XMLHttpRequest;if(e.auth){var f=e.auth.username||"",l=e.auth.password?unescape(encodeURIComponent(e.auth.password)):"";a.set("Authorization","Basic "+btoa(f+":"+l))}var d=be(e.baseURL,e.url);function p(){if(c){var r=pe.from("getAllResponseHeaders"in c&&c.getAllResponseHeaders());!function(e,t,n){var r=n.config.validateStatus;n.status&&r&&!r(n.status)?t(new M("Request failed with status code "+n.status,[M.ERR_BAD_REQUEST,M.ERR_BAD_RESPONSE][Math.floor(n.status/100)-4],n.config,n.request,n)):e(n)}((function(e){t(e),u()}),(function(e){n(e),u()}),{data:s&&"text"!==s&&"json"!==s?c.response:c.responseText,status:c.status,statusText:c.statusText,headers:r,config:e,request:c}),c=null}}if(c.open(e.method.toUpperCase(),Y(d,e.params,e.paramsSerializer),!0),c.timeout=e.timeout,"onloadend"in c?c.onloadend=p:c.onreadystatechange=function(){c&&4===c.readyState&&(0!==c.status||c.responseURL&&0===c.responseURL.indexOf("file:"))&&setTimeout(p)},c.onabort=function(){c&&(n(new M("Request aborted",M.ECONNABORTED,e,c)),c=null)},c.onerror=function(){n(new M("Network Error",M.ERR_NETWORK,e,c)),c=null},c.ontimeout=function(){var t=e.timeout?"timeout of "+e.timeout+"ms exceeded":"timeout exceeded",r=e.transitional||ne;e.timeoutErrorMessage&&(t=e.timeoutErrorMessage),n(new M(t,r.clarifyTimeoutError?M.ETIMEDOUT:M.ECONNABORTED,e,c)),c=null},re.isStandardBrowserEnv){var h=ge(d)&&e.xsrfCookieName&&ye.read(e.xsrfCookieName);h&&a.set(e.xsrfHeaderName,h)}void 0===i&&a.setContentType(null),"setRequestHeader"in c&&q.forEach(a.toJSON(),(function(e,t){c.setRequestHeader(t,e)})),q.isUndefined(e.withCredentials)||(c.withCredentials=!!e.withCredentials),s&&"json"!==s&&(c.responseType=e.responseType),"function"==typeof e.onDownloadProgress&&c.addEventListener("progress",we(e.onDownloadProgress,!0)),"function"==typeof e.onUploadProgress&&c.upload&&c.upload.addEventListener("progress",we(e.onUploadProgress)),(e.cancelToken||e.signal)&&(r=function(t){c&&(n(!t||t.type?new ve(null,e,c):t),c.abort(),c=null)},e.cancelToken&&e.cancelToken.subscribe(r),e.signal&&(e.signal.aborted?r():e.signal.addEventListener("abort",r)));var m,v=(m=/^([-+\w]{1,25})(:?\/\/|:)/.exec(d))&&m[1]||"";v&&-1===re.protocols.indexOf(v)?n(new M("Unsupported protocol "+v+":",M.ERR_BAD_REQUEST,e)):c.send(i||null)}))}};q.forEach(Ee,(function(e,t){if(e){try{Object.defineProperty(e,"name",{value:t})}catch(e){}Object.defineProperty(e,"adapterName",{value:t})}}));var Oe=function(e){return"- ".concat(e)},Se=function(e){return q.isFunction(e)||null===e||!1===e},Re=function(e){for(var t,n,r=(e=q.isArray(e)?e:[e]).length,i={},a=0;a<r;a++){var s=void 0;if(n=t=e[a],!Se(t)&&void 0===(n=Ee[(s=String(t)).toLowerCase()]))throw new M("Unknown adapter '".concat(s,"'"));if(n)break;i[s||"#"+a]=n}if(!n){var u=Object.entries(i).map((function(e){var t=o(e,2),n=t[0],r=t[1];return"adapter ".concat(n," ")+(!1===r?"is not supported by the environment":"is not available in the build")}));throw new M("There is no suitable adapter to dispatch the request "+(r?u.length>1?"since :\n"+u.map(Oe).join("\n"):" "+Oe(u[0]):"as no adapter specified"),"ERR_NOT_SUPPORT")}return n};function Ae(e){if(e.cancelToken&&e.cancelToken.throwIfRequested(),e.signal&&e.signal.aborted)throw new ve(null,e)}function Te(e){return Ae(e),e.headers=pe.from(e.headers),e.data=he.call(e,e.transformRequest),-1!==["post","put","patch"].indexOf(e.method)&&e.headers.setContentType("application/x-www-form-urlencoded",!1),Re(e.adapter||ae.adapter)(e).then((function(t){return Ae(e),t.data=he.call(e,e.transformResponse,t),t.headers=pe.from(t.headers),t}),(function(t){return me(t)||(Ae(e),t&&t.response&&(t.response.data=he.call(e,e.transformResponse,t.response),t.response.headers=pe.from(t.response.headers))),Promise.reject(t)}))}var je=function(e){return e instanceof pe?e.toJSON():e};function Ce(e,t){t=t||{};var n={};function r(e,t,n){return q.isPlainObject(e)&&q.isPlainObject(t)?q.merge.call({caseless:n},e,t):q.isPlainObject(t)?q.merge({},t):q.isArray(t)?t.slice():t}function o(e,t,n){return q.isUndefined(t)?q.isUndefined(e)?void 0:r(void 0,e,n):r(e,t,n)}function i(e,t){if(!q.isUndefined(t))return r(void 0,t)}function a(e,t){return q.isUndefined(t)?q.isUndefined(e)?void 0:r(void 0,e):r(void 0,t)}function s(n,o,i){return i in t?r(n,o):i in e?r(void 0,n):void 0}var u={url:i,method:i,data:i,baseURL:a,transformRequest:a,transformResponse:a,paramsSerializer:a,timeout:a,timeoutMessage:a,withCredentials:a,adapter:a,responseType:a,xsrfCookieName:a,xsrfHeaderName:a,onUploadProgress:a,onDownloadProgress:a,decompress:a,maxContentLength:a,maxBodyLength:a,beforeRedirect:a,transport:a,httpAgent:a,httpsAgent:a,cancelToken:a,socketPath:a,responseEncoding:a,validateStatus:s,headers:function(e,t){return o(je(e),je(t),!0)}};return q.forEach(Object.keys(Object.assign({},e,t)),(function(r){var i=u[r]||o,a=i(e[r],t[r],r);q.isUndefined(a)&&i!==s||(n[r]=a)})),n}var Ne="1.6.0",xe={};["object","boolean","number","function","string","symbol"].forEach((function(t,n){xe[t]=function(r){return e(r)===t||"a"+(n<1?"n ":" ")+t}}));var Pe={};xe.transitional=function(e,t,n){function r(e,t){return"[Axios v1.6.0] Transitional option '"+e+"'"+t+(n?". "+n:"")}return function(n,o,i){if(!1===e)throw new M(r(o," has been removed"+(t?" in "+t:"")),M.ERR_DEPRECATED);return t&&!Pe[o]&&(Pe[o]=!0,console.warn(r(o," has been deprecated since v"+t+" and will be removed in the near future"))),!e||e(n,o,i)}};var ke={assertOptions:function(t,n,r){if("object"!==e(t))throw new M("options must be an object",M.ERR_BAD_OPTION_VALUE);for(var o=Object.keys(t),i=o.length;i-- >0;){var a=o[i],s=n[a];if(s){var u=t[a],c=void 0===u||s(u,a,t);if(!0!==c)throw new M("option "+a+" must be "+c,M.ERR_BAD_OPTION_VALUE)}else if(!0!==r)throw new M("Unknown option "+a,M.ERR_BAD_OPTION)}},validators:xe},Ue=ke.validators,_e=function(){function e(n){t(this,e),this.defaults=n,this.interceptors={request:new te,response:new te}}return r(e,[{key:"request",value:function(e,t){"string"==typeof e?(t=t||{}).url=e:t=e||{};var n=t=Ce(this.defaults,t),r=n.transitional,o=n.paramsSerializer,i=n.headers;void 0!==r&&ke.assertOptions(r,{silentJSONParsing:Ue.transitional(Ue.boolean),forcedJSONParsing:Ue.transitional(Ue.boolean),clarifyTimeoutError:Ue.transitional(Ue.boolean)},!1),null!=o&&(q.isFunction(o)?t.paramsSerializer={serialize:o}:ke.assertOptions(o,{encode:Ue.function,serialize:Ue.function},!0)),t.method=(t.method||this.defaults.method||"get").toLowerCase();var a=i&&q.merge(i.common,i[t.method]);i&&q.forEach(["delete","get","head","post","put","patch","common"],(function(e){delete i[e]})),t.headers=pe.concat(a,i);var s=[],u=!0;this.interceptors.request.forEach((function(e){"function"==typeof e.runWhen&&!1===e.runWhen(t)||(u=u&&e.synchronous,s.unshift(e.fulfilled,e.rejected))}));var c,f=[];this.interceptors.response.forEach((function(e){f.push(e.fulfilled,e.rejected)}));var l,d=0;if(!u){var p=[Te.bind(this),void 0];for(p.unshift.apply(p,s),p.push.apply(p,f),l=p.length,c=Promise.resolve(t);d<l;)c=c.then(p[d++],p[d++]);return c}l=s.length;var h=t;for(d=0;d<l;){var m=s[d++],v=s[d++];try{h=m(h)}catch(e){v.call(this,e);break}}try{c=Te.call(this,h)}catch(e){return Promise.reject(e)}for(d=0,l=f.length;d<l;)c=c.then(f[d++],f[d++]);return c}},{key:"getUri",value:function(e){return Y(be((e=Ce(this.defaults,e)).baseURL,e.url),e.params,e.paramsSerializer)}}]),e}();q.forEach(["delete","get","head","options"],(function(e){_e.prototype[e]=function(t,n){return this.request(Ce(n||{},{method:e,url:t,data:(n||{}).data}))}})),q.forEach(["post","put","patch"],(function(e){function t(t){return function(n,r,o){return this.request(Ce(o||{},{method:e,headers:t?{"Content-Type":"multipart/form-data"}:{},url:n,data:r}))}}_e.prototype[e]=t(),_e.prototype[e+"Form"]=t(!0)}));var Fe=_e,Be=function(){function e(n){if(t(this,e),"function"!=typeof n)throw new TypeError("executor must be a function.");var r;this.promise=new Promise((function(e){r=e}));var o=this;this.promise.then((function(e){if(o._listeners){for(var t=o._listeners.length;t-- >0;)o._listeners[t](e);o._listeners=null}})),this.promise.then=function(e){var t,n=new Promise((function(e){o.subscribe(e),t=e})).then(e);return n.cancel=function(){o.unsubscribe(t)},n},n((function(e,t,n){o.reason||(o.reason=new ve(e,t,n),r(o.reason))}))}return r(e,[{key:"throwIfRequested",value:function(){if(this.reason)throw this.reason}},{key:"subscribe",value:function(e){this.reason?e(this.reason):this._listeners?this._listeners.push(e):this._listeners=[e]}},{key:"unsubscribe",value:function(e){if(this._listeners){var t=this._listeners.indexOf(e);-1!==t&&this._listeners.splice(t,1)}}}],[{key:"source",value:function(){var t;return{token:new e((function(e){t=e})),cancel:t}}}]),e}();var Le={Continue:100,SwitchingProtocols:101,Processing:102,EarlyHints:103,Ok:200,Created:201,Accepted:202,NonAuthoritativeInformation:203,NoContent:204,ResetContent:205,PartialContent:206,MultiStatus:207,AlreadyReported:208,ImUsed:226,MultipleChoices:300,MovedPermanently:301,Found:302,SeeOther:303,NotModified:304,UseProxy:305,Unused:306,TemporaryRedirect:307,PermanentRedirect:308,BadRequest:400,Unauthorized:401,PaymentRequired:402,Forbidden:403,NotFound:404,MethodNotAllowed:405,NotAcceptable:406,ProxyAuthenticationRequired:407,RequestTimeout:408,Conflict:409,Gone:410,LengthRequired:411,PreconditionFailed:412,PayloadTooLarge:413,UriTooLong:414,UnsupportedMediaType:415,RangeNotSatisfiable:416,ExpectationFailed:417,ImATeapot:418,MisdirectedRequest:421,UnprocessableEntity:422,Locked:423,FailedDependency:424,TooEarly:425,UpgradeRequired:426,PreconditionRequired:428,TooManyRequests:429,RequestHeaderFieldsTooLarge:431,UnavailableForLegalReasons:451,InternalServerError:500,NotImplemented:501,BadGateway:502,ServiceUnavailable:503,GatewayTimeout:504,HttpVersionNotSupported:505,VariantAlsoNegotiates:506,InsufficientStorage:507,LoopDetected:508,NotExtended:510,NetworkAuthenticationRequired:511};Object.entries(Le).forEach((function(e){var t=o(e,2),n=t[0],r=t[1];Le[r]=n}));var De=Le;var Ie=function e(t){var n=new Fe(t),r=a(Fe.prototype.request,n);return q.extend(r,Fe.prototype,n,{allOwnKeys:!0}),q.extend(r,n,null,{allOwnKeys:!0}),r.create=function(n){return e(Ce(t,n))},r}(ae);return Ie.Axios=Fe,Ie.CanceledError=ve,Ie.CancelToken=Be,Ie.isCancel=me,Ie.VERSION=Ne,Ie.toFormData=G,Ie.AxiosError=M,Ie.Cancel=Ie.CanceledError,Ie.all=function(e){return Promise.all(e)},Ie.spread=function(e){return function(t){return e.apply(null,t)}},Ie.isAxiosError=function(e){return q.isObject(e)&&!0===e.isAxiosError},Ie.mergeConfig=Ce,Ie.AxiosHeaders=pe,Ie.formToJSON=function(e){return oe(q.isHTMLForm(e)?new FormData(e):e)},Ie.getAdapter=Re,Ie.HttpStatusCode=De,Ie.default=Ie,Ie}));
//# sourceMappingURL=axios.min.js.map

// md5
;(function ($) {
  'use strict'
  /**
   * Add integers, wrapping at 2^32.
   * This uses 16-bit operations internally to work around bugs in interpreters.
   *
   * @param {number} x First integer
   * @param {number} y Second integer
   * @returns {number} Sum
   */
  function safeAdd(x, y) {
    var lsw = (x & 0xffff) + (y & 0xffff)
    var msw = (x >> 16) + (y >> 16) + (lsw >> 16)
    return (msw << 16) | (lsw & 0xffff)
  }

  /**
   * Bitwise rotate a 32-bit number to the left.
   *
   * @param {number} num 32-bit number
   * @param {number} cnt Rotation count
   * @returns {number} Rotated number
   */
  function bitRotateLeft(num, cnt) {
    return (num << cnt) | (num >>> (32 - cnt))
  }

  /**
   * Basic operation the algorithm uses.
   *
   * @param {number} q q
   * @param {number} a a
   * @param {number} b b
   * @param {number} x x
   * @param {number} s s
   * @param {number} t t
   * @returns {number} Result
   */
  function md5cmn(q, a, b, x, s, t) {
    return safeAdd(bitRotateLeft(safeAdd(safeAdd(a, q), safeAdd(x, t)), s), b)
  }
  /**
   * Basic operation the algorithm uses.
   *
   * @param {number} a a
   * @param {number} b b
   * @param {number} c c
   * @param {number} d d
   * @param {number} x x
   * @param {number} s s
   * @param {number} t t
   * @returns {number} Result
   */
  function md5ff(a, b, c, d, x, s, t) {
    return md5cmn((b & c) | (~b & d), a, b, x, s, t)
  }
  /**
   * Basic operation the algorithm uses.
   *
   * @param {number} a a
   * @param {number} b b
   * @param {number} c c
   * @param {number} d d
   * @param {number} x x
   * @param {number} s s
   * @param {number} t t
   * @returns {number} Result
   */
  function md5gg(a, b, c, d, x, s, t) {
    return md5cmn((b & d) | (c & ~d), a, b, x, s, t)
  }
  /**
   * Basic operation the algorithm uses.
   *
   * @param {number} a a
   * @param {number} b b
   * @param {number} c c
   * @param {number} d d
   * @param {number} x x
   * @param {number} s s
   * @param {number} t t
   * @returns {number} Result
   */
  function md5hh(a, b, c, d, x, s, t) {
    return md5cmn(b ^ c ^ d, a, b, x, s, t)
  }
  /**
   * Basic operation the algorithm uses.
   *
   * @param {number} a a
   * @param {number} b b
   * @param {number} c c
   * @param {number} d d
   * @param {number} x x
   * @param {number} s s
   * @param {number} t t
   * @returns {number} Result
   */
  function md5ii(a, b, c, d, x, s, t) {
    return md5cmn(c ^ (b | ~d), a, b, x, s, t)
  }

  /**
   * Calculate the MD5 of an array of little-endian words, and a bit length.
   *
   * @param {Array} x Array of little-endian words
   * @param {number} len Bit length
   * @returns {Array<number>} MD5 Array
   */
  function binlMD5(x, len) {
    /* append padding */
    x[len >> 5] |= 0x80 << len % 32
    x[(((len + 64) >>> 9) << 4) + 14] = len

    var i
    var olda
    var oldb
    var oldc
    var oldd
    var a = 1732584193
    var b = -271733879
    var c = -1732584194
    var d = 271733878

    for (i = 0; i < x.length; i += 16) {
      olda = a
      oldb = b
      oldc = c
      oldd = d

      a = md5ff(a, b, c, d, x[i], 7, -680876936)
      d = md5ff(d, a, b, c, x[i + 1], 12, -389564586)
      c = md5ff(c, d, a, b, x[i + 2], 17, 606105819)
      b = md5ff(b, c, d, a, x[i + 3], 22, -1044525330)
      a = md5ff(a, b, c, d, x[i + 4], 7, -176418897)
      d = md5ff(d, a, b, c, x[i + 5], 12, 1200080426)
      c = md5ff(c, d, a, b, x[i + 6], 17, -1473231341)
      b = md5ff(b, c, d, a, x[i + 7], 22, -45705983)
      a = md5ff(a, b, c, d, x[i + 8], 7, 1770035416)
      d = md5ff(d, a, b, c, x[i + 9], 12, -1958414417)
      c = md5ff(c, d, a, b, x[i + 10], 17, -42063)
      b = md5ff(b, c, d, a, x[i + 11], 22, -1990404162)
      a = md5ff(a, b, c, d, x[i + 12], 7, 1804603682)
      d = md5ff(d, a, b, c, x[i + 13], 12, -40341101)
      c = md5ff(c, d, a, b, x[i + 14], 17, -1502002290)
      b = md5ff(b, c, d, a, x[i + 15], 22, 1236535329)

      a = md5gg(a, b, c, d, x[i + 1], 5, -165796510)
      d = md5gg(d, a, b, c, x[i + 6], 9, -1069501632)
      c = md5gg(c, d, a, b, x[i + 11], 14, 643717713)
      b = md5gg(b, c, d, a, x[i], 20, -373897302)
      a = md5gg(a, b, c, d, x[i + 5], 5, -701558691)
      d = md5gg(d, a, b, c, x[i + 10], 9, 38016083)
      c = md5gg(c, d, a, b, x[i + 15], 14, -660478335)
      b = md5gg(b, c, d, a, x[i + 4], 20, -405537848)
      a = md5gg(a, b, c, d, x[i + 9], 5, 568446438)
      d = md5gg(d, a, b, c, x[i + 14], 9, -1019803690)
      c = md5gg(c, d, a, b, x[i + 3], 14, -187363961)
      b = md5gg(b, c, d, a, x[i + 8], 20, 1163531501)
      a = md5gg(a, b, c, d, x[i + 13], 5, -1444681467)
      d = md5gg(d, a, b, c, x[i + 2], 9, -51403784)
      c = md5gg(c, d, a, b, x[i + 7], 14, 1735328473)
      b = md5gg(b, c, d, a, x[i + 12], 20, -1926607734)

      a = md5hh(a, b, c, d, x[i + 5], 4, -378558)
      d = md5hh(d, a, b, c, x[i + 8], 11, -2022574463)
      c = md5hh(c, d, a, b, x[i + 11], 16, 1839030562)
      b = md5hh(b, c, d, a, x[i + 14], 23, -35309556)
      a = md5hh(a, b, c, d, x[i + 1], 4, -1530992060)
      d = md5hh(d, a, b, c, x[i + 4], 11, 1272893353)
      c = md5hh(c, d, a, b, x[i + 7], 16, -155497632)
      b = md5hh(b, c, d, a, x[i + 10], 23, -1094730640)
      a = md5hh(a, b, c, d, x[i + 13], 4, 681279174)
      d = md5hh(d, a, b, c, x[i], 11, -358537222)
      c = md5hh(c, d, a, b, x[i + 3], 16, -722521979)
      b = md5hh(b, c, d, a, x[i + 6], 23, 76029189)
      a = md5hh(a, b, c, d, x[i + 9], 4, -640364487)
      d = md5hh(d, a, b, c, x[i + 12], 11, -421815835)
      c = md5hh(c, d, a, b, x[i + 15], 16, 530742520)
      b = md5hh(b, c, d, a, x[i + 2], 23, -995338651)

      a = md5ii(a, b, c, d, x[i], 6, -198630844)
      d = md5ii(d, a, b, c, x[i + 7], 10, 1126891415)
      c = md5ii(c, d, a, b, x[i + 14], 15, -1416354905)
      b = md5ii(b, c, d, a, x[i + 5], 21, -57434055)
      a = md5ii(a, b, c, d, x[i + 12], 6, 1700485571)
      d = md5ii(d, a, b, c, x[i + 3], 10, -1894986606)
      c = md5ii(c, d, a, b, x[i + 10], 15, -1051523)
      b = md5ii(b, c, d, a, x[i + 1], 21, -2054922799)
      a = md5ii(a, b, c, d, x[i + 8], 6, 1873313359)
      d = md5ii(d, a, b, c, x[i + 15], 10, -30611744)
      c = md5ii(c, d, a, b, x[i + 6], 15, -1560198380)
      b = md5ii(b, c, d, a, x[i + 13], 21, 1309151649)
      a = md5ii(a, b, c, d, x[i + 4], 6, -145523070)
      d = md5ii(d, a, b, c, x[i + 11], 10, -1120210379)
      c = md5ii(c, d, a, b, x[i + 2], 15, 718787259)
      b = md5ii(b, c, d, a, x[i + 9], 21, -343485551)

      a = safeAdd(a, olda)
      b = safeAdd(b, oldb)
      c = safeAdd(c, oldc)
      d = safeAdd(d, oldd)
    }
    return [a, b, c, d]
  }

  /**
   * Convert an array of little-endian words to a string
   *
   * @param {Array<number>} input MD5 Array
   * @returns {string} MD5 string
   */
  function binl2rstr(input) {
    var i
    var output = ''
    var length32 = input.length * 32
    for (i = 0; i < length32; i += 8) {
      output += String.fromCharCode((input[i >> 5] >>> i % 32) & 0xff)
    }
    return output
  }

  /**
   * Convert a raw string to an array of little-endian words
   * Characters >255 have their high-byte silently ignored.
   *
   * @param {string} input Raw input string
   * @returns {Array<number>} Array of little-endian words
   */
  function rstr2binl(input) {
    var i
    var output = []
    output[(input.length >> 2) - 1] = undefined
    for (i = 0; i < output.length; i += 1) {
      output[i] = 0
    }
    var length8 = input.length * 8
    for (i = 0; i < length8; i += 8) {
      output[i >> 5] |= (input.charCodeAt(i / 8) & 0xff) << i % 32
    }
    return output
  }

  /**
   * Calculate the MD5 of a raw string
   *
   * @param {string} s Input string
   * @returns {string} Raw MD5 string
   */
  function rstrMD5(s) {
    return binl2rstr(binlMD5(rstr2binl(s), s.length * 8))
  }

  /**
   * Calculates the HMAC-MD5 of a key and some data (raw strings)
   *
   * @param {string} key HMAC key
   * @param {string} data Raw input string
   * @returns {string} Raw MD5 string
   */
  function rstrHMACMD5(key, data) {
    var i
    var bkey = rstr2binl(key)
    var ipad = []
    var opad = []
    var hash
    ipad[15] = opad[15] = undefined
    if (bkey.length > 16) {
      bkey = binlMD5(bkey, key.length * 8)
    }
    for (i = 0; i < 16; i += 1) {
      ipad[i] = bkey[i] ^ 0x36363636
      opad[i] = bkey[i] ^ 0x5c5c5c5c
    }
    hash = binlMD5(ipad.concat(rstr2binl(data)), 512 + data.length * 8)
    return binl2rstr(binlMD5(opad.concat(hash), 512 + 128))
  }

  /**
   * Convert a raw string to a hex string
   *
   * @param {string} input Raw input string
   * @returns {string} Hex encoded string
   */
  function rstr2hex(input) {
    var hexTab = '0123456789abcdef'
    var output = ''
    var x
    var i
    for (i = 0; i < input.length; i += 1) {
      x = input.charCodeAt(i)
      output += hexTab.charAt((x >>> 4) & 0x0f) + hexTab.charAt(x & 0x0f)
    }
    return output
  }

  /**
   * Encode a string as UTF-8
   *
   * @param {string} input Input string
   * @returns {string} UTF8 string
   */
  function str2rstrUTF8(input) {
    return unescape(encodeURIComponent(input))
  }

  /**
   * Encodes input string as raw MD5 string
   *
   * @param {string} s Input string
   * @returns {string} Raw MD5 string
   */
  function rawMD5(s) {
    return rstrMD5(str2rstrUTF8(s))
  }
  /**
   * Encodes input string as Hex encoded string
   *
   * @param {string} s Input string
   * @returns {string} Hex encoded string
   */
  function hexMD5(s) {
    return rstr2hex(rawMD5(s))
  }
  /**
   * Calculates the raw HMAC-MD5 for the given key and data
   *
   * @param {string} k HMAC key
   * @param {string} d Input string
   * @returns {string} Raw MD5 string
   */
  function rawHMACMD5(k, d) {
    return rstrHMACMD5(str2rstrUTF8(k), str2rstrUTF8(d))
  }
  /**
   * Calculates the Hex encoded HMAC-MD5 for the given key and data
   *
   * @param {string} k HMAC key
   * @param {string} d Input string
   * @returns {string} Raw MD5 string
   */
  function hexHMACMD5(k, d) {
    return rstr2hex(rawHMACMD5(k, d))
  }

  /**
   * Calculates MD5 value for a given string.
   * If a key is provided, calculates the HMAC-MD5 value.
   * Returns a Hex encoded string unless the raw argument is given.
   *
   * @param {string} string Input string
   * @param {string} [key] HMAC key
   * @param {boolean} [raw] Raw output switch
   * @returns {string} MD5 output
   */
  function md5(string, key, raw) {
    if (!key) {
      if (!raw) {
        return hexMD5(string)
      }
      return rawMD5(string)
    }
    if (!raw) {
      return hexHMACMD5(key, string)
    }
    return rawHMACMD5(key, string)
  }
  $.md5 = md5
})(window)