package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Actors.Player;
import com.WitchHunter.GameObject.WitchHunterObject.Monsters.Monster;
import com.WitchHunter.GameObject.WitchHunterObject.Other.Bullet;
import com.WitchHunter.GameObject.WitchHunterObject.Props.Props;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.Cameras.Camera;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.WitchHunter.utils.Global.*;

public class TeachingScene extends Scene {

    /** 背景資料 */
    private int backgroundImageWidth; //背景圖片寬
    private int backgroundImageHeight; //背景圖片高
    private Image backgroundImg; //背景圖片
    private Image WASDImg; //方向鍵說明圖片
    private Image buttonControlImg; //方向鍵文字說明圖片
    private Image buttonShotImg; //按鍵說明圖片
    private Image buttonUseItemImg; //按鍵說明文字圖片
    private Image useItemImg;
    private Image useItemImg2;
    private Image mouseImg; //滑鼠說明圖片
    private Image gameStartImage; //開始遊戲圖片
    private Image numbers; //遊戲記錄使用
    private Image name_recoverypotion; //補血藥水名字圖片
    private Image name_recoverystate; //回復藥劑名字圖片
    private Image name_recoverybullet; //彈匣名字圖片
    private Image name_empowerbullet; //子彈升級名字圖片
    private Image name_landmine; //炸彈名字圖片
    private Image name_flare; //手電筒名字圖片
    private Image short_recoverypotion; //補血藥水名字圖片
    private Image short_recoverystate; //回復藥劑名字圖片
    private Image short_recoverybullet; //彈匣名字圖片
    private Image short_empowerbullet; //子彈升級名字圖片
    private Image short_landmine; //炸彈名字圖片
    private Image short_flare; //手電筒名字圖片

    /** 遊戲場景中使用的鏡頭 */
    private Camera camera; //主要鏡頭

    /** 玩家資料 */
    private Player hunter;
    private ArrayList<Props> bag;

    /** 遊戲使用物件 */
    private ArrayList<Monster> ghosts;
    private ArrayList<Props> items;
    private ArrayList<Bullet> bullets;
    private final int[] arr = new int[]{0,1,2,3,4,5,6,7,7};//門的動畫陣列
    private Image doorImage;
    private GameObject door;
    private int count;
    private Delay delay;
    private Delay delayForChangeSence;
    private boolean closeDoor;

    //包包
    private final int BAG_LIMIT = 5;
    private Image bagFrame; //道具欄框框
    private Image bagContent; //道具欄底色



