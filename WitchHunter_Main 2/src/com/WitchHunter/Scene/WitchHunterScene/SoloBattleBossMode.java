package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Animator.BossAnimator;
import com.WitchHunter.Animator.EndingEffectAnimator;
import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Actors.Player;
import com.WitchHunter.GameObject.WitchHunterObject.Map.MapObject;
import com.WitchHunter.GameObject.WitchHunterObject.Monsters.Boss;
import com.WitchHunter.GameObject.WitchHunterObject.Monsters.Monster;
import com.WitchHunter.GameObject.WitchHunterObject.Other.BossAttack;
import com.WitchHunter.GameObject.WitchHunterObject.Other.Bullet;
import com.WitchHunter.GameObject.WitchHunterObject.Props.Props;
import com.WitchHunter.Map.Map;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.Cameras.Camera;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.Global;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.WitchHunter.utils.Global.*;

public class SoloBattleBossMode extends Scene {

    /** 背景資料 */
    private int backgroundImageWidth; //背景圖片寬
    private int backgroundImageHeight; //背景圖片高
    private Image backgroundImg; //背景圖片
    private Image letterB; //遊戲記錄使用
    private Image letterO; //遊戲記錄使用
    private Image letterS; //遊戲記錄使用
    private Image numbers; //遊戲記錄使用
    private Image skeleton; //遊戲記錄使用
    private Image skeleton2; //遊戲記錄使用
    private Image heart; //遊戲記錄使用
    private Image bullet; //遊戲記錄使用
    private Image slash; //遊戲紀錄使用

    /** 地圖 */
    private Map map;

    /** 遊戲場景中使用的鏡頭 */
    private Camera camera; //主要鏡頭

    /** 玩家資料 */
    private Player hunter;
    private ArrayList<Props> bag;
    private final int BAG_LIMIT = 5;

    /** 遊戲使用物件 */
    private Boss boss;
    private ArrayList<BossAttack> bossAttacks;

    private ArrayList<Monster> ghosts;

    private ArrayList<Bullet> bullets;
    private int bulletAmount; //玩家子彈總數
    private int bulletAmountSingleDigit; //玩家子彈個位數
    private int bulletAmountTenDigit; //玩家子彈十位數
    private int bulletSpeed; //子彈速度
    private int bulletPower; //子彈力量

    private ArrayList<Props> items;
    private Image bagFrame; //道具欄框框
    private Image bagContent; //道具欄底色

    /** 碰撞替身 */
    private GameObject rightCollision; //右邊偵測碰撞
    private GameObject leftCollision; //左邊偵測碰撞
    private GameObject topCollision; //頂部偵測碰撞
    private GameObject bottomCollision; //底部偵測碰撞

    /** 場景紀錄資料 */
    private long START_TIME; //開始計算時間
    private final long TIME_LIMIT = EVERY_ROUND_TIME; //每局遊戲時間
    private long passTime; //經過時間(秒)
    private long minutes; //倒數分鐘
    private long second; //倒數秒數
    private int secondSingleDigit; //倒數秒數個位數
    private int secondTenDigit; //倒數秒數十位數

    private int killAmount; //殺敵數
    private int killAmountSingleDigit; //殺敵數個位數
    private int killAmountTenDigit; //殺敵數十位數

    private int hpSingleDigit; //HP個位數
    private int hpTenDigit; //HP十位數

    private Delay monstesrDelay; //怪物產生計時器
    private Delay itemsDelay; //物品產生計時器
    private Delay sceneDelay; //場景轉換計時器
    private Delay bossDelay; //魔王狀態更新計時器
    private Delay sceneBeginDelay; //場景開始時顯示魔王關卡提示計時器

    private boolean thisSceneisOver; //確認當前場景結束
    private boolean winThisBattle; //確認是否贏得當前遊戲
    private boolean sceneBeginDisplay; //確認是否印出魔王關卡提示

    private EndingEffectAnimator endingAnimator; //結束時的小動畫;

