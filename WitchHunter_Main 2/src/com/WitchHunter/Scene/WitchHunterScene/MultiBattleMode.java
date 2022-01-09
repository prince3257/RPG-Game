package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Animator.Animator;
import com.WitchHunter.Animator.EndingEffectAnimator;
import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Actors.Player;
import com.WitchHunter.GameObject.WitchHunterObject.Map.MapObject;
import com.WitchHunter.GameObject.WitchHunterObject.Monsters.Monster;
import com.WitchHunter.GameObject.WitchHunterObject.Other.Bullet;
import com.WitchHunter.GameObject.WitchHunterObject.Props.Props;
import com.WitchHunter.Internet.Client;
import com.WitchHunter.Internet.CommandReceiver;
import com.WitchHunter.Internet.Server;
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

public class MultiBattleMode extends Scene {

    /** 背景資料 */
    private int backgroundImageWidth; //背景圖片寬
    private int backgroundImageHeight; //背景圖片高
    private Image backgroundImg; //背景圖片
    private Image numbers; //遊戲記錄使用
    private Image skeleton; //遊戲記錄使用
    private Image skeleton2; //遊戲記錄使用
    private Image heart; //遊戲記錄使用
    private Image bullet; //遊戲記錄使用
    private Image slash; //遊戲紀錄使用

    /** 地圖 */
    private Map map;
    private int randomMap;

    /** 遊戲場景中使用的鏡頭 */
    private Camera camera; //主要鏡頭

    /** 玩家資料 */
    private Player hunter;
    private int localTypeRecord; //本地玩家外觀紀錄

    private ArrayList<Props> bag;
    private final int BAG_LIMIT = 5;

    private Player hunter2;
    private int remoteTypeRecord; //遠端玩家外觀紀錄

    /** 遊戲使用物件 */
    private ArrayList<Monster> ghosts;

    private ArrayList<Bullet> bullets1;

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
    private int againstKillAmount; //對手殺敵數

    private int killAmountSingleDigit; //殺敵數個位數
    private int killAmountTenDigit; //殺敵數十位數

    private int hpSingleDigit; //HP個位數
    private int hpTenDigit; //HP十位數

    private Delay monstesrDelay; //怪物產生計時器
    private Delay itemsDelay; //物品產生計時器
    private Delay sceneDelay; //場景轉換計時器

    private boolean thisSceneisOver; //確認當前場景結束

    private boolean first; //確認是否為初始資料

    private EndingEffectAnimator endingAnimator; //結束時的小動畫;

