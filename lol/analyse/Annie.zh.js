if (!LOLherojs) var LOLherojs = {
    champion: {}
};
LOLherojs.champion.Annie = {
    "data": {
        "id": "Annie",
        "key": "1",
        "name": "黑暗之女",
        "title": "安妮",
        "tags": ["Mage"],
        "image": {
            "full": "Annie.png",
            "sprite": "champion0.png",
            "group": "champion",
            "x": 288,
            "y": 0,
            "w": 48,
            "h": 48
        },
        "skins": [{
            "id": "1000",
            "num": 0,
            "name": "default",
            "chromas": false
        }, {
            "id": "1001",
            "num": 1,
            "name": "哥特萝莉 安妮",
            "chromas": false
        }, {
            "id": "1002",
            "num": 2,
            "name": "小红帽 安妮",
            "chromas": false
        }, {
            "id": "1003",
            "num": 3,
            "name": "安妮梦游仙境",
            "chromas": false
        }, {
            "id": "1004",
            "num": 4,
            "name": "舞会公主 安妮",
            "chromas": false
        }, {
            "id": "1005",
            "num": 5,
            "name": "冰霜烈焰 安妮",
            "chromas": false
        }, {
            "id": "1006",
            "num": 6,
            "name": "安伯斯与提妮",
            "chromas": false
        }, {
            "id": "1007",
            "num": 7,
            "name": "科学怪熊的新娘 安妮",
            "chromas": false
        }, {
            "id": "1008",
            "num": 8,
            "name": "“你看见过我的熊猫吗？”安妮",
            "chromas": false
        }, {
            "id": "1009",
            "num": 9,
            "name": "甜心宝贝 安妮",
            "chromas": false
        }, {
            "id": "1010",
            "num": 10,
            "name": "海克斯科技 安妮",
            "chromas": false
        }, {
            "id": "1011",
            "num": 11,
            "name": "银河魔装机神 安妮",
            "chromas": false
        }, {
            "id": "1012",
            "num": 12,
            "name": "十周年纪念 安妮",
            "chromas": false
        }],
        "info": {
            "attack": 2,
            "defense": 3,
            "magic": 10,
            "difficulty": 6
        },
        "spells": [{
            "id": "AnnieQ",
            "name": "碎裂之火",
            "description": "安妮向目标投出注入了法力值的火球，对目标造成魔法伤害。如果目标死于碎裂之火，则碎裂之火消耗的法力值会返还给安妮，且碎裂之火的冷却时间减半。",
            "image": {
                "full": "AnnieQ.png",
                "sprite": "spell0.png",
                "group": "spell",
                "x": 384,
                "y": 144,
                "w": 48,
                "h": 48
            },
            "tooltip": "造成 <scaleAP>(+0.8)<\/scaleAP>魔法伤害。如果目标死于碎裂之火，则返还法力值并且冷却时间减半。",
            "leveltip": {
                "label": ["伤害", "@AbilityResourceName@消耗"],
                "effect": ["", "60\/65\/70\/75\/80"]
            },
            "resource": "60\/65\/70\/75\/80 "
        }, {
            "id": "AnnieW",
            "name": "焚烧",
            "description": "安妮向锥形区域施放一道烈焰，对区域内的所有敌人造成伤害。",
            "image": {
                "full": "AnnieW.png",
                "sprite": "spell0.png",
                "group": "spell",
                "x": 432,
                "y": 144,
                "w": 48,
                "h": 48
            },
            "tooltip": "向锥形区域施放一道烈焰，对区域内的所有敌人造成 <scaleAP>(+0.85)<\/scaleAP>魔法伤害。",
            "leveltip": {
                "label": ["伤害", "@AbilityResourceName@消耗"],
                "effect": ["", "70\/80\/90\/100\/110"]
            },
            "resource": "70\/80\/90\/100\/110 "
        }, {
            "id": "AnnieE",
            "name": "熔岩护盾",
            "description": "为安妮提供百分比伤害减免，爆发性的移动速度加成，并且任何对安妮进行普通攻击的敌人都会受到伤害。",
            "image": {
                "full": "AnnieE.png",
                "sprite": "spell1.png",
                "group": "spell",
                "x": 0,
                "y": 0,
                "w": 48,
                "h": 48
            },
            "tooltip": "安妮为她自己提供%伤害减免，持续秒，以及在秒里持续衰减的移动速度。<br \/><br \/>当护盾处于激活状态时，敌人对护盾进行普攻会受到<scaleAP>(+0.2)<\/scaleAP>魔法伤害。",
            "leveltip": {
                "label": ["伤害减免", "伤害回敬",
                    "冷却时间"],
                "effect": ["%", "", "14\/13\/12\/11\/10"]
            },
            "resource": "20 "
        }, {
            "id": "AnnieR",
            "name": "提伯斯之怒",
            "description": "安妮召唤地狱火泰迪：提伯斯为其作战，对目标区域造成伤害，提伯斯也会攻击和烧伤站在它周围的敌人。",
            "image": {
                "full": "AnnieR.png",
                "sprite": "spell1.png",
                "group": "spell",
                "x": 48,
                "y": 0,
                "w": 48,
                "h": 48
            },
            "tooltip": "召唤提伯斯，对目标范围内的敌人造成<scaleAP>(+0.65)<\/scaleAP>魔法伤害。在接下来的45秒里，提伯斯会灼烧附近的敌人，每秒造成<scaleAP>(+0.1)<\/scaleAP>伤害，并且它的普攻会造成<scaleAP>(+)<\/scaleAP>魔法伤害。安妮可以通过重新激活此技能的方式来控制提伯斯。<br \/><br \/>在安妮死亡时，提伯斯会<font color='#FFEB7F'>暴怒<\/span>，获得275%攻击速度加成和100%移动速度加成，这些加成会在3秒里持续衰减。",
            "leveltip": {
                "label": ["技能冷却时间", "初始伤害",
                    "光环伤害", "提伯斯攻击力",
                    "额外双抗", "额外生命值"],
                "effect": ["120\/100\/80", "", "", "", "", ""]
            },
            "resource": "100 "
        }],
        "passive": {
            "name": "嗜火",
            "description": "在施放4个技能后，安妮的下一次伤害类技能就会对目标造成短暂的晕眩效果。",
            "image": {
                "full": "Annie_Passive.png",
                "sprite": "passive0.png",
                "group": "passive",
                "x": 288,
                "y": 0,
                "w": 48,
                "h": 48
            }
        },
        "lore": "既拥有危险夺命的能力，又拥有小大人儿的可爱模样，安妮是一名掌握着深不可测的占火魔法的幼女魔法师。安妮生活在诺克萨斯北边的山脚下，即使是在这种地方，她也仍然是魔法师中的异类。她与火焰的紧密关系与生俱来，最初是伴随着喜怒无常的情绪冲动出现的，不过后来她学会了如何掌握这些“好玩的小把戏”。其中她最喜欢的就是召唤她亲爱的泰迪熊提伯斯——一头狂野的守护兽。安妮已经迷失在了永恒的天真里。她在黑暗的森林中游荡，始终寻觅着能陪自己玩耍的人。",
        "blurb": "既拥有危险夺命的能力，又拥有小大人儿的可爱模样，安妮是一名掌握着深不可测的占火魔法的幼女魔法师。安妮生活在诺克萨斯北边的山脚下，即使是在这种地方，她也仍然是魔法师中的异类。她与火焰的紧密关系与生俱来，最初是伴随着喜怒无常的情绪冲动出现的，不过后来她学会了如何掌握这些“好玩的小把戏”。其中她最喜欢的就是召唤她亲爱的泰迪熊提伯斯——一头狂野的守护兽。安妮已经迷失在了永恒的天真里。她在黑暗的森林中游荡，始终寻觅着能陪自己玩耍的人。",
        "allytips": [
            "- 安妮的终极必杀技和晕眩技能一起使用能够扭转局势。",
            "- 使用碎裂之火技能杀死小兵，可以让安妮在游戏早期打到很多钱。",
            "- 熔岩护盾能够有效地帮助安妮叠加嗜火技能所带来的晕眩，有时尽早升1级该技能是合算的。"
            ],
        "enemytips": [
            "- 安妮召唤的巨熊提伯斯能烧伤他附近的敌方单位。切记远离被召唤出的提伯斯。",
            "- 召唤师技能惩戒也能够对提伯斯造成伤害。",
            "- 留意安妮身上白色的漩涡能量，这意味着她已经准备好施放眩晕。"
            ],
        "blocks": null
    },
    "version": "10.6.1",
    "updated": "2020-03-24"
};