    @Override
    public void sceneBegin() {

        AudioResourceController.getInstance().stop(new Path().sounds().demonlaugh());
        AudioResourceController.getInstance().stop(new Path().sounds().soloSceneBackground());
        AudioResourceController.getInstance().loop(new Path().sounds().bossSceneBackground(),99);


        /** 載入地圖＆地圖物件 */
        int randomMap = random(1, 2);
        switch (randomMap) {
            case 1:
                this.map = new Map.Builder().setBossWitchMap().build(Map.Type.BOSS_WITCH);
                this.hunter = new Player(BORN_X_BOSS_WITCH, BORN_Y_BOSS_WITCH,1,"Player");
                this.boss = new Boss(BOSS_WITCH_BORN_X,BOSS_WITCH_BORN_Y, Boss.Type.ALTAR);
                break;
            case 2:
                this.map = new Map.Builder().setBossPirateMap().build(Map.Type.BOSS_PIRATE);
                this.hunter = new Player(BORN_X_BOSS_PIRATE, BORN_Y_BOSS_PIRATE,1,"Player");
                this.boss = new Boss(BOSS_PIRATE_BORN_X,BOSS_PIRATE_BORN_Y, Boss.Type.PIRATE);
                break;
        }

        /** 準備背景資料 */
        this.backgroundImageWidth = Global.BACKGROUND_WIDTH;
        this.backgroundImageHeight = Global.BACKGROUND_HEIGHT;
        this.backgroundImg = SceneController.instance().irc().tryGetImage(map.type().path());
        this.letterB = SceneController.instance().irc().tryGetImage(new Path().img().letters().letterB());
        this.letterO = SceneController.instance().irc().tryGetImage(new Path().img().letters().letterO());
        this.letterS = SceneController.instance().irc().tryGetImage(new Path().img().letters().letterS());
        this.numbers = SceneController.instance().irc().tryGetImage(new Path().img().numbers().numbers());
        this.skeleton = SceneController.instance().irc().tryGetImage(new Path().img().numbers().skeleton());
        this.skeleton2 = SceneController.instance().irc().tryGetImage(new Path().img().numbers().skeleton2());
        this.slash = SceneController.instance().irc().tryGetImage(new Path().img().numbers().slash());
        this.heart = SceneController.instance().irc().tryGetImage(new Path().img().numbers().heart());
        this.bullet = SceneController.instance().irc().tryGetImage(new Path().img().numbers().bullet());


        /** 準備物件 */
        this.bag = new ArrayList<>();

        this.hpSingleDigit = 0;
        this.hpTenDigit = 0;

        this.killAmount = 0;
        this.killAmountSingleDigit = 0;
        this.killAmountTenDigit = 0;

        //製作碰撞區
        this.leftCollision = new GameObject(0, 0, 0, 0) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.rightCollision = new GameObject(0, 0, 0, 0) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.topCollision = new GameObject(0, 0, 0, 0) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.bottomCollision = new GameObject(0, 0, 0, 0) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };

        //生成魔王攻擊陣列
        this.bossAttacks = new ArrayList<>();

        //生成怪物陣列
        ghosts = new ArrayList<>();
        switch (map.type()) {
            case CEMETERY:
                generateNewGhost(BOSS_SMALL_MONSTER_AMOUNT, Monster.Type.ZOMBIE);
                generateNewGhost(BOSS_MEDIUM_MONSTER_AMOUNT, Monster.Type.MUMMY_DOG);
                generateNewGhost(BOSS_BIG_MONSTER_AMOUNT, Monster.Type.MASTER_GHOST);
                break;
            case HAUNTED_HOUSE:
                generateNewGhost(BOSS_SMALL_MONSTER_AMOUNT, Monster.Type.WAILING_GHOST);
                generateNewGhost(BOSS_MEDIUM_MONSTER_AMOUNT, Monster.Type.PHANTOM_WATCH);
                generateNewGhost(BOSS_BIG_MONSTER_AMOUNT, Monster.Type.SOUL_TEDDY);
                break;
            case PIRATE_SHIP:
                generateNewGhost(BOSS_SMALL_MONSTER_AMOUNT, Monster.Type.BONE_FISH);
                generateNewGhost(BOSS_MEDIUM_MONSTER_AMOUNT, Monster.Type.PHANTOM_WATCH);
                generateNewGhost(BOSS_BIG_MONSTER_AMOUNT, Monster.Type.SKELETON);
                break;
        }

        //生成道具陣列
        this.items = new ArrayList<>();
        this.bagFrame = SceneController.instance().irc().tryGetImage(new Path().img().objects().itemFrame());
        this.bagContent = SceneController.instance().irc().tryGetImage(new Path().img().objects().itemContent());
        generateNewItem(DEFAULT_BASIC_PROPS_AMOUNT, Props.Category.RECOVERY_POTION);
        generateNewItem(DEFAULT_BASIC_PROPS_AMOUNT, Props.Category.RECOVERY_BULLET);

        //生成子彈陣列
        this.bullets = new ArrayList<>();
        this.bulletAmount = BULLET_AMOUNT;
        this.bulletAmountSingleDigit = 0;
        this.bulletAmountTenDigit = 0;
        this.bulletSpeed = BULLET_SPEED;
        this.bulletPower = BULLET_POWER;

        /** 準備鏡頭 */
        this.camera = new Camera(backgroundImageWidth, backgroundImageHeight);
        this.camera.setTarget(hunter);
        this.camera.setScreenMovingRange(100,15);

        /** 開始計算時間 */
        this.START_TIME = System.nanoTime()/1000000000;
        this.secondSingleDigit = 0;
        this.secondTenDigit = 0;

        /** 建立計時器 */
        //每幾秒更改魔王狀態
        this.bossDelay = new Delay(UPDATE_TIMES_PER_SEC*6);
        this.bossDelay.loop();

        //每幾秒生一個怪物
        this.monstesrDelay = new Delay(UPDATE_TIMES_PER_SEC*3);
        this.monstesrDelay.loop();

        //每幾秒生一個道具
        this.itemsDelay = new Delay(UPDATE_TIMES_PER_SEC*10);
        this.itemsDelay.loop();

        /** 轉換場景相關 */

        //場景開始時顯示魔王關卡
        this.sceneBeginDelay = new Delay(UPDATE_TIMES_PER_SEC*3);
        this.sceneBeginDelay.play();
        this.sceneBeginDisplay = true;

