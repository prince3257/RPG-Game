package com.WitchHunter.GameObject.WitchHunterObject.Props;

import com.WitchHunter.Animator.BulletAnimator;
import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Actors.Player;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Path;
import java.awt.*;
import static com.WitchHunter.utils.Global.*;

public class Props extends GameObject {

    public enum Category{

        RECOVERY_POTION(new Path().img().objects().propsRecoveryPotion(),Type.PLAYER),
        RECOVERY_BULLET(new Path().img().objects().propsRecoveryBullet(),Type.SCENE),
        EMPOWER_BULLET(new Path().img().objects().propsEmpowerBullet(),Type.SCENE),
        RECOVERY_STATE(new Path().img().objects().propsRecoveryState(),Type.PLAYER),
        FLARE(new Path().img().objects().propsFlare(),Type.SCENE),
        LANDMINE(new Path().img().objects().propsLandmine(),Type.SCENE);

        private final String path;
        private final Type type;

        Category(String path, Type type) {
            this.path = path;
            this.type = type;
        }

    }

    public enum Type{
        PLAYER,
        SCENE
    }

    public enum State{
        EXIST,
        ELIMINATED
    }

    private final Category category; //道具類別
    private final Type type; //道具屬性
    private final Image img; //道具圖片
    private final Image landmindExplode; //地雷改變
    private State state; //道具狀態
    private final Delay delay; //道具改變狀態計時器
    private boolean landMinePlace; //放置地雷
    private boolean shouldExplode; //要爆炸
    private final BulletAnimator animator; //爆炸動畫
    private boolean canTake; //是否可以拿取 -> 用在TeachingScene
    private Delay canTakeDelay; //自動改變可拿取的狀態 -> 用在TeachingScene

    public Props(int x, int y, Category category) {
        super(x, y, CHACRACTER_UNIT_X, CHACRACTER_UNIT_Y);
        this.category = category;
        this.img = SceneController.instance().irc().tryGetImage(category.path);
        this.landmindExplode = SceneController.instance().irc().tryGetImage(new Path().img().objects().propsLandmineExplode());
        this.type = category.type;
        this.state = State.EXIST;
        this.delay = new Delay(UPDATE_TIMES_PER_SEC*15);
        this.delay.play();
        this.landMinePlace = false;
        this.shouldExplode = false;
        this.animator = new BulletAnimator();
        this.canTake = true;
        this.canTakeDelay = new Delay(UPDATE_TIMES_PER_SEC);
    }

    @Override
    public void paintComponent(Graphics g) {
        if(shouldExplode){
            animator.paintComponent(painter().left()-200, painter().top()-200, painter().right()+200, painter().bottom()+200,g);
        } else {
            if(category == Category.LANDMINE && landMinePlace){
                g.drawImage(landmindExplode, painter().left(), painter().top(), painter().width(), painter().height(), null);
            } else {
                g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
            }
        }
    }

    @Override
    public void update() {

        //如果是放置地雷 -> 倒數消失暫停
        if (landMinePlace) {
            delay.stop();
        }
        //如果沒有拿到就消失
        if(delay.count()) {
            this.state = State.ELIMINATED;
        }
        //如果有爆炸就跑特效
        if(shouldExplode){
            animator.update();
        }

        if(canTakeDelay.count()){
            this.canTake = true;
        }

    }

    //針對使用在玩家身上的道具
    public void useOnPlayer(Player player){
        switch (category) {
            case RECOVERY_POTION:
                AudioResourceController.getInstance().shot(new Path().sounds().recoverWater());
                player.recoverHP();
                break;
            case RECOVERY_STATE:
                AudioResourceController.getInstance().shot(new Path().sounds().powerUp2());
                player.recoverSpeed();
                break;
        }
    }

    //地雷碰到後爆炸
    public void setShouldExplode() {
        shouldExplode = true;
        landMinePlace = false;
        animator.expolde();
        delay.setCountLimit(UPDATE_TIMES_PER_SEC);
        delay.play();
        AudioResourceController.getInstance().shot(new Path().sounds().bombBoom());
    }

    //判斷是否是放置後的地雷
    public boolean isLandMinePlace() {
        return landMinePlace;
    }

    //放置地雷
    public void setLandMinePlace() {
        this.landMinePlace = true;
    }

    public boolean isShouldExplode() {
        return shouldExplode;
    }

    //在遊戲畫面時可以同步圖片
    public Image getImg() {
        return img;
    }

    //取得道具狀態
    public State state() {
        return state;
    }

    //取得道具種類
    public Category getCategory() {
        return category;
    }

    //取得道具使用對象
    public Type getType() {
        return type;
    }

    //暫停更新的計時
    public void stopDelay() {
        this.delay.stop();
    }

    //拿取物品(TeachingScene)
    public void takeItem() {
        this.canTake = false;
        this.canTakeDelay.play();
    }

    //確認是否可以拿取物品
    public boolean isCanTake() {
        return canTake;
    }
}
