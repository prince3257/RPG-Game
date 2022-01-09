package com.WitchHunter.utils;

public class Global {

    public enum Direction{
        LEFT(1),
        RIGHT(2),
        UP(3),
        DOWN(0);

        private final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

    }

    /** 資料刷新的時間 */
    public static final int UPDATE_TIMES_PER_SEC = 60; //每秒更新60次遊戲邏輯
    public static final int NANOSECOND_PER_UPDATE = 1000000000 / UPDATE_TIMES_PER_SEC; //每過多少奈秒就要更新遊戲邏輯
    /** 畫面更新時間 */
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT; //每過多少奈秒就要更新畫面
    /** 視窗相關 */
    public static final int WINDOW_WIDTH = 1280; //視窗寬
    public static final int WINDOW_HEIGHT = 800; //視窗高
    public static final int SCREEN_X = WINDOW_WIDTH - 8 - 8; //左邊跟右邊會佔8點
    public static final int SCREEN_Y = WINDOW_HEIGHT - 31 - 8; //視窗上面的bar佔31點 下面佔8點
    public static final int BACKGROUND_WIDTH = 3840; //背景圖片寬
    public static final int BACKGROUND_HEIGHT = 2560; //背景圖片高
    /** 遊戲角色單位 */
    public static final int CHACRACTER_UNIT_X = 40; //生成物件寬
    public static final int CHACRACTER_UNIT_Y = 40; //生成物件高
    public static final int UNIT_X = 64; //生成物件寬
    public static final int UNIT_Y = 64; //生成物件高
    public static final int CUTRANGE = 48; //角色切割範圍(動畫)
    public static final float HUNTER_LOOKRANGE_MIN = 400f; //角色可視範圍下限
    public static final float HUNTER_LOOKRANGE_MAX = 3000f; //角色可視範圍上限

    public static final int HUNTER_SPEED = 5; //角色移動速度
    public static final int HUNTER_SPEED_WEAK = 2; //角色虛弱速度
    public static final int HUNTER_HP = 50; //角色血量
    public static final int HUNTER_RECOVERY_HP = 6; //角色回血量
    public static final int HUNTER_HP_WIDTH = 2; //角色血量寬度
    public static final int HUNTER_HP_HEIGHT = 4; //角色血量高度
    public static final int BORN_X_NORMAL = BACKGROUND_WIDTH/2; //一般地圖x座標
    public static final int BORN_Y_NORMAL = BACKGROUND_HEIGHT/2+300; //一般地圖y座標
    public static final int BORN_X_BOSS_WITCH = BACKGROUND_WIDTH / 2; //女巫Bosse關角色生成x座標
    public static final int BORN_Y_BOSS_WITCH = BACKGROUND_HEIGHT / 2 + 800; //女巫Boss關角色生成y座標
    public static final int BORN_X_BOSS_PIRATE = BACKGROUND_WIDTH/2 - 500; //海盜Boss關角色生成x座標
    public static final int BORN_Y_BOSS_PIRATE = BACKGROUND_HEIGHT/2; //海盜Boss關角色生成y座標
    /** 子彈單位 */
    public static final int BULLET_AMOUNT = 25; //子彈數量上限
    public static final int BULLET_POWER_LIMIT = 15; //子彈數量上限
    public static final int BULLET_ADDINGPOWER = 1; //子彈增加力量
    public static final int BULLET_RECOVERY = 10; //子彈補充
    public static final int BULLET_SPEED = 8; //子彈飛行速度
    public static final int BULLET_POWER = 3; //子彈攻擊力
    public static final int BULLET_WIDTH = 20; //子彈寬度
    public static final int BULLET_HEIGHT = 20; //子彈高度
    /** 其他數值(道具、攻擊目標等等) */
    public static final int KILL_AMOUNT_TARGET = 20; //攻擊數量目標
    public static final int DEFAULT_BASIC_PROPS_AMOUNT = 3; //一般道具數量
    public static final int DEFAULT_UPPER_PROPS_AMOUNT = 2; //進階道具數量
    /** 怪物角色單位*/
    public static final int DEFAULT_SMALL_MONSTER_AMOUNT = 5; //初始怪獸數量
    public static final int DEFAULT_MEDIUM_MONSTER_AMOUNT = 3; //初始怪獸數量
    public static final int DEFAULT_BIG_MONSTER_AMOUNT = 2; //初始怪獸數量
    public static final int BOSS_SMALL_MONSTER_AMOUNT = 3; //初始怪獸數量
    public static final int BOSS_MEDIUM_MONSTER_AMOUNT = 2; //初始怪獸數量
    public static final int BOSS_BIG_MONSTER_AMOUNT = 1; //初始怪獸數量

