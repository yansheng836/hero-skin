package xyz.yansheng.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * lol英雄实体类。
 * 
 * @author yansheng
 * @date 2020/06/14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LolHero {
    /**
     * 英雄ID
     */
    private String heroId;

    /**
     * 英雄称号
     */
    private String name;

    /**
     * 英雄别名（即英文名）
     */
    private String alias;

    /**
     * 英雄名
     */
    private String title;

    /**
     * lol英雄角色
     */
    private Role[] roles;

    /**
     * 是否周免
     */
    private String isWeekFree;
    /**
     * 攻击力
     */
    private String attack;
    /**
     * 防御力
     */
    private String defense;
    /**
     * 伤害（法强）
     */
    private String magic;
    /**
     * 英雄操作难度
     */
    private String difficulty;
    /**
     * 英雄选中语音
     */
    private String selectAudio;
    /**
     * 英雄被禁语音
     */
    private String banAudio;
    /**
     * 英雄介绍页
     */
    private String isARAMweekfree;

    /**
     * 是否ispermanentweekfree
     */
    private String ispermanentweekfree;
    /**
     * 改动标签
     */
    private String changeLabel;

}