    @Override
    public void sceneBegin() {

        AudioResourceController.getInstance().loop(new Path().sounds().bossSceneBackground(),99);

        action(new CommandReceiver() {
            @Override
            public void run(Client.Command command) {
                switch (command.getCommandCode()) {

                    case Global.Commands.MAP:
                        setRandomMap(Integer.parseInt(command.getStrs().get(0)));
                        System.out.println(randomMap);
                        break;

                    default:
                        break;

                }
            }
        });

        /** 載入地圖＆地圖物件 */
        switch (randomMap) {
            case 1:
                this.map = new Map.Builder().setCemeteryMap().build(Map.Type.CEMETERY);
                break;
            case 2:
                this.map = new Map.Builder().setHauntedHouseMap().build(Map.Type.HAUNTED_HOUSE);
                break;
            case 3:
                this.map = new Map.Builder().setPirateShipMap().build(Map.Type.PIRATE_SHIP);
                break;
        }

        this.hunter = new Player(BORN_X_NORMAL, BORN_Y_NORMAL,localTypeRecord,"Player");
        this.hunter2 = new Player(BORN_X_NORMAL-3 * UNIT_X, BORN_Y_NORMAL,remoteTypeRecord,"Player2");

        /** 準備背景資料 */
        this.backgroundImageWidth = Global.BACKGROUND_WIDTH;
        this.backgroundImageHeight = Global.BACKGROUND_HEIGHT;
        this.backgroundImg = SceneController.instance().irc().tryGetImage(map.type().path());
        this.numbers = SceneController.instance().irc().tryGetImage(new Path().img().numbers().numbers());
        this.skeleton = SceneController.instance().irc().tryGetImage(new Path().img().numbers().skeleton());
        this.skeleton2 = SceneController.instance().irc().tryGetImage(new Path().img().numbers().skeleton2());
        this.heart = SceneController.instance().irc().tryGetImage(new Path().img().numbers().heart());
        this.bullet = SceneController.instance().irc().tryGetImage(new Path().img().numbers().bullet());
        this.slash = SceneController.instance().irc().tryGetImage(new Path().img().numbers().slash());

        /** 準備物件 */
        this.bag = new ArrayList<>();

        this.hpSingleDigit = 0;
        this.hpTenDigit = 0;

        this.killAmount = 0;
        this.againstKillAmount = 0;
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

        //生成怪物陣列
        ghosts = new ArrayList<>();

        //生成道具陣列
        this.items = new ArrayList<>();
        this.bagFrame = SceneController.instance().irc().tryGetImage(new Path().img().objects().itemFrame());
        this.bagContent = SceneController.instance().irc().tryGetImage(new Path().img().objects().itemContent());

        //生成子彈陣列
        this.bullets1 = new ArrayList<>();

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
        //每幾秒生一個怪物
        this.monstesrDelay = new Delay(UPDATE_TIMES_PER_SEC*10);
        this.monstesrDelay.loop();

        //每幾秒生一個道具
        this.itemsDelay = new Delay(UPDATE_TIMES_PER_SEC*10);
        this.itemsDelay.loop();

        /** 轉換場景相關 */
        //場景開始時顯示一般關卡介面說明
        this.first = true;

        //轉換場景才需要
        this.sceneDelay = new Delay(UPDATE_TIMES_PER_SEC*3);

        //確認當前場景是否結束
        this.thisSceneisOver = false;

        //確認當前遊戲是否獲勝
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
        hunter2.paint(g);

        //畫出怪物
        for (Monster ghost : ghosts) {
            ghost.paint(g);
        }

        //畫出子彈
        for (Bullet bullet : bullets1) {
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
        g.drawImage(numbers,camera.painter().right()-(UNIT_X*11)-(UNIT_X/2),camera.painter().top(),camera.painter().right()-(UNIT_X*10)-(UNIT_X/2),camera.painter().top()+(UNIT_Y),100*(int)minutes,0,100+100*(int)minutes,120,null);
        g.drawImage(skeleton,camera.painter().right()-(UNIT_X*10)-(UNIT_X/2),camera.painter().top(),camera.painter().right()-(UNIT_X*10),camera.painter().top()+(UNIT_Y),0,0,250,500,null);
        g.drawImage(numbers,camera.painter().right()-(UNIT_X*10),camera.painter().top(),camera.painter().right()-(UNIT_X*9),camera.painter().top()+(UNIT_Y),100*secondTenDigit,0,100+100*secondTenDigit,120,null);
        g.drawImage(numbers,camera.painter().right()-(UNIT_X*9),camera.painter().top(),camera.painter().right()-(UNIT_X*8),camera.painter().top()+(UNIT_Y),100*secondSingleDigit,0,100+100*secondSingleDigit,120,null);

        //殺敵數
        g.drawImage(skeleton2,camera.painter().right()-(UNIT_X*3),camera.painter().top(),camera.painter().right()-(UNIT_X*2),camera.painter().top()+(UNIT_Y),0,0,436,572,null);
        g.drawImage(numbers,camera.painter().right()-(UNIT_X*2),camera.painter().top(),camera.painter().right()-(UNIT_X),camera.painter().top()+(UNIT_Y),100*killAmountTenDigit,0,100+100*killAmountTenDigit,120,null);
        g.drawImage(numbers,camera.painter().right()-(UNIT_X),camera.painter().top(), camera.painter().right(),camera.painter().top()+(UNIT_Y),100*killAmountSingleDigit,0,100+100*killAmountSingleDigit,120,null);

        //印出背包框框＆底色＆擁有的道具
        for(int i = 0; i<BAG_LIMIT; i++) {
            g.drawImage(bagFrame,camera.painter().right()-80-((UNIT_X+20)*i), camera.painter().bottom()-20-UNIT_Y,UNIT_X,UNIT_Y,null);
        }
        for(int i = 0; i<bag.size(); i++) {
            Props item = bag.get(i);
            if(item != null) {
                g.drawImage(bagContent,camera.painter().right()-80-((UNIT_X+20)*(BAG_LIMIT-1-i)), camera.painter().bottom()-20-UNIT_Y,UNIT_X,UNIT_Y,null);
                g.drawImage(item.getImg(),camera.painter().right()-80-((UNIT_X+20)*(BAG_LIMIT-1-i)), camera.painter().bottom()-20-UNIT_Y,UNIT_X,UNIT_Y,null);
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

        //讓畫布回歸
        camera.endCamera(g);

    }

    @Override
    public void update() {
        updateFrame(hunter,PLAYER1);
        updatePlayerHP(hunter,PLAYER1);
        updatePlayerKillAmount(PLAYER1);

        action(new CommandReceiver() {
            @Override
            public void run(Client.Command command) {
                switch (command.getCommandCode()) {
                    case Global.Commands.MOVE:
                        if (command.getSerialNum() == 2) {
                            if (command.getStrs().get(0).equals("A") || command.getStrs().get(0).equals("a")) {
                                if(hunter2.getPlayerHP()>=0){
                                    hunter2.changeState(Animator.State.WALK);
                                    hunter2.setDir(Direction.LEFT);
                                    hunter2.move();
                                }
                                leftCollision(hunter2, hunter2.speed());
                            }
                            if (command.getStrs().get(0).equals("D") || command.getStrs().get(0).equals("d")) {

                                if(hunter2.getPlayerHP()>=0){
                                    hunter2.changeState(Animator.State.WALK);
                                    hunter2.setDir(Direction.RIGHT);
                                    hunter2.move();
                                }
                                rightCollision(hunter2, hunter2.speed());
                            }
                            if (command.getStrs().get(0).equals("W") || command.getStrs().get(0).equals("w")) {

                                if(hunter2.getPlayerHP()>=0){
                                    hunter2.changeState(Animator.State.WALK);
                                    hunter2.setDir(Direction.UP);
                                    hunter2.move();
                                }
                                topCollision(hunter2, hunter2.speed());
                            }
                            if (command.getStrs().get(0).equals("S") || command.getStrs().get(0).equals("s")) {

                                if(hunter2.getPlayerHP()>=0){
                                    hunter2.changeState(Animator.State.WALK);
                                    hunter2.setDir(Direction.DOWN);
                                    hunter2.move();
                                }
                                bottomCollision(hunter2, hunter2.speed());
                            }
                        }
                        break;
                    case Global.Commands.UPDATE_FRAME:
                        if(!command.getStrs().isEmpty()){
                            if(command.getSerialNum() == PLAYER2) {
                                hunter2.setNewX(Integer.parseInt(command.getStrs().get(0)));
                                hunter2.setNewY(Integer.parseInt(command.getStrs().get(1)));
                            }
                        }
                        break;
                    case Global.Commands.TIME:
                        setPassTime(Integer.parseInt(command.getStrs().get(0)));
                        break;
                    case Global.Commands.HP:
                        if(command.getSerialNum() == PLAYER2) {
                            hunter2.setPlayerHP(Integer.parseInt(command.getStrs().get(0)));
                        }
                        break;
                    case Global.Commands.KILLAMOUNT:
                        againstKillAmount = Integer.parseInt(command.getStrs().get(0));
                    default:
                        break;

                }
            }
        });

        hunter.update();
        hunter2.update();

        /** 更新倒數計時 */
        //時間到完畢切換場景
        if(thisSceneisOver){
            endingAnimator.update();
        }

        if(passTime>=TIME_LIMIT || (hunter2.getPlayerHP() <=0 || hunter.getPlayerHP() <= 0)){
            if(!thisSceneisOver){
                if(passTime < TIME_LIMIT){
                    if(hunter.getPlayerHP() <= 0){
                        endingAnimator.setEndingEffect(EndingEffectAnimator.State.FAIL);
                        System.out.println("player2WIN");
                    } else if(hunter2.getPlayerHP() <= 0) {
                        endingAnimator.setEndingEffect(EndingEffectAnimator.State.FINALE);
                        System.out.println("player1WIN");
                    }
                } else {
                    if(killAmount>againstKillAmount){
                        endingAnimator.setEndingEffect(EndingEffectAnimator.State.FINALE);
                        System.out.println("player2WIN");
                    } else if(killAmount<againstKillAmount) {
                        endingAnimator.setEndingEffect(EndingEffectAnimator.State.FAIL);
                        System.out.println("player1WIN");
                    }else {
                        endingAnimator.setEndingEffect(EndingEffectAnimator.State.FINALE);
                        System.out.println("平手");
                    }
                }
                thisSceneisOver = true;
                sceneDelay.play();
            }
        }

        if(sceneDelay.count()){
            //GameOveScene -> 回主畫面
            AudioResourceController.getInstance().stop(new Path().sounds().bossSceneBackground());
//            try {
//                Client.getInstance().closeConnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            SceneController.instance().change(new GameOverScene());
        }

        //更新倒數時間
//        passTime = ((System.nanoTime()/1000000000)-START_TIME);
        second = 60 - (passTime%60);
        if(second == 60){
            second = 0;
        }
        secondSingleDigit = (int)second%10;
        secondTenDigit = (int)second/10;
        minutes = (180 - passTime)/60;

//        定時定量生成怪物
        if(monstesrDelay.count()){
            if(first){
                //初始生成
                switch (map.type()) {
                    case CEMETERY:
                        generateNewGhost(DEFAULT_SMALL_MONSTER_AMOUNT, Monster.Type.MUMMY_DOG);
                        generateNewGhost(DEFAULT_MEDIUM_MONSTER_AMOUNT, Monster.Type.ZOMBIE);
                        generateNewGhost(DEFAULT_BIG_MONSTER_AMOUNT, Monster.Type.MASTER_GHOST);
                        break;
                    case HAUNTED_HOUSE:
                        generateNewGhost(DEFAULT_SMALL_MONSTER_AMOUNT, Monster.Type.WAILING_GHOST);
                        generateNewGhost(DEFAULT_MEDIUM_MONSTER_AMOUNT, Monster.Type.PHANTOM_WATCH);
                        generateNewGhost(DEFAULT_BIG_MONSTER_AMOUNT, Monster.Type.SOUL_TEDDY);
                        break;
                    case PIRATE_SHIP:
                        generateNewGhost(DEFAULT_SMALL_MONSTER_AMOUNT, Monster.Type.BONE_FISH);
                        generateNewGhost(DEFAULT_MEDIUM_MONSTER_AMOUNT, Monster.Type.PHANTOM_WATCH);
                        generateNewGhost(DEFAULT_BIG_MONSTER_AMOUNT, Monster.Type.SKELETON);
                        break;
                }
                first = false;
            } else {
                if(ghosts.size()<BATTLEGROUND_MONSTER_LIMIT){
                    switch (map.type()){
                        case CEMETERY:
                            generateNewGhost(1, Monster.Type.MUMMY_DOG);
                            break;
                        case HAUNTED_HOUSE:
                            generateNewGhost(1, Monster.Type.WAILING_GHOST);
                            break;
                        case PIRATE_SHIP:
                            generateNewGhost(1, Monster.Type.BONE_FISH);
                    }
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
//        hunter.update();

        hpSingleDigit = hunter.getPlayerHP()%10;
        hpTenDigit = hunter.getPlayerHP()/10;

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
            if(!thisSceneisOver){
                if (ghost.isDetectPlayer(hunter) || ghost.state() == Monster.State.TRACKING) {
                    if(!detectGhost(ghost.type())){
                        rightCollision(ghost, ghost.getSpeed());
                        leftCollision(ghost,ghost.getSpeed());
                        topCollision(ghost,ghost.getSpeed());
                        bottomCollision(ghost, ghost.getSpeed());
                    }
                    ghost.update(hunter);
                } else {
                    if(!detectGhost(ghost.type())){
                        rightCollision(ghost, ghost.getSpeed());
                        leftCollision(ghost,ghost.getSpeed());
                        topCollision(ghost,ghost.getSpeed());
                        bottomCollision(ghost, ghost.getSpeed());
                    }
                    ghost.update();
                }
            }
        }

        //更新場上所有子彈物件1
        for(int i = 0; i < bullets1.size(); i++) {
            Bullet bullet = bullets1.get(i);
            //子彈爆炸狀態要刪除
            if(bullet.shouldEliminate()){
                bullets1.remove(i--);
                continue;
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

    /** 監聽相關 */

    @Override
    public void keyPressed(int commandCode, long trigTime) {

        if (commandCode == Global.D) {
            if(hunter.isActorHaunted()){
                leftCollision(hunter, hunter.speed());
            } else {
                rightCollision(hunter, hunter.speed());
            }
            ArrayList<String> arr = new ArrayList<String>();
            arr.add("D");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        if (commandCode == Global.A) {
            if(hunter.isActorHaunted()){
                rightCollision(hunter, hunter.speed());
            } else {
                leftCollision(hunter, hunter.speed());
            }
            ArrayList<String> arr = new ArrayList<String>();
            arr.add("A");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        if (commandCode == Global.W) {
            if(hunter.isActorHaunted()){
                bottomCollision(hunter, hunter.speed());
            } else {
                topCollision(hunter, hunter.speed());
            }
            ArrayList<String> arr = new ArrayList<String>();
            arr.add("W");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        if (commandCode == Global.S) {
            if(hunter.isActorHaunted()){
                topCollision(hunter, hunter.speed());
            } else {
                bottomCollision(hunter, hunter.speed());
            }
            ArrayList<String> arr = new ArrayList<String>();
            arr.add("S");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        //判斷主角有沒有撞牆
//        if (commandCode == KeyEvent.VK_D) {
//            rightCollision(hunter, hunter.speed());
//        }
//        if (commandCode == KeyEvent.VK_A) {
//            leftCollision(hunter, hunter.speed());
//        }
//        if (commandCode == KeyEvent.VK_W) {
//            topCollision(hunter, hunter.speed());
//        }
//        if (commandCode == KeyEvent.VK_S) {
//            bottomCollision(hunter, hunter.speed());
//        }

        hunter.keyPressed(commandCode, trigTime);

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        hunter.keyReleased(commandCode, trigTime);

        if (commandCode == KeyEvent.VK_1 && bag.size() > 0) {
            if (bag.get(0) != null) {
                checkItem(bag.get(0));
                bag.remove(0);
            }
        } else if (commandCode == KeyEvent.VK_2 && bag.size() > 1) {
            if (bag.get(1) != null) {
                checkItem(bag.get(1));
                bag.remove(1);
            }
        } else if (commandCode == KeyEvent.VK_3 && bag.size() > 2) {
            if (bag.get(2) != null) {
                checkItem(bag.get(2));
                bag.remove(2);
            }
        } else if (commandCode == KeyEvent.VK_4 && bag.size() > 3) {
            if (bag.get(3) != null) {
                checkItem(bag.get(3));
                bag.remove(3);
            }
        } else if (commandCode == KeyEvent.VK_5 && bag.size() > 4) {
            if (bag.get(4) != null) {
                checkItem(bag.get(4));
                bag.remove(4);
            }
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime){

        camera.mouseTrig(e, state, trigTime);

        //主角面對方向 -> 要有多種方向的動畫才能
        if (state == CommandSolver.MouseState.MOVED) {
            hunter.facingDirection(e.getX() + camera.painter().left(), e.getY() + camera.painter().top());
        }

        //點擊射出子彈
        if (state == CommandSolver.MouseState.CLICKED) {
            if (bulletAmount > 0) {
                AudioResourceController.getInstance().shot(new Path().sounds().shotting());
                bullets1.add(new Bullet(
                        e.getX() + camera.painter().left(), e.getY() + camera.painter().top(),
                        hunter.painter().centerX(), hunter.painter().centerY(), bulletSpeed, bulletPower));
                bulletAmount--;
            }
        }

    }

    /** 網路相關 */

    public void start() {
        Server.getInstance().start();
    }

    public void action(CommandReceiver commandReceiver) {
        Client.getInstance().consume(commandReceiver);
    }

    public void updateFrame(GameObject o, int id) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(Integer.toString(o.painter().left()));
        arr.add(Integer.toString(o.painter().top()));
        Client.getInstance().sendCommand(new Client.Command(id, Global.Commands.UPDATE_FRAME,IP,arr));
    }

    public void updateTime(int id){
        ArrayList<String> arr = new ArrayList<>();
        arr.add(String.valueOf(passTime));
        Client.getInstance().sendCommand(new Client.Command(id ,Global.Commands.TIME,IP,arr));
        System.out.println("送出時間");
    }

    public void setPassTime(long passTime) {
        this.passTime = passTime;
    }

    public void updateMap(int id){
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(Integer.toString(randomMap));
        Client.getInstance().sendCommand(new Client.Command(id,Global.Commands.MAP,IP,arr));
    }

    public void setRandomMap(int randomMap) {
        this.randomMap = randomMap;
    }

    public void updatePlayerHP(GameObject o, int id){
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(Integer.toString(hunter.getPlayerHP()));
        Client.getInstance().sendCommand(new Client.Command(id,Global.Commands.HP,IP,arr));
    }

    public void updatePlayerKillAmount(int id){
        ArrayList<String> arr = new ArrayList<>();
        arr.add(Integer.toString(killAmount));
        Client.getInstance().sendCommand(new Client.Command(id,Global.Commands.KILLAMOUNT,IP,arr));
    }
    /** 使用道具相關 */

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
                if(bulletAmount>BULLET_AMOUNT){
                    bulletAmount = BULLET_AMOUNT;
                }
                AudioResourceController.getInstance().shot(new Path().sounds().reloadBullet());
                break;
            case EMPOWER_BULLET:
                if(bulletPower<=BULLET_POWER_LIMIT){
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

    public void setActorsAppearance(int local, int remote) {
        this.localTypeRecord = local;
        this.remoteTypeRecord = remote;
    }

    public void setNewMap(int random){
        this.randomMap = random;
    }

}