    @Override
    public void sceneBegin() {

        /** 載入音效 */
        AudioResourceController.getInstance().loop(new Path().sounds().teachingBackground(),99);

        /** 準備背景資料 */
        this.backgroundImageWidth = WINDOW_WIDTH;
        this.backgroundImageHeight = WINDOW_HEIGHT;
        this.backgroundImg = SceneController.instance().irc().tryGetImage(new Path().img().backgrounds().TeachingBackground());

        this.WASDImg = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_WASD());
        this.buttonControlImg = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_control());

        this.mouseImg = SceneController.instance().irc().tryGetImage(new Path().img().buttons().mouse());
        this.buttonShotImg = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_shot());

        this.buttonUseItemImg = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_useItem());
        this.useItemImg = SceneController.instance().irc().tryGetImage(new Path().img().buttons().useItem());
        this.useItemImg2 = SceneController.instance().irc().tryGetImage(new Path().img().buttons().useItem2());
        this.numbers = SceneController.instance().irc().tryGetImage(new Path().img().numbers().numbers());

        this.doorImage = SceneController.instance().irc().tryGetImage(new Path().img().objects().doorOpenEffect());
        this.gameStartImage = SceneController.instance().irc().tryGetImage(new Path().img().buttons().startGame());
        this.count = 0;
        this.delay = new Delay(UPDATE_TIMES_PER_SEC/4);
        this.delay.loop();
        this.delayForChangeSence = new Delay(UPDATE_TIMES_PER_SEC*2);

        this.name_recoverypotion = SceneController.instance().irc().tryGetImage(new Path().img().objects().name_recoverypotion());
        this.name_recoverybullet = SceneController.instance().irc().tryGetImage(new Path().img().objects().name_recoverybullet());
        this.name_recoverystate = SceneController.instance().irc().tryGetImage(new Path().img().objects().name_recoverystate());
        this.name_empowerbullet = SceneController.instance().irc().tryGetImage(new Path().img().objects().name_empowerbullet());
        this.name_landmine = SceneController.instance().irc().tryGetImage(new Path().img().objects().name_landmine());
        this.name_flare = SceneController.instance().irc().tryGetImage(new Path().img().objects().name_flare());

        this.short_recoverypotion = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().shortdescription_recoverypotion());
        this.short_recoverystate = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().shortdescription_recoverystate());
        this.short_recoverybullet = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().shortdescription_recoverybullet());
        this.short_empowerbullet = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().shortdescription_enpowerbullet());
        this.short_landmine = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().shortdescription_landmine());
        this.short_flare = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().shortdescription_flare());

        /** 準備物件 */
        this.hunter = new Player(WINDOW_WIDTH/2-UNIT_X*3, WINDOW_HEIGHT/2-UNIT_Y*3,1,"教學模式");
        this.ghosts = new ArrayList<>();
        ghosts.add(new Monster(WINDOW_WIDTH/2+UNIT_X*3,WINDOW_HEIGHT/2+UNIT_Y*3, Monster.Type.ZOMBIE));
        this.bag = new ArrayList<>();
        this.bagFrame = SceneController.instance().irc().tryGetImage(new Path().img().objects().itemFrame());
        this.bagContent = SceneController.instance().irc().tryGetImage(new Path().img().objects().itemContent());

        //生成道具陣列
        this.items = new ArrayList<>();
        items.add(new Props(UNIT_X+UNIT_X/2,WINDOW_HEIGHT-(UNIT_Y*2), Props.Category.RECOVERY_POTION));
        items.add(new Props(UNIT_X*3+UNIT_X/2,WINDOW_HEIGHT-(UNIT_Y*2), Props.Category.RECOVERY_BULLET));
        items.add(new Props(UNIT_X*5+UNIT_X/2,WINDOW_HEIGHT-(UNIT_Y*2), Props.Category.RECOVERY_STATE));
        items.add(new Props(UNIT_X*7+UNIT_X/2,WINDOW_HEIGHT-(UNIT_Y*2), Props.Category.EMPOWER_BULLET));
        items.add(new Props(UNIT_X*9+UNIT_X/2,WINDOW_HEIGHT-(UNIT_Y*2), Props.Category.LANDMINE));
        items.add(new Props(UNIT_X*11+UNIT_X/2,WINDOW_HEIGHT-(UNIT_Y*2), Props.Category.FLARE));
        for (Props item : items) {
            item.stopDelay();
        }

        //生成子彈陣列
        this.bullets = new ArrayList<>();

        //創建門實體
        this.door = new GameObject(WINDOW_WIDTH - 250, WINDOW_HEIGHT - 350, 200, 200) {

            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.closeDoor =true;


        /** 準備鏡頭 */
        this.camera = new Camera(backgroundImageWidth, backgroundImageHeight);
        this.camera.setTarget(hunter);
//        this.camera.scaleUpforLookrange();
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {

        //開啟主要相機
        camera.startCamera(g);

        g.drawImage(backgroundImg,0,0, backgroundImageWidth, backgroundImageHeight,null);

        //如果有特別需要
        camera.paint(g);

        //使用Spolight模式
        camera.cameraWithSpotlight(g);

//        g.drawImage(name_recoverypotion, UNIT_X,WINDOW_HEIGHT-UNIT_Y - nameHeight, nameWidth, nameHeight, null);
        g.drawImage(short_recoverypotion, UNIT_X,WINDOW_HEIGHT-(UNIT_Y*4), 100, 100, null);

//        g.drawImage(name_recoverybullet, UNIT_X+(UNIT_X*3),WINDOW_HEIGHT-UNIT_Y - nameHeight, nameWidth, nameHeight, null);
        g.drawImage(short_recoverybullet, UNIT_X*3,WINDOW_HEIGHT-(UNIT_Y*4), 100, 100, null);

//        g.drawImage(name_recoverystate, UNIT_X+(UNIT_X*5),WINDOW_HEIGHT-UNIT_Y - nameHeight, nameWidth, nameHeight, null);
        g.drawImage(short_recoverystate, UNIT_X*5,WINDOW_HEIGHT-(UNIT_Y*4), 100, 100, null);

//        g.drawImage(name_empowerbullet, UNIT_X+(UNIT_X*7),WINDOW_HEIGHT-UNIT_Y - nameHeight, nameWidth, nameHeight, null);
        g.drawImage(short_empowerbullet, UNIT_X*7,WINDOW_HEIGHT-(UNIT_Y*4), 100, 100, null);

//        g.drawImage(name_landmine, UNIT_X+(UNIT_X*9),WINDOW_HEIGHT-UNIT_Y - nameHeight, nameWidth, nameHeight, null);
        g.drawImage(short_landmine, UNIT_X*9,WINDOW_HEIGHT-(UNIT_Y*4), 100, 100, null);

//        g.drawImage(name_flare, UNIT_X+(UNIT_X*11),WINDOW_HEIGHT-UNIT_Y - nameHeight, nameWidth, nameHeight, null);
        g.drawImage(short_flare, UNIT_X*11,WINDOW_HEIGHT-(UNIT_Y*4), 100, 100, null);

        g.drawImage(doorImage, WINDOW_WIDTH - 250, WINDOW_HEIGHT - 350, WINDOW_WIDTH - 50, WINDOW_HEIGHT - 150,
                0,
                96 * arr[count],
                100,
                96 + 96 * arr[count],
                null);

        //畫出背景底圖背景
        g.drawImage(WASDImg, 220,70,120,120,null);
        g.drawImage(buttonControlImg, 180, 190,200,80,null);

        g.drawImage(mouseImg, 600,70,100,100,null);
        g.drawImage(buttonShotImg, 550,190,200,80,null);

        g.drawImage(useItemImg, WINDOW_WIDTH-305, 40,100,100,null);
        g.drawImage(useItemImg2, WINDOW_WIDTH-330, 70,150,150,null);
        g.drawImage(buttonUseItemImg,WINDOW_WIDTH-355,190,200,80,null);

        g.drawImage(gameStartImage, 0, 0, SCREEN_X, SCREEN_Y, null);


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

        //畫出玩家
        hunter.paint(g);

        g.setColor(Color.YELLOW);
        String contentWords = "- 按下ESC回主選單 - ";
        g.drawString(contentWords, 8, 16);

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

        //關閉相機
        camera.endCamera(g);

    }

    @Override
    public void update() {

        if(door.isCollision(hunter)) {
            if (delay.count()) {
                count = ++count % arr.length;
                delayForChangeSence.play();
                if(closeDoor) {//強迫音效僅觸發一次
                    AudioResourceController.getInstance().shot(new Path().sounds().doorOpened());//碰撞門觸發音效
                }
                closeDoor = false;
            }
        }
        if(delayForChangeSence.count()){
            SceneController.instance().change(new SoloBattleMode());
            AudioResourceController.getInstance().stop(new Path().sounds().teachingBackground());//停音樂
        }


        /** 更新物件邏輯 */
        //更新玩家物件
        hunter.update();

        //更新道具
        for (int i = 0; i < items.size(); i++) {
            Props item = items.get(i);
            if (item.isCollision(hunter)) {
                if(!item.isLandMinePlace() && !item.isShouldExplode()) {
                    if(item.isCanTake()){
                        addtoBag(item);
                        item.takeItem();
                    }
                    continue;
                }
            }
            item.update();
            //道具裡面放Delay，如果放太久就要消失
            if (item.state() == Props.State.ELIMINATED) {
                items.remove(i--);
            }
        }


        //更新場上所有怪物物件
        for(int i = 0; i< ghosts.size(); i++) {
            Monster ghost = ghosts.get(i);
            if (ghost.isCollision(hunter)) {
                if (ghost.isCollision(hunter)) {
                    if (ghost.state() != Monster.State.ELIMINATED) {
                        ghost.changeState(Monster.State.ELIMINATED);
                        hunter.getHaunted();
                        AudioResourceController.getInstance().play(new Path().sounds().girlGetHurt());
                        if(hunter.getPlayerHP()>5){
                            hunter.beAttacked(1);
                        }
                    }
                }
            }

            for (Props item : items) {
                if (ghost.isCollision(item) && !ghost.state().equals(Monster.State.ELIMINATED)) {
                    if (item.getCategory() == Props.Category.LANDMINE && item.isLandMinePlace()) {
                        ghost.changeState(Monster.State.ELIMINATED);
                        item.setShouldExplode();
                    }
                }
            }

            if (ghost.shouldEliminate()) {
                ghosts.remove(i--);
                //讓系統再生成一隻幽靈
                ghosts.add(new Monster(WINDOW_WIDTH/2+UNIT_X*3,WINDOW_HEIGHT/2+UNIT_Y*3, Monster.Type.ZOMBIE));
            }

            if (ghost.isDetectPlayer(hunter)) {
                ghost.update(hunter);
            } else {
                ghost.update();
            }
        }


        //更新場上所有子彈物件
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(!bullet.BulletCollider().overlap(camera.collider())){
                bullets.remove(i--);
                continue;
            }
            if (bullet.shouldEliminate()) {
                bullets.remove(i--);
                continue;
            }
            for (Monster ghost : ghosts) {
                if (ghost.state() != Monster.State.ELIMINATED) {
                    if (ghost.painter().overlap(bullet.BulletCollider())) {
                        if (bullet.getBulletState() == Bullet.State.NORMAL) {
                            //怪物扣血
                            ghost.beAttacked(1);
                            bullet.changeBulletState();
                        }
                    }
                }
            }

            bullet.update();
        }

//      更新鏡頭位置狀態
        camera.update();
    }

    @Override
    public void keyPressed ( int commandCode, long trigTime){

        //判斷主角有沒有撞牆
        if (commandCode == KeyEvent.VK_A) {
            if(hunter.isActorHaunted()){
                if(hunter.collider().right()>=WINDOW_WIDTH){
                    hunter.setLockRight(true);
                } else {
                    hunter.setLockRight(false);
                }
            }
        }
        if (commandCode == KeyEvent.VK_W) {
            if(hunter.isActorHaunted()){
                if(hunter.collider().bottom()>=WINDOW_HEIGHT-50){
                    hunter.setLockBottom(true);
                } else {
                    hunter.setLockBottom(false);
                }
            }
        }
        if (commandCode == KeyEvent.VK_D) {
            if(hunter.collider().right()>=WINDOW_WIDTH){
                hunter.setLockRight(true);
            } else {
                hunter.setLockRight(false);
            }
        }
        if (commandCode == KeyEvent.VK_S) {
            if(hunter.collider().bottom()>=WINDOW_HEIGHT-50){
                hunter.setLockBottom(true);
            } else {
                hunter.setLockBottom(false);
            }
        }


        hunter.keyPressed(commandCode, trigTime);

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

        hunter.keyReleased(commandCode,trigTime);

        if(commandCode == KeyEvent.VK_ESCAPE){
            SceneController.instance().change(new LoggingScene());
            AudioResourceController.getInstance().stop(new Path().sounds().teachingBackground());//停音樂
        }

        if(commandCode == KeyEvent.VK_ENTER){
            SceneController.instance().change(new SoloBattleMode());
            AudioResourceController.getInstance().stop(new Path().sounds().teachingBackground());//停音樂
        }

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
        if(commandCode == KeyEvent.VK_6 && bag.size() > 5) {
            if(bag.get(5)!=null) {
                checkItem(bag.get(5));
                bag.remove(5);
            }
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

        //主角面對方向 -> 要有多種方向的動畫才能
        if(state == CommandSolver.MouseState.MOVED) {
            hunter.facingDirection(e.getX()+camera.painter().left(), e.getY()+camera.painter().top());
        }

        //點擊射出子彈
        if(state == CommandSolver.MouseState.CLICKED) {
            AudioResourceController.getInstance().shot(new Path().sounds().shotting());
            bullets.add(new Bullet(
                    e.getX()+camera.painter().left(),e.getY()+camera.painter().top(),
                    hunter.painter().centerX(),hunter.painter().centerY(), BULLET_SPEED,0));
        }

    }

    private void checkItem (Props item){
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

    //道具影響整個場景
    private void useOnScene(Props.Category category) {
        switch (category) {
            case RECOVERY_BULLET:
                AudioResourceController.getInstance().shot(new Path().sounds().reloadBullet());
                break;
            case EMPOWER_BULLET:
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


    //使用物品後自動排序包包
    private void addtoBag(Props item) {
        if(bag.size()<BAG_LIMIT) {
            bag.add(item);
        }
    }

}