    public static final int BOSS_WIDTH = 384; //BOSS怪物寬
    public static final int BOSS_HEIGHT = 384; //BOSS怪物高
    public static final int BOSS_HP = 100; //BOSS血量
    public static final int BOSS_HP_WIDTH = 2; //BOSS血量寬度
    public static final int BOSS_HP_HEIGHT = 7; //BOSS血量高度
    public static final int BOSS_POWER = 11; //BOSS攻擊力

    public static final int BIG_MONSTER_WIDTH = 96; //大型怪物寬
    public static final int BIG_MONSTER_HEIGHT = 128; //大型怪物高
    public static final int BIG_MONSTER_HP = 60; //大怪獸血量
    public static final int BIG_MONSTER_POWER = 9; //大怪獸攻擊力
    public static final int BIG_MONSTER_SPEED = 2; //大怪獸速度
    public static final int BIG_MONSTER_LOOKRANGE = 350; //大怪獸可視範圍

    public static final int MEDIUM_MONSTER_HP = 40; //中怪獸血量
    public static final int MEDIUM_MONSTER_POWER = 7; //中怪獸攻擊力
    public static final int MEDIUM_MONSTER_SPEED = 3; //中怪獸速度
    public static final int MEDIUM_MONSTER_LOOKRANGE = 400; //中怪獸可視範圍

    public static final int SMALL_MONSTER_HP = 20; //小怪獸血量
    public static final int SMALL_MONSTER_POWER = 5; //小怪獸攻擊力
    public static final int SMALL_MONSTER_SPEED = 4; //小怪獸速度
    public static final int SMALL_MONSTER_LOOKRANGE = 500; //小怪獸可視範圍

    public static final int BOSS_WITCH_BORN_X = BACKGROUND_WIDTH / 2 - 200; //女巫Boss生成x座標
    public static final int BOSS_WITCH_BORN_Y = BACKGROUND_HEIGHT / 2 - 200; //女巫Boss生成y座標
    public static final int BOSS_PIRATE_BORN_X = BACKGROUND_WIDTH / 2 - 100; //海盜Boss生成x座標
    public static final int BOSS_PIRATE_BORN_Y = BACKGROUND_HEIGHT / 2 - 200; //海盜Boss生成y座標
    public static final int BORN_DX1 = 256; //生成怪物範圍(左上x)
    public static final int BORN_DY1 = 160; //生成怪物範圍(左上x)
    public static final int BORN_DX2 = BACKGROUND_WIDTH - BORN_DX1; //生成怪物範圍(左上x)
    public static final int BORN_DY2 = BACKGROUND_HEIGHT - BORN_DY1; //生成怪物範圍(左上x)

    public static final int MONSTER_WALK_SPEED = 1; //怪獸行走速度
    public static final int WALK_DELAY_TIME = 1; //怪獸行走倒數

    public static final int BATTLEGROUND_MONSTER_LIMIT = 30;//場上怪物數量限制
    public static final int BATTLEGROUND_MONSTER_LIMIT_BOSS = 15;//場上怪物數量限制

    /** 遊戲時間單位 */
    public static final long EVERY_ROUND_TIME = 3L * 60; //每局三分鐘
    /** 地圖物件單位 */
    public static final int MAP_OBJECT_WIDTH = 64;
    public static final int MAP_OBJECT_HEIGHT = 64;

    /** 網路測試用 */
    public static final String IP = "172.20.10.11";
    public static final String SERVERIP = "127.0.0.1";

    public class Commands{
        public static final int MOVE = 1;
        public static final int TIME = 2;
        public static final int UPDATE_FRAME = 3;
        public static final int MAP = 4;
        public static final int HP = 5;
        public static final int KILLAMOUNT = 6;
    }

    //keyevent
    public static final int A = 65;
    public static final int W = 87;
    public static final int D = 68;
    public static final int S = 83;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    /** Debug mode使用 */
    public static final boolean IS_DEBUG = false;
    /** 常用的Random函式 */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