        //轉換場景才需要
        this.sceneDelay = new Delay(UPDATE_TIMES_PER_SEC*3);

        //確認當前場景是否結束
        this.thisSceneisOver = false;

        //確認當前遊戲是否獲勝
        this.winThisBattle = false;
        this.endingAnimator = new EndingEffectAnimator(EndingEffectAnimator.State.FAIL);

    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {

        //開啟主要相機
        camera.startCamera(g);

        //畫出背景底圖背景
        g.drawImage(backgroundImg,0,0, backgroundImageWidth, backgroundImageHeight,null);

        //遮罩
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g.fillRect(0,0,backgroundImageWidth,backgroundImageHeight);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //畫出地圖
        for (MapObject mpobj: map.getMapObjects() ) {
            mpobj.paint(g);
        }

        //畫出玩家
        hunter.paint(g);

        //畫出魔王
        boss.paint(g);

        //畫出魔王招數
        for (BossAttack attack : bossAttacks) {
            attack.paint(g);
        }

        //畫出怪物
        for (Monster ghost : ghosts) {
            ghost.paint(g);
        }

        //畫出子彈
        for (Bullet bullet : bullets) {
            bullet.paint(g);
        }

        //畫出道具
        for (Props item: items) {
            item.paint(g);
        }

        //如果有特別需要
        camera.paint(g);

        //改成Spotlight相機
        camera.cameraWithSpotlight(g);

        //血量
        g.drawImage(heart,camera.painter().left(),camera.painter().top(),camera.painter().left()+(UNIT_X/2),camera.painter().top()+(UNIT_Y/2),0,0,480,420,null);
        g.drawImage(slash,camera.painter().left()+((UNIT_X/2)*3),camera.painter().top(),camera.painter().left()+((UNIT_X/2)*3+(UNIT_X/4)),camera.painter().top()+(UNIT_Y/2),0,0,1200,1200,null);
        g.drawImage(numbers,camera.painter().left()+(UNIT_X/2),camera.painter().top(),camera.painter().left()+(UNIT_X),camera.painter().top()+(UNIT_Y/2),100*hpTenDigit,0,100+100*hpTenDigit,120,null);
        g.drawImage(numbers,camera.painter().left()+(UNIT_X),camera.painter().top(),camera.painter().left()+((UNIT_X/2)*3),camera.painter().top()+(UNIT_Y/2),100*hpSingleDigit,0,100+100*hpSingleDigit,120,null);
        g.drawImage(numbers,camera.painter().left()+((UNIT_X/2)*3+(UNIT_X/4)),camera.painter().top(),camera.painter().left()+((UNIT_X*2)+(UNIT_X/4)),camera.painter().top()+(UNIT_Y/2),100*(HUNTER_HP/10),0,100+100*(HUNTER_HP/10),120,null);
        g.drawImage(numbers,camera.painter().left()+((UNIT_X*2)+(UNIT_X/4)),camera.painter().top(),camera.painter().left()+((UNIT_X/2)*5+(UNIT_X/4)),camera.painter().top()+(UNIT_Y/2),100*(HUNTER_HP%10),0,100+100*(HUNTER_HP%10),120,null);

        //子彈數
        g.drawImage(bullet,camera.painter().left(),camera.painter().top()+(UNIT_Y/2),camera.painter().left()+(UNIT_X/2),camera.painter().top()+(UNIT_Y),0,0,250,250,null);
        g.drawImage(slash,camera.painter().left()+((UNIT_X/2)*3),camera.painter().top()+(UNIT_Y/2),camera.painter().left()+((UNIT_X/2)*3+(UNIT_X/4)),camera.painter().top()+(UNIT_Y),0,0,1200,1200,null);
        g.drawImage(numbers,camera.painter().left()+(UNIT_X/2),camera.painter().top()+(UNIT_Y/2),camera.painter().left()+(UNIT_X),camera.painter().top()+(UNIT_Y),100*bulletAmountTenDigit,0,100+100*bulletAmountTenDigit,120,null);
        g.drawImage(numbers,camera.painter().left()+(UNIT_X),camera.painter().top()+(UNIT_Y/2),camera.painter().left()+((UNIT_X/2)*3),camera.painter().top()+(UNIT_Y),100*bulletAmountSingleDigit,0,100+100*bulletAmountSingleDigit,120,null);
        g.drawImage(numbers,camera.painter().left()+((UNIT_X/2)*3+(UNIT_X/4)),camera.painter().top()+(UNIT_Y/2),camera.painter().left()+((UNIT_X*2)+(UNIT_X/4)),camera.painter().top()+(UNIT_Y),100*(BULLET_AMOUNT/10),0,100+100*(BULLET_AMOUNT/10),120,null);
        g.drawImage(numbers,camera.painter().left()+((UNIT_X*2)+(UNIT_X/4)),camera.painter().top()+(UNIT_Y/2),camera.painter().left()+((UNIT_X/2)*5+(UNIT_X/4)),camera.painter().top()+(UNIT_Y),100*(BULLET_AMOUNT%10),0,100+100*(BULLET_AMOUNT%10),120,null);

        //倒數時間
        if(!thisSceneisOver) {
            g.drawImage(numbers, camera.painter().right() - (UNIT_X * 11) - (UNIT_X / 2), camera.painter().top(), camera.painter().right() - (UNIT_X * 10) - (UNIT_X / 2), camera.painter().top() + (UNIT_Y), 100 * (int) minutes, 0, 100 + 100 * (int) minutes, 120, null);
            g.drawImage(skeleton, camera.painter().right() - (UNIT_X * 10) - (UNIT_X / 2), camera.painter().top(), camera.painter().right() - (UNIT_X * 10), camera.painter().top() + (UNIT_Y), 0, 0, 250, 500, null);
            g.drawImage(numbers, camera.painter().right() - (UNIT_X * 10), camera.painter().top(), camera.painter().right() - (UNIT_X * 9), camera.painter().top() + (UNIT_Y), 100 * secondTenDigit, 0, 100 + 100 * secondTenDigit, 120, null);
            g.drawImage(numbers, camera.painter().right() - (UNIT_X * 9), camera.painter().top(), camera.painter().right() - (UNIT_X * 8), camera.painter().top() + (UNIT_Y), 100 * secondSingleDigit, 0, 100 + 100 * secondSingleDigit, 120, null);
        }
        //殺敵數
//        g.drawImage(skeleton2,camera.painter().right()-(UNIT_X*3),camera.painter().top(),camera.painter().right()-(UNIT_X*2),camera.painter().top()+(UNIT_Y),0,0,436,572,null);
//        g.drawImage(slash,camera.painter().right()-(UNIT_X*2),camera.painter().top(),camera.painter().right(),camera.painter().top()+UNIT_Y,0,0,1200,1200,null);
//        g.drawImage(numbers,camera.painter().right()-(UNIT_X*2),camera.painter().top(),camera.painter().right()-(UNIT_X+UNIT_X/2),camera.painter().top()+(UNIT_Y/2),100*killAmountTenDigit,0,100+100*killAmountTenDigit,120,null);
//        g.drawImage(numbers,camera.painter().right()-(UNIT_X+UNIT_X/2),camera.painter().top(),camera.painter().right()-UNIT_X,camera.painter().top()+(UNIT_Y/2),100*killAmountSingleDigit,0,100+100*killAmountSingleDigit,120,null);
//        g.drawImage(numbers,camera.painter().right()-UNIT_X,camera.painter().top()+(UNIT_Y/2),camera.painter().right()-(UNIT_X/2),camera.painter().top()+UNIT_Y,100*(KILL_AMOUNT_TARGET/10),0,100+100*(KILL_AMOUNT_TARGET/10),120,null);
//        g.drawImage(numbers,camera.painter().right()-(UNIT_X/2),camera.painter().top()+(UNIT_Y/2),camera.painter().right(),camera.painter().top()+UNIT_Y,100*(KILL_AMOUNT_TARGET%10),0,100+100*(KILL_AMOUNT_TARGET%10),120,null);

        //印出背包框框＆底色＆擁有的道具
        for(int i = 0; i<BAG_LIMIT; i++) {
            g.drawImage(bagFrame,camera.painter().right()-90-((UNIT_X+20)*i), camera.painter().bottom()-UNIT_Y*2+15,UNIT_X,UNIT_Y,null);
        }
        for(int i = 0; i<bag.size(); i++) {
            Props item = bag.get(i);
            if(item != null) {
                g.drawImage(bagContent,camera.painter().right()-90-((UNIT_X+20)*(BAG_LIMIT-1-i)), camera.painter().bottom()-UNIT_Y*2+15,UNIT_X,UNIT_Y,null);
                g.drawImage(item.getImg(),camera.painter().right()-90-((UNIT_X+20)*(BAG_LIMIT-1-i)), camera.painter().bottom()-UNIT_Y*2+15,UNIT_X,UNIT_Y,null);
                g.setColor(Color.DARK_GRAY);
                g.fillRoundRect(camera.painter().right()-90-((UNIT_X+20)*(BAG_LIMIT-1-i)),camera.painter().bottom()-UNIT_Y*2,UNIT_X/4,UNIT_Y/4,4,4);
                g.drawImage(numbers,camera.painter().right()-90-((UNIT_X+20)*(BAG_LIMIT-1-i)),camera.painter().bottom()-UNIT_Y*2,camera.painter().right()-90-((UNIT_X+20)*(BAG_LIMIT-1-i))+(UNIT_X/4),camera.painter().bottom()-UNIT_Y*2+(UNIT_Y/4),
                        100*(i+1),0,100+100*(i+1),120,null);
            }
        }

        //場景切換時跑動畫
        if(thisSceneisOver) {
            //黑屏
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g.fillRect(0,0,backgroundImageWidth,backgroundImageHeight);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            //跑特效
            endingAnimator.paintComponent(camera.painter().left(),camera.painter().top(),camera.painter().right(),camera.painter().bottom(),g);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
        //印出魔王關卡提示
        if(sceneBeginDelay.count()){
            sceneBeginDisplay = false;
        }
        if(sceneBeginDisplay){
            g.drawImage(letterB,camera.painter().left()+(UNIT_X),camera.painter().top()+(UNIT_Y*3),400,400,null);
            g.drawImage(letterO,camera.painter().left()+(UNIT_X*5),camera.painter().top()+(UNIT_Y*3),400,400,null);
            g.drawImage(letterS,camera.painter().left()+(UNIT_X*9),camera.painter().top()+(UNIT_Y*3),400,400,null);
            g.drawImage(letterS,camera.painter().left()+(UNIT_X*13),camera.painter().top()+(UNIT_Y*3),400,400,null);
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //讓畫布回歸
        camera.endCamera(g);

    }

    @Override
    public void update() {

        /** 更新倒數計時 */
        //時間到完畢切換場景
        if(thisSceneisOver){
            endingAnimator.update();
        }
        if(passTime>=TIME_LIMIT || hunter.getPlayerHP() <=0 || boss.getBossHP() <= 0){
            if(!thisSceneisOver){
                if(boss.getBossHP()<=0){
                    endingAnimator.setEndingEffect(EndingEffectAnimator.State.FINALE);
                } else {
                    AudioResourceController.getInstance().shot(new Path().sounds().demonlaugh());
                    endingAnimator.setEndingEffect(EndingEffectAnimator.State.FAIL);
                }
                thisSceneisOver = true;
                sceneDelay.play();
            }
        }

        if(sceneDelay.count()){
            if (hunter.getPlayerHP() <=0){
                //GameOveScene -> 回主畫面
                AudioResourceController.getInstance().stop(new Path().sounds().demonlaugh());
                AudioResourceController.getInstance().stop(new Path().sounds().bossSceneBackground());
                AudioResourceController.getInstance().shot(new Path().sounds().girlDead());
                SceneController.instance().change(new GameOverScene());
            } else {
                //回主畫面
                AudioResourceController.getInstance().stop(new Path().sounds().demonlaugh());
                AudioResourceController.getInstance().stop(new Path().sounds().bossSceneBackground());
                SceneController.instance().change(new LoggingScene());
            }
        }

        //更新倒數時間
        passTime = ((System.nanoTime()/1000000000)-START_TIME);
        second = 60 - (passTime%60);
        secondSingleDigit = (int)second%10;
        secondTenDigit = (int)second/10;
        minutes = (180 - passTime)/60;

        //定時定量生成怪物
        if(monstesrDelay.count()){
            if(ghosts.size()<BATTLEGROUND_MONSTER_LIMIT_BOSS){
                switch (map.type()){
                    case BOSS_WITCH:
                        generateNewGhost(1, Monster.Type.ZOMBIE);
                        generateNewGhost(1, Monster.Type.WAILING_GHOST);
                        generateNewGhost(1,Monster.Type.SOUL_TEDDY);
                        break;
                    case BOSS_PIRATE:
                        generateNewGhost(1, Monster.Type.BONE_FISH);
                        generateNewGhost(1, Monster.Type.MUMMY_DOG);
                        generateNewGhost(1, Monster.Type.SKELETON);
                        break;
                }
            }
        }

        //定時定量生成道具
        if(itemsDelay.count()){
            generateNewItem(DEFAULT_BASIC_PROPS_AMOUNT, Props.Category.RECOVERY_POTION);
            generateNewItem(DEFAULT_UPPER_PROPS_AMOUNT, Props.Category.RECOVERY_STATE);
            generateNewItem(DEFAULT_BASIC_PROPS_AMOUNT, Props.Category.RECOVERY_BULLET);
            generateNewItem(DEFAULT_UPPER_PROPS_AMOUNT, Props.Category.EMPOWER_BULLET);
            generateNewItem(DEFAULT_UPPER_PROPS_AMOUNT, Props.Category.FLARE);
            generateNewItem(DEFAULT_UPPER_PROPS_AMOUNT, Props.Category.LANDMINE);
        }

        //更新殺敵數
        killAmountSingleDigit = killAmount%10;
        killAmountTenDigit = killAmount/10;

        /** 更新物件邏輯 */
        //更新玩家物件
        //碰到魔王直接死亡
        if(hunter.isCollision(boss)){
            hunter.beAttacked(HUNTER_HP);
        }
        hunter.update();
        hpSingleDigit = hunter.getPlayerHP()%10;
        hpTenDigit = hunter.getPlayerHP()/10;

        //更新魔王物件
        boss.update();
        if(bossDelay.count()){
            bossCallSkill();
        }

        //更新魔王招式
        for(int i = 0; i< bossAttacks.size(); i++) {
            BossAttack attack = bossAttacks.get(i);
            if(attack.shouldEliminate()) {
                bossAttacks.remove(i--);
                continue;
            }
            if(hunter.collider().overlap(attack.collider())) {
                if(attack.getState() == BossAttack.State.NORMAL) {
                    hunter.beAttacked(attack.getPower());
                    attack.changeState();
                }
            }
            attack.update();
        }

        //更新道具
        for (int i = 0; i < items.size(); i++) {
            Props item = items.get(i);
            if (item.isCollision(hunter)) {
                if(item.getCategory() != Props.Category.LANDMINE) {
                    items.remove(i--);
                    addtoBag(item);
                    continue;
                } else {
                    if(!item.isLandMinePlace() && !item.isShouldExplode()) {
                        items.remove(i--);
                        addtoBag(item);
                        continue;
                    }
                }
            }
            item.update();
            //道具裡面放Delay，如果放太久就要消失
            if (item.state() == Props.State.ELIMINATED) {
                items.remove(i--);
            }
        }

        //更新場上所有怪物物件
        for (int i = 0; i < ghosts.size(); i++) {
            Monster ghost = ghosts.get(i);
            //碰到玩家 -> 改變狀態
            if (ghost.isCollision(hunter)) {
                if (ghost.state() != Monster.State.ELIMINATED) {
                    ghost.changeState(Monster.State.ELIMINATED);
                    AudioResourceController.getInstance().shot(new Path().sounds().girlGetHurt());//受傷音效
                    hunter.getHaunted();
                    hunter.beAttacked(ghost.power());
                }
            }
            //怪物被地雷炸
            for (Props item : items) {
                if (ghost.isCollision(item) && !ghost.state().equals(Monster.State.ELIMINATED)) {
                    if (item.getCategory() == Props.Category.LANDMINE && item.isLandMinePlace()) {
                        ghost.changeState(Monster.State.ELIMINATED);
                        killAmount++;
                        item.setShouldExplode();
                    }
                }
            }

            //幽靈已改狀態 -> 要被消滅
            if (ghost.shouldEliminate()) {
                Monster.Type monsterType = ghost.type();
                killAmount++;
                ghosts.remove(i--);
                //讓系統再生成一隻幽靈
                generateNewGhost(1, monsterType);
                continue;
            }

            //偵測是否卡牆
            //偵測到玩家 -> 往玩家方向移動
            //沒偵測到玩家 -> 原地左右移動
            if(!thisSceneisOver) {
                if (ghost.isDetectPlayer(hunter) || ghost.state() == Monster.State.TRACKING) {
                    if (!detectGhost(ghost.type())) {
                        rightCollision(ghost, ghost.getSpeed());
                        leftCollision(ghost, ghost.getSpeed());
                        topCollision(ghost, ghost.getSpeed());
                        bottomCollision(ghost, ghost.getSpeed());
                    }
                    ghost.update(hunter);
                } else {
                    if (!detectGhost(ghost.type())) {
                        rightCollision(ghost, ghost.getSpeed());
                        leftCollision(ghost, ghost.getSpeed());
                        topCollision(ghost, ghost.getSpeed());
                        bottomCollision(ghost, ghost.getSpeed());
                    }
                    ghost.update();
                }
            }
        }

        //判斷是否殺死魔王通關
        if(boss.getBossHP() <= 0) {
            winThisBattle = true;
        }

        //更新場上所有子彈物件
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            //子彈爆炸狀態要刪除
            if(bullet.shouldEliminate()){
                bullets.remove(i--);
                continue;
            }
            //魔王被射會扣血
            if(boss.collider().overlap(bullet.BulletCollider())){
                if(bullet.getBulletState() == Bullet.State.NORMAL){
                    boss.beAttacked(bulletPower);
                    bullet.changeBulletState();
                }
            }
            //子彈碰到怪物要改狀態
            for (Monster ghost : ghosts) {
                //讓子彈碰到怪物就消失
                if (ghost.state() != Monster.State.ELIMINATED) {
                    if (ghost.painter().overlap(bullet.BulletCollider())) {
                        if (bullet.getBulletState() == Bullet.State.NORMAL) {
                            //怪物扣血
                            ghost.beAttacked(bulletPower);
                            if(ghost.state() != Monster.State.ELIMINATED){
                                ghost.turnOnTrackingMode();
                            }
                            bullet.changeBulletState();
                        }
                    }
                }
            }
            //讓子彈碰到地圖就消失
            for (int j = 0; j < map.getMapObjects().size(); j++) {
                MapObject mapObject = map.getMapObjects().get(j);
                if(mapObject.collider().overlap(bullet.BulletCollider())){
                    bullet.changeBulletState();
                }
            }
            //子彈更新狀態
            bullet.update();

        }

        //更新目前子彈數
        bulletAmountSingleDigit = bulletAmount%10;
        bulletAmountTenDigit = bulletAmount/10;

        //更新鏡頭位置狀態
        camera.update();
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {

        //判斷主角有沒有撞牆
        if(commandCode == KeyEvent.VK_D) {
            if(hunter.isActorHaunted()){
                leftCollision(hunter, hunter.speed());
            } else {
                rightCollision(hunter, hunter.speed());
            }
        }
        if(commandCode == KeyEvent.VK_A) {
            if(hunter.isActorHaunted()){
                rightCollision(hunter, hunter.speed());
            } else {
                leftCollision(hunter, hunter.speed());
            }
        }
        if(commandCode == KeyEvent.VK_W) {
            if(hunter.isActorHaunted()){
                bottomCollision(hunter, hunter.speed());
            } else {
                topCollision(hunter, hunter.speed());
            }
        }
        if(commandCode == KeyEvent.VK_S) {
            if(hunter.isActorHaunted()){
                topCollision(hunter, hunter.speed());
            } else {
                bottomCollision(hunter, hunter.speed());
            }
        }

        hunter.keyPressed(commandCode,trigTime);

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

        hunter.keyReleased(commandCode,trigTime);

        if(commandCode == KeyEvent.VK_1 && bag.size() > 0) {
            if(bag.get(0)!=null) {
                checkItem(bag.get(0));
                bag.remove(0);
            }
        } else
        if(commandCode == KeyEvent.VK_2 && bag.size() > 1) {
            if(bag.get(1)!=null) {
                checkItem(bag.get(1));
                bag.remove(1);
            }
        } else
        if(commandCode == KeyEvent.VK_3 && bag.size() > 2) {
            if(bag.get(2)!=null) {
                checkItem(bag.get(2));
                bag.remove(2);
            }
        } else
        if(commandCode == KeyEvent.VK_4 && bag.size() > 3) {
            if(bag.get(3)!=null) {
                checkItem(bag.get(3));
                bag.remove(3);
            }
        } else
        if(commandCode == KeyEvent.VK_5 && bag.size() > 4) {
            if(bag.get(4)!=null) {
                checkItem(bag.get(4));
                bag.remove(4);
            }
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

        camera.mouseTrig(e,state,trigTime);

        //主角面對方向 -> 要有多種方向的動畫才能
        if(state == CommandSolver.MouseState.MOVED) {
            hunter.facingDirection(e.getX()+camera.painter().left(), e.getY()+camera.painter().top());
        }

        //點擊射出子彈
        if(state == CommandSolver.MouseState.CLICKED) {
            if(bulletAmount>0){
                AudioResourceController.getInstance().shot(new Path().sounds().shotting());
                bullets.add(new Bullet(
                        e.getX()+camera.painter().left(),e.getY()+camera.painter().top(),
                        hunter.painter().centerX(),hunter.painter().centerY(), bulletSpeed,bulletPower));
                bulletAmount--;
            }
        }

    }

    /** 使用道具相關*/

    //使用物品後自動排序包包
    private void addtoBag(Props item) {
        if(bag.size()<BAG_LIMIT) {
            bag.add(item);
        }
    }

    //檢查道具使用對象
    private void checkItem(Props item){
        if(hunter.getPlayerHP()>0){
            hunter.getPropsAnimator().setItemEffect(item.getCategory());
            hunter.setUseItem();
            switch (item.getType()) {
                case PLAYER:
                    item.useOnPlayer(hunter);
                    break;
                case SCENE:
                    useOnScene(item.getCategory());
                    break;
            }
        }
    }

    //道具影響整個場景
    private void useOnScene(Props.Category category) {
        switch (category) {
            case RECOVERY_BULLET:
                this.bulletAmount += BULLET_RECOVERY;
                if(bulletAmount > BULLET_AMOUNT){
                    bulletAmount = BULLET_AMOUNT;
                }
                AudioResourceController.getInstance().shot(new Path().sounds().reloadBullet());
                break;
            case EMPOWER_BULLET:
                if(bulletPower <= BULLET_POWER_LIMIT){
                    this.bulletPower += BULLET_ADDINGPOWER;
                }
                AudioResourceController.getInstance().shot(new Path().sounds().powerUp1());
                break;
            case FLARE:
                this.camera.lookRangeEnlarge();
                AudioResourceController.getInstance().shot(new Path().sounds().BOOM());
                break;
            case LANDMINE:
                Props landMine = new Props(hunter.painter().left(), hunter.painter().top(), Props.Category.LANDMINE);
                landMine.setLandMinePlace();
                items.add(landMine);
                AudioResourceController.getInstance().shot(new Path().sounds().setBomb());
                break;
        }
    }

    /** 魔王施放技能 */

    //魔王呼叫技能
    private void bossCallSkill() {
        int random = Global.random(1,3);
        //確認是否每次update更新狀態 -> TBC
        switch (random){
            default:
            case 1:
                boss.changeState(BossAnimator.State.STAND);
                break;
            case 2:
                int randomNum = Global.random(1,2);
                if(randomNum == 1) {
                    bossFog();
                } else {
                    bossFireBall();
                }
                break;
            case 3:
                boss.changeState(BossAnimator.State.SUMMON_GHOST);
                if(ghosts.size() <= BATTLEGROUND_MONSTER_LIMIT) {
                    int differentMonster = Global.random(1,3);
                    switch (boss.getType()) {
                        case ALTAR:
                            if(differentMonster == 1 ){
                                generateNewGhost(2, Monster.Type.BOOK_GHOST);
                            } else if(differentMonster == 2 ){
                                generateNewGhost(2, Monster.Type.WAILING_GHOST);
                            } else {
                                generateNewGhost(2, Monster.Type.MUMMY_DOG);
                            }
                            break;
                        case PIRATE:
                            if(differentMonster == 1 ){
                                generateNewGhost(2, Monster.Type.ZOMBIE);
                            } else if(differentMonster == 2 ){
                                generateNewGhost(2, Monster.Type.PHANTOM_WATCH);
                            } else {
                                generateNewGhost(2, Monster.Type.BONE_FISH);
                            }
                            break;
                    }
                }
                break;
        }
    }

    //施放毒霧方法
    public void bossFog(){
        boss.changeState(BossAnimator.State.FOG);
        for(int i =0 ;i < 36 ;i++) {
            bossAttacks.add(new BossAttack((int)(Global.BACKGROUND_WIDTH*Math.cos(Math.toRadians(10)*i)), (int)(Global.BACKGROUND_WIDTH*Math.sin(Math.toRadians(10)*i)),
                    boss.painter().centerX(), boss.painter().centerY(),bulletSpeed, BossAttack.Type.POISONFOG));
        }
    }

    //施放火球方法
    public void bossFireBall(){
        boss.changeState(BossAnimator.State.FIREBALL);
        for(int i =0 ;i < 40 ;i++) {
            bossAttacks.add(new BossAttack(Global.random(0,BACKGROUND_WIDTH), Global.random(BORN_DY1,BORN_DY2),
                    Global.random(0,BACKGROUND_WIDTH),-10, bulletSpeed, BossAttack.Type.FIREBALL));
        }
    }

    /** 場景相關方法 */

    //隨機生成幽靈
    private void generateNewGhost(int ghostNum, Monster.Type type) {
        for(int i = 0; i < ghostNum; i++) {
            int x;
            int y;
            do{
                x = Global.random(BORN_DX1, BORN_DX2);
                y = Global.random(BORN_DY1, BORN_DY2);
                x += Global.MAP_OBJECT_WIDTH;
                y += Global.MAP_OBJECT_HEIGHT;
            } while (!isAvailablePosition(new Monster(x,y,type)));
            ghosts.add(new Monster(x, y, type));
        }
    }

    //隨機生成道具
    private void generateNewItem(int itemNum, Props.Category category) {
        for(int i = 0; i < itemNum; i++) {
            int x;
            int y;
            do{
                x = Global.random(BORN_DX1, BORN_DX2);
                y = Global.random(BORN_DY1, BORN_DY2);
                x += Global.MAP_OBJECT_WIDTH;
                y += Global.MAP_OBJECT_HEIGHT;
            } while (!isAvailablePosition(new Props(x,y,category)));
            items.add(new Props(x, y,category));
        }
    }

    //確定生成位置不是地圖物件
    private boolean isAvailablePosition(GameObject object){
        if(object.isCollision(hunter)){
            return false;
        }
        for(int i = 0; i < map.getMapObjects().size(); i++) {
            MapObject mapObject = map.getMapObjects().get(i);
            if(mapObject.isCollision(object)) {
                return false;
            }
        }
        return true;
    }

    /** 地圖上物件的碰撞 -> 可套用GameObject */

    //判斷是否為幽靈 -> 可以穿牆
    private boolean detectGhost(Monster.Type type) {
        return type == Monster.Type.PHANTOM_WATCH || type == Monster.Type.BOOK_GHOST || type == Monster.Type.WAILING_GHOST ||
                type == Monster.Type.MASTER_GHOST || type == Monster.Type.SOUL_TEDDY;
    }

    private void rightCollision(GameObject gameObject, int speed){

        this.rightCollision.setNewXY(gameObject.painter().left()+speed,gameObject.painter().top(),gameObject.painter().width(),gameObject.painter().height());
        for(int i = 0; i < map.getMapObjects().size(); i++) {
            MapObject mapObject = map.getMapObjects().get(i);
            if(rightCollision.isCollision(mapObject)){
                gameObject.setLockRight(true);
                return;
            }
        }
        gameObject.setLockRight(false);
    }

    private void leftCollision(GameObject gameObject, int speed){

        this.leftCollision.setNewXY(gameObject.painter().left()-speed,gameObject.painter().top(),gameObject.painter().width(),gameObject.painter().height());
        for(int i = 0; i < map.getMapObjects().size(); i++) {
            MapObject mapObject = map.getMapObjects().get(i);
            if(leftCollision.isCollision(mapObject)){
                gameObject.setLockLeft(true);
                return;
            }
        }
        gameObject.setLockLeft(false);
    }

    private void topCollision(GameObject gameObject, int speed){

        this.topCollision.setNewXY(gameObject.painter().left(),gameObject.painter().top()-speed,gameObject.painter().width(),gameObject.painter().height());
        for(int i = 0; i < map.getMapObjects().size(); i++) {
            MapObject mapObject = map.getMapObjects().get(i);
            if(topCollision.isCollision(mapObject)){
                gameObject.setLockTop(true);
                return;
            }
        }
        gameObject.setLockTop(false);
    }

    private void bottomCollision(GameObject gameObject, int speed){

        this.bottomCollision.setNewXY(gameObject.painter().left(),gameObject.painter().top()+speed,gameObject.painter().width(),gameObject.painter().height());
        for(int i = 0; i < map.getMapObjects().size(); i++) {
            MapObject mapObject = map.getMapObjects().get(i);
            if(bottomCollision.isCollision(mapObject)){
                gameObject.setLockBottom(true);
                return;
            }
        }
        gameObject.setLockBottom(false);
    }

}
