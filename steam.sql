/*
 Navicat Premium Dump SQL

 Source Server         : zx
 Source Server Type    : MySQL
 Source Server Version : 90001 (9.0.1)
 Source Host           : localhost:3306
 Source Schema         : steam

 Target Server Type    : MySQL
 Target Server Version : 90001 (9.0.1)
 File Encoding         : 65001

 Date: 03/06/2025 10:12:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int NOT NULL,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (1, 'zx', 'zx');

-- ----------------------------
-- Table structure for bananas
-- ----------------------------
DROP TABLE IF EXISTS `bananas`;
CREATE TABLE `bananas`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `N` int UNSIGNED NOT NULL,
  `R` int UNSIGNED NOT NULL,
  `SR` int UNSIGNED NOT NULL,
  `SSR` int UNSIGNED NOT NULL,
  `UR` int UNSIGNED NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bananas
-- ----------------------------
INSERT INTO `bananas` VALUES ('a', 12, 27, 7, 2, 2);
INSERT INTO `bananas` VALUES ('qq', 0, 0, 0, 0, 0);
INSERT INTO `bananas` VALUES ('ww', 13, 9, 2, 1, 0);
INSERT INTO `bananas` VALUES ('zx', 14, 10, 1, 0, 0);

-- ----------------------------
-- Table structure for games
-- ----------------------------
DROP TABLE IF EXISTS `games`;
CREATE TABLE `games`  (
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `price` double NOT NULL,
  `overview` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of games
-- ----------------------------
INSERT INTO `games` VALUES ('仙剑奇侠传七', '动作冒险', 75, 128, '经典IP续作，即时制战斗革新尝试');
INSERT INTO `games` VALUES ('光明记忆：无限', '动作冒险/射击', 77, 48, '国产单人FPS，画面表现力惊艳');
INSERT INTO `games` VALUES ('全面战争：三国', '策略/历史模拟', 85, 268, '水墨风三国史诗，军政经全面战争模拟');
INSERT INTO `games` VALUES ('双人成行', '动作冒险/解谜', 89, 198, '创意双人合作游戏，年度最佳游戏获得者');
INSERT INTO `games` VALUES ('双人成行2', '动作冒险/解谜', 91, 198, '全新合作谜题，延续前作创意玩法');
INSERT INTO `games` VALUES ('只狼：影逝二度', '动作冒险/类银河城', 90, 268, '忍者题材高难度战斗，拼刀机制独具匠心');
INSERT INTO `games` VALUES ('哈迪斯', '肉鸽/动作冒险', 93, 80, '希腊神话roguelike，流畅战斗与丰富剧情结合');
INSERT INTO `games` VALUES ('奥日与黑暗森林', '类银河城/平台跳跃', 88, 68, '绝美艺术风格，情感充沛的平台冒险');
INSERT INTO `games` VALUES ('幽灵行者', '动作冒险/赛博朋克', 83, 148, '赛博朋克跑酷砍杀，一击必杀的快节奏体验');
INSERT INTO `games` VALUES ('怪物猎人：世界', '动作冒险/开放世界', 88, 203, '共斗狩猎巅峰之作，生态系统栩栩如生');
INSERT INTO `games` VALUES ('戴森球计划', '模拟经营/沙盒/策略', 92, 70, '自动化生产线与星际探索的完美结合');
INSERT INTO `games` VALUES ('文明6', '策略/模拟经营', 88, 199, '再来一回合的魔性，人类文明发展模拟器');
INSERT INTO `games` VALUES ('星际拓荒', '解谜/开放世界', 95, 80, '太空考古时间循环，年度最佳叙事体验');
INSERT INTO `games` VALUES ('星露谷物语', '模拟经营/沙盒', 89, 48, '现代农场模拟经典，像素风种田治愈神作');
INSERT INTO `games` VALUES ('暖雪', '肉鸽/动作冒险', 88, 58, '国风暗黑快节奏战斗，Build组合丰富');
INSERT INTO `games` VALUES ('暗影火炬城', '类银河城/动作冒险', 83, 108, '柴油朋克兽人主角，精致横版关卡设计');
INSERT INTO `games` VALUES ('最终幻想7 重制版', '日式角色扮演/动作冒险', 87, 446, '经典IP现代重生，电影化叙事与高速战斗结合');
INSERT INTO `games` VALUES ('杀戮尖塔', '肉鸽/卡牌策略', 89, 80, '卡组构建roguelike，策略深度与随机性完美平衡');
INSERT INTO `games` VALUES ('极乐迪斯科', '角色扮演/视觉小说', 92, 116, '哲学侦探RPG，文本量震撼的文学杰作');
INSERT INTO `games` VALUES ('死亡细胞', '肉鸽/类银河城', 91, 80, '像素风快节奏战斗，随机地图百玩不腻');
INSERT INTO `games` VALUES ('永劫无间', '动作冒险/格斗/射击', 81, 98, '东方武侠吃鸡，飞索系统与冷兵器对决');
INSERT INTO `games` VALUES ('沙石镇时光', '模拟经营/沙盒', 80, 78, '荒漠小镇重建，种田与社交结合');
INSERT INTO `games` VALUES ('泰拉瑞亚', '沙盒/动作冒险', 93, 36, '2D沙盒神作，探索建造与战斗的完美融合');
INSERT INTO `games` VALUES ('港诡实录', '恐怖/解谜', 70, 56, '香港都市传说改编，沉浸式恐怖体验');
INSERT INTO `games` VALUES ('烟火', '恐怖/解谜', 94, 36, '中式民俗恐怖，层层反转的深刻叙事');
INSERT INTO `games` VALUES ('生死狙击2', '射击/MOBA', 72, 0, '免费国产FPS，枪械手感持续优化中');
INSERT INTO `games` VALUES ('神界：原罪2', '策略/角色扮演', 93, 133, 'CRPG巅峰之作，高自由度战术战斗');
INSERT INTO `games` VALUES ('空洞骑士', '类银河城/动作冒险', 90, 48, '手绘风虫族世界，精妙地图设计与Boss战');
INSERT INTO `games` VALUES ('笼中窥梦', '解谜/视觉小说', 89, 38, '视错觉创意解谜，年度独立游戏黑马');
INSERT INTO `games` VALUES ('纪元：变异', '动作冒险/射击/赛博朋克', 82, 78, '2D/3D场景切换，赛博都市侦探冒险');
INSERT INTO `games` VALUES ('艾尔登法环', '动作冒险/开放世界', 96, 298, '史诗级开放世界，高难度战斗与宏大叙事结合');
INSERT INTO `games` VALUES ('蔚蓝', '平台跳跃/解谜', 94, 68, '高难度像素跳跃，抑郁症主题治愈叙事');
INSERT INTO `games` VALUES ('赛博朋克2077', '动作冒险/开放世界', 76, 298, '未来都市开放世界，剧情分支深刻，优化持续改进中');
INSERT INTO `games` VALUES ('边境', '射击/科幻', 79, 98, '无重力太空FPS，枪械改装系统深度');
INSERT INTO `games` VALUES ('逆转裁判123', '视觉小说/解谜', 87, 162, '法庭辩论经典，层层反转的侦探推理');
INSERT INTO `games` VALUES ('部落与弯刀', '沙盒/策略/动作冒险', 83, 48, '西域幻想题材，多周目继承系统');
INSERT INTO `games` VALUES ('雨中冒险2', '肉鸽/射击/动作冒险', 85, 80, '3D化roguelike射击，道具叠加效果夸张');
INSERT INTO `games` VALUES ('霓虹深渊', '肉鸽/射击/动作冒险', 85, 58, '赛博风地牢冒险，道具叠加效果惊艳');
INSERT INTO `games` VALUES ('风来之国', '动作冒险/解谜', 84, 68, '精美像素美术，末世背景温馨叙事');
INSERT INTO `games` VALUES ('鬼谷八荒', '动作冒险/沙盒', 86, 68, '国产修仙题材，随机生成江湖奇遇');

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
  `gameName` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reviews
-- ----------------------------
INSERT INTO `reviews` VALUES ('赛博朋克2077', '夜之城的细节令人窒息！每个霓虹灯牌都藏着故事，这才是未来都市该有的样子。', '夜行猎人', '2023-12-25 19:32:45');
INSERT INTO `reviews` VALUES ('赛博朋克2077', '更新后的2.1版本终于让我的3080能稳定60帧了，蠢驴这次真的在努力！', '显卡拯救者', '2023-12-28 14:15:09');
INSERT INTO `reviews` VALUES ('艾尔登法环', '300小时全成就达成！黄金树之巅的战斗让我手抖了半小时，这就是魂系的魅力！', '褪色者本褪', '2023-11-11 23:59:12');
INSERT INTO `reviews` VALUES ('艾尔登法环', 'DLC快来吧！已经刷完所有武器+25，现在天天在交界地当太阳骑士', '阳光枪信徒', '2024-01-05 08:45:37');
INSERT INTO `reviews` VALUES ('双人成行', '和女朋友通关后她居然主动要求玩二周目！这游戏拯救了我的情人节！', '爱情催化剂', '2023-02-14 21:13:28');
INSERT INTO `reviews` VALUES ('双人成行', '小象那段哭成狗...年度最佳合作游戏当之无愧，哈金工作室永远的神！', '泪腺爆破者', '2023-03-18 16:27:54');
INSERT INTO `reviews` VALUES ('永劫无间', '飞索振刀太爽快了！就是遇到火男三排队容易血压飙升...', '妖刀姬本命', '2023-10-01 10:05:17');
INSERT INTO `reviews` VALUES ('永劫无间', '新赛季通行证质量在线！青龙皮肤特效值回票价，继续肝段位！', '国服季沧海', '2023-10-05 18:44:32');
INSERT INTO `reviews` VALUES ('戴森球计划', '流水线强迫症患者的福音！看着星际物流网络自动运转比看ASMR还解压', '星际工程师', '2023-09-01 09:09:09');
INSERT INTO `reviews` VALUES ('戴森球计划', '建议改名《戴森球模拟器》，已经100小时了还没飞出初始星系...', '重力陷阱', '2023-09-15 22:30:45');
INSERT INTO `reviews` VALUES ('鬼谷八荒', '开局被鸡哥追着打，修仙修出黑魂的感觉也是没谁了！', '逆天凡人', '2023-07-07 13:14:15');
INSERT INTO `reviews` VALUES ('鬼谷八荒', '更新后的双修系统太真实了吧！道侣居然会因为我送错礼物离家出走...', '情劫渡劫中', '2023-08-20 17:17:17');
INSERT INTO `reviews` VALUES ('暗影火炬城', '柴油朋克+国风美学！每个场景都能当壁纸，国产银河城的标杆之作', '柴油兔子', '2023-06-06 06:06:06');
INSERT INTO `reviews` VALUES ('暗影火炬城', 'Boss战设计绝了！特别是武师父的太极拳见招拆招，建议申遗！', '功夫熊猫', '2023-06-18 12:12:12');
INSERT INTO `reviews` VALUES ('暖雪', 'Build多样性惊人！冰2流派秒天秒地，就是有时候太强导致索然无味...', '七剑修罗', '2023-05-05 05:05:05');
INSERT INTO `reviews` VALUES ('暖雪', '每次重开都能发现新剧情碎片，这个雪到底要暖到第几周目才能真相大白？', '雪国侦探', '2023-05-20 20:20:20');
INSERT INTO `reviews` VALUES ('霓虹深渊', '道具叠加效果太魔性了！见过自带跟踪的加特林火箭炮吗？这游戏就能实现！', '赛博仓鼠', '2023-04-04 04:04:04');
INSERT INTO `reviews` VALUES ('霓虹深渊', '宠物系统比主角还抢戏！我的哈士奇蛋居然能进化成三头犬，惊了！', '铲屎官MAX', '2023-04-30 19:19:19');
INSERT INTO `reviews` VALUES ('风来之国', '像素美术的天花板！末世背景下的温情叙事让我想起《最后生还者》', '怀旧旅人', '2023-03-03 03:03:03');
INSERT INTO `reviews` VALUES ('风来之国', '大地之子小游戏比本体还上头！建议单独出个Roguelike模式！', 'NES考古学家', '2023-03-21 15:15:15');
INSERT INTO `reviews` VALUES ('边境', '无重力射击体验独一无二！就是新人容易被老六从各种诡异角度狙杀...', '太空幽灵', '2023-02-02 02:02:02');
INSERT INTO `reviews` VALUES ('边境', '枪械改装系统堪比《逃离塔科夫》，建议改名《逃离空间站》！', '军火商人', '2023-02-22 22:22:22');
INSERT INTO `reviews` VALUES ('仙剑奇侠传七', '即时战斗比前作进步明显！就是剧情转折有点强行，我的月清疏啊...', '情怀玩家', '2023-01-01 01:01:01');
INSERT INTO `reviews` VALUES ('仙剑奇侠传七', '光追效果惊艳！仙霞派场景让我在显卡燃烧中重温童年感动', '显卡煎蛋师', '2023-01-15 12:12:12');
INSERT INTO `reviews` VALUES ('纪元：变异', '2D转3D的镜头切换太丝滑了！赛博都市的每个霓虹灯都在讲故事', '次元穿梭者', '2022-12-12 12:12:12');
INSERT INTO `reviews` VALUES ('纪元：变异', '侦探支线比主线还精彩！建议出个《纪谷探案集》DLC！', '赛博福尔摩斯', '2022-12-24 18:18:18');
INSERT INTO `reviews` VALUES ('笼中窥梦', '视错觉解谜的巅峰！通关后看现实世界都觉得能旋转拼接...', '空间魔术师', '2022-11-11 11:11:11');
INSERT INTO `reviews` VALUES ('笼中窥梦', '退伍军人线看破防了...这游戏应该拿年度叙事奖！', '心灵捕手', '2022-11-30 23:59:59');
INSERT INTO `reviews` VALUES ('沙石镇时光', '比波西亚更丰富的制造系统！就是沙漠种田真的不会被晒黑吗？', '荒漠工匠', '2022-10-10 10:10:10');
INSERT INTO `reviews` VALUES ('沙石镇时光', 'NPC好感度系统升级好评！现在每天给欧文送汉堡看他脸红超有趣！', '恋爱模拟器', '2022-10-31 17:17:17');
INSERT INTO `reviews` VALUES ('生死狙击2', '免费游戏这个品质真的可以！就是外挂检测需要再加强些...', '白嫖战士', '2022-09-09 09:09:09');
INSERT INTO `reviews` VALUES ('生死狙击2', '机甲模式比本体还上头！建议单独出个《机甲纪元》！', '钢铁洪流', '2022-09-28 14:14:14');
INSERT INTO `reviews` VALUES ('光明记忆：无限', '国产画面天花板！子弹时间+光剑连招爽到飞起，就是流程太短了...', '国单之光', '2022-08-08 08:08:08');
INSERT INTO `reviews` VALUES ('光明记忆：无限', '舒雅的黑丝质感真实物理效果！这很科学（狗头）', 'LSP代表', '2022-08-25 20:20:20');
INSERT INTO `reviews` VALUES ('烟火', '中式恐怖不需要Jump Scare！细思极恐的细节才是真吓人...', '民俗学者', '2022-07-07 07:07:07');
INSERT INTO `reviews` VALUES ('烟火', '通关后给家里打了电话，有些心结真的需要及时解开...', '破防玩家', '2022-07-20 19:19:19');
INSERT INTO `reviews` VALUES ('港诡实录', '香港都市传说比鬼还可怕！玩完都不敢坐电梯了...', '恐高症患者', '2022-06-06 06:06:06');
INSERT INTO `reviews` VALUES ('港诡实录', '佳慧女士荣获年度最恐怖NPC！建议申遗！', '作死小分队', '2022-06-18 18:18:18');
INSERT INTO `reviews` VALUES ('部落与弯刀', '西域版骑砍！带着美女军团征服大漠的感觉太棒了！', '沙漠之鹰', '2022-05-05 05:05:05');
INSERT INTO `reviews` VALUES ('部落与弯刀', 'MOD支持才是本体！现在游戏时长1/3在玩创意工坊内容...', 'MOD狂魔', '2022-05-20 12:12:12');
INSERT INTO `reviews` VALUES ('赛博朋克2077', '这他妈就是个半成品！宣传片诈骗！玩了10小时主线卡成PPT，蠢驴吃屎去吧！', '退款专家', '2023-01-10 00:00:00');
INSERT INTO `reviews` VALUES ('赛博朋克2077', '基努里维斯都救不了这坨屎！开车手感像在开肥皂，BUG多到能写本百科全书！', '2077怨种', '2023-01-15 12:34:56');
INSERT INTO `reviews` VALUES ('原神', '抄袭狗滚出游戏圈！抽卡系统就是赌博，米哈游全家暴毙！', '任豚狂怒', '2023-05-20 18:00:00');
INSERT INTO `reviews` VALUES ('原神', '648一单零提升！客服机器人只会复读\"旅行者请耐心等待\"，等你妈棺材板都烂了！', '氪金乞丐', '2023-06-06 06:06:06');
INSERT INTO `reviews` VALUES ('战地2042', 'DICE脑瘫晚期！128人战场卡成幻灯片，载具平衡就是一坨狗屎！', '战地遗老', '2023-03-03 03:03:03');
INSERT INTO `reviews` VALUES ('战地2042', '这逼游戏也配叫战地？专家系统毁经典，地图设计是实习生用脚画的吧？', 'EA亲妈升天', '2023-03-15 15:15:15');
INSERT INTO `reviews` VALUES ('最后生还者2', '顽皮狗全员脑残！高尔夫球杆敲碎玩家情怀，艾比这丑逼应该下地狱！', '乔尔亡魂', '2023-07-20 20:20:20');
INSERT INTO `reviews` VALUES ('最后生还者2', '政治正确毁游戏！LGBTQ+圣母剧情恶心到吐，给零分都嫌多！', '白左去死', '2023-07-25 23:59:59');
INSERT INTO `reviews` VALUES ('动物派对', '三年跳票就这？物理引擎像屎一样，服务器烂得连他妈都认不出来！', '被鸽之王', '2023-09-01 09:09:09');
INSERT INTO `reviews` VALUES ('动物派对', '定价198抢劫呢？这破玩意最多值38，开发组集体进ICU吧！', '诈骗受害者', '2023-09-10 10:10:10');
INSERT INTO `reviews` VALUES ('魔兽争霸3重制版', '暴雪已死！重制版比原版还垃圾，过场动画是实习生用PPT做的吧？', '暴白转暴黑', '2023-04-01 00:00:00');
INSERT INTO `reviews` VALUES ('魔兽争霸3重制版', '退款！这他妈就是贴图替换器！中国玩家不配拥有完整剧情？操！', '亡灵天灾', '2023-04-15 12:00:00');
INSERT INTO `reviews` VALUES ('星空', 'B社孝子快来洗地！加载界面比游戏时间还长，这他妈就是2023年度游戏？', '陶德亲爹', '2023-09-06 06:06:06');
INSERT INTO `reviews` VALUES ('原子之心', '苏穗宗模拟器！冰箱媚宅露骨，这游戏应该改名叫《原子批股》！', '政治猎巫人', '2023-02-25 19:19:19');
INSERT INTO `reviews` VALUES ('暗黑破坏神：不朽', '暴雪婊子养的！氪金才能刷秘境？网易滚出游戏圈！', '暗黑老狗', '2023-08-08 08:08:08');
INSERT INTO `reviews` VALUES ('暗黑破坏神：不朽', '手机烫得能煎蛋！零氪玩家连狗都不如，这游戏应该改名叫《pay to win》！', '氪金奴隶', '2023-08-18 18:18:18');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NULL DEFAULT NULL,
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `balance` double UNSIGNED NOT NULL,
  `package` int NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '1', '1', 0, 0);
INSERT INTO `users` VALUES (1, '111', '11', 0, 0);
INSERT INTO `users` VALUES (4, 'a', 'a', 4859, 20);
INSERT INTO `users` VALUES (3, 'cch', 'cch', 0, 0);
INSERT INTO `users` VALUES (2, 'lyx', 'lyx', 200, 0);
INSERT INTO `users` VALUES (1, 'qq', 'qq', 324, NULL);
INSERT INTO `users` VALUES (1, 'ww', 'ww', 282, 0);
INSERT INTO `users` VALUES (1, 'zx', 'zx', 219.2, 2);

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gameName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`username`, `gameName`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC, `gameName` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES ('a', '双人成行2', '2025-06-02 01:29:47');
INSERT INTO `warehouse` VALUES ('a', '只狼：影逝二度', '2025-05-29 22:05:53');
INSERT INTO `warehouse` VALUES ('a', '奥日与黑暗森林', '2025-05-29 22:06:03');
INSERT INTO `warehouse` VALUES ('a', '幽灵行者', '2025-05-31 06:32:12');
INSERT INTO `warehouse` VALUES ('a', '怪物猎人：世界', '2025-05-29 20:28:18');
INSERT INTO `warehouse` VALUES ('a', '最终幻想7 重制版', '2025-06-02 00:51:25');
INSERT INTO `warehouse` VALUES ('qq', '仙剑奇侠传七', '2025-06-03 09:49:42');
INSERT INTO `warehouse` VALUES ('qq', '光明记忆：无限', '2025-06-03 09:53:00');
INSERT INTO `warehouse` VALUES ('ww', '全面战争：三国', '2025-06-03 09:59:13');
INSERT INTO `warehouse` VALUES ('zx', '光明记忆：无限', '2025-06-03 09:48:00');
INSERT INTO `warehouse` VALUES ('zx', '全面战争：三国', '2025-06-03 09:48:09');
INSERT INTO `warehouse` VALUES ('zx', '双人成行2', '2025-05-30 00:55:40');
INSERT INTO `warehouse` VALUES ('zx', '只狼：影逝二度', '2025-06-02 00:40:27');
INSERT INTO `warehouse` VALUES ('zx', '哈迪斯', '2025-06-02 00:36:51');
INSERT INTO `warehouse` VALUES ('zx', '奥日与黑暗森林', '2025-05-29 23:21:46');
INSERT INTO `warehouse` VALUES ('zx', '幽灵行者', '2025-06-02 00:22:47');
INSERT INTO `warehouse` VALUES ('zx', '怪物猎人：世界', '2025-05-29 23:14:24');
INSERT INTO `warehouse` VALUES ('zx', '文明6', '2025-06-02 00:25:56');
INSERT INTO `warehouse` VALUES ('zx', '星际拓荒', '2025-06-02 00:22:20');
INSERT INTO `warehouse` VALUES ('zx', '空洞骑士', '2025-05-30 11:27:16');

-- ----------------------------
-- Table structure for wishlist
-- ----------------------------
DROP TABLE IF EXISTS `wishlist`;
CREATE TABLE `wishlist`  (
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gameName` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime NULL DEFAULT NULL,
  UNIQUE INDEX `username`(`username` ASC, `gameName` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wishlist
-- ----------------------------
INSERT INTO `wishlist` VALUES ('test', '双人成行', '2025-06-01 23:04:42');
INSERT INTO `wishlist` VALUES ('test', '哈迪斯', '2025-06-01 23:02:59');
INSERT INTO `wishlist` VALUES ('ww', '全面战争：三国', '2025-06-03 09:59:14');
INSERT INTO `wishlist` VALUES ('zx', '双人成行2', '2025-06-02 00:22:17');
INSERT INTO `wishlist` VALUES ('zx', '幽灵行者', '2025-05-30 00:01:37');
INSERT INTO `wishlist` VALUES ('zx', '暖雪', '2025-06-03 09:47:37');

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- song list
-- ----------------------------
DROP TABLE IF EXISTS `songs`;
CREATE TABLE `songs` (
    `song_id` int(11) NOT NULL AUTO_INCREMENT,
    `song_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `song_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    UNIQUE INDEX `song_id`(`song_id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- songs
-- ----------------------------
INSERT INTO songs (song_name, song_path)
VALUES ('両翼のBrilliance', '/FRONT/images/music/song1.mp3');
