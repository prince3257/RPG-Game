package com.WitchHunter.GameObject.WitchHunterObject.Monsters;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.Circle;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Actors.Player;
import com.WitchHunter.Animator.MonsterAnimator;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Path;

import java.awt.*;

import static com.WitchHunter.utils.Global.*;

public class Monster extends GameObject {

    /**
     * 怪物類型
     */
    public enum Type {
        //墓園怪
        MUMMY_DOG(Ability.SPEED_DOWN, Size.SMALL, new Path().img().monsters().cemetery().mummyDog()),
        ZOMBIE(Ability.POWER_DOWN, Size.MEDIUM, new Path().img().monsters().cemetery().zombie()),
        MASTER_GHOST(Ability.CURSE, Size.BIG, new Path().img().monsters().cemetery().masterGhost()),

        //鬼屋怪
        WAILING_GHOST(Ability.POWER_DOWN, Size.SMALL, new Path().img().monsters().hauntedHouse().wailingGhost()),
        BOOK_GHOST(Ability.POWER_DOWN, Size.MEDIUM, new Path().img().monsters().hauntedHouse().bookGhost()),
        SOUL_TEDDY(Ability.SPEED_DOWN, Size.BIG, new Path().img().monsters().hauntedHouse().soulTeddy()),

        //海盜船怪
        BONE_FISH(Ability.SPEED_DOWN, Size.SMALL, new Path().img().monsters().pirateShip().boneFish()),
        PHANTOM_WATCH(Ability.POWER_DOWN, Size.MEDIUM, new Path().img().monsters().pirateShip().phantomWatch()),
        SKELETON(Ability.CURSE, Size.BIG, new Path().img().monsters().pirateShip().skeleton());

        private final Ability ability;//怪物能力
        private final Size size;//怪物大小
        private final String path;//怪物圖片路徑

        Type(Ability ability, Size size, String path) {
            this.ability = ability;
            this.size = size;
            this.path = path;
        }

        /**
         * 取得圖片路徑
         *
         * @return 圖片路徑
         */
        public String path() {
            return path;
        }
    }

    /**
     * 怪物大小
     */
    private enum Size {
        SMALL, //小型怪
        MEDIUM, //中型怪
        BIG //大型怪
    }

    /**
     * 怪物能力
     */
    private enum Ability {
        SPEED_DOWN, //使玩家速度變慢
        POWER_DOWN, // 使玩家攻擊力變弱
        CURSE //使玩家受詛咒，時間內無法攻擊(或視野減少)
    }

    /**
     * 怪物狀態
     */
    public enum State {
        WALK, //走路狀態
        RUN, //跑步狀態
        TRACKING, // 追蹤模式
        ELIMINATED //被消滅狀態
    }

    private final Type type;//怪物類型
    private Circle lookRange;//怪物可視範圍
    private int power;//怪物攻擊力
    private final int speed_walk;//怪物行走速度
    private int speed_run;//怪物跑速
    private int hp;//怪物血量
    private State state;//怪物狀態
    private final Delay delay;//行走延遲 -> 死亡時為死亡延遲
    private int count;//行走計數
    private Direction dir;//怪物行走方向
    private final MonsterAnimator animator;//怪物動畫
    private boolean newBorn; //是否新生
    private final Delay newBornAnimateDelay; //新生動畫更新計時器
    private int newBorncount;
    private final Delay newBornDelay; //新生計時器
    private final Image newBornEffect; //新生特效圖
    private final int[] newBornAnimatearr; //新生特效陣列
    private final Delay trackingDelay; //追蹤狀態改變

    public Monster(int x, int y, Type type) {
        super(x, y, UNIT_X, UNIT_Y);
        this.type = type;
        this.speed_walk = MONSTER_WALK_SPEED;
        this.newBorn = true;
        this.newBornAnimatearr = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        this.newBornEffect = SceneController.instance().irc().tryGetImage(new Path().img().monsters().newborneffect());
        this.newBornAnimateDelay = new Delay(0);
        this.newBornDelay = new Delay(UPDATE_TIMES_PER_SEC*4);
        newBornDelay.play();
        newBornAnimateDelay.loop();
        this.newBorncount = 0;
        this.trackingDelay = new Delay(UPDATE_TIMES_PER_SEC*3);
        //依照怪物大小設定可視範圍、攻擊力、跑速、血量、大型怪物重製大小
        switch (type.size) {
            case SMALL:
                lookRange = new Circle(x, y, SMALL_MONSTER_LOOKRANGE);
                power = SMALL_MONSTER_POWER;
                speed_run = SMALL_MONSTER_SPEED;
                hp = SMALL_MONSTER_HP;
                break;
            case MEDIUM:
                lookRange = new Circle(x, y, MEDIUM_MONSTER_LOOKRANGE);
                power = MEDIUM_MONSTER_POWER;
                speed_run = MEDIUM_MONSTER_SPEED;
                hp = MEDIUM_MONSTER_HP;
                break;
            case BIG:
                lookRange = new Circle(x, y, BIG_MONSTER_LOOKRANGE);
                power = BIG_MONSTER_POWER;
                speed_run = BIG_MONSTER_SPEED;
                hp = BIG_MONSTER_HP;
                painter().scale(BIG_MONSTER_WIDTH, BIG_MONSTER_HEIGHT);
                collider().scale(BIG_MONSTER_WIDTH, BIG_MONSTER_HEIGHT);
                break;
        }
        state = State.WALK;
        delay = new Delay(UPDATE_TIMES_PER_SEC * WALK_DELAY_TIME);
        count = 0;
        dir = Direction.LEFT;
        animator = new MonsterAnimator(this.type.path, type, state, dir);
    }

    /**
     * 取得怪物類型
     *
     * @return 怪物類型
     */
    public Type type() {
        return type;
    }

    /**
     * 取得怪物攻擊力
     *
     * @return 怪物攻擊力
     */
    public int power() {
        return power;
    }

    /**
     * 取得怪物狀態
     *
     * @return 怪物狀態
     */
    public State state() {
        return state;
    }

    @Override
    public void paintComponent(Graphics g) {
        //畫出可視範圍
        if (IS_DEBUG) {
            lookRange.paintComponent(g);
        }
        g.setColor(Color.RED);
        for (int i = 0; i < hp; i++) {
            g.fillRect(painter().centerX() - HUNTER_HP_WIDTH*(hp/2) +(i*HUNTER_HP_WIDTH),
                    painter().top()-20,
                    HUNTER_HP_WIDTH,HUNTER_HP_HEIGHT);
        }
        //前四個 -> 預計呈現在畫布上的位置(dx1,dy1)到(dx2,dy2)
        //後四個 -> 資料來源的定位點位置從(sx1,sy1)到(sx2,sy2)
        if(newBorn) {
            g.drawImage(newBornEffect, painter().left()-80, painter().bottom()-80,painter().right()+80, painter().bottom()+30,
                    125*newBornAnimatearr[newBorncount],0,
                    125+125*newBornAnimatearr[newBorncount],125,
                    null);
        }
        animator.paintComponent(painter().left(), painter().top(), painter().right(), painter().bottom(), g);

    }

    /**
     * 沒偵測到玩家時自己左右移動
     */
    @Override
    public void update() {
        if(newBornDelay.count()){
            this.newBorn = false;
        }
        if(newBornAnimateDelay.count()){
            newBorncount = ++newBorncount % newBornAnimatearr.length;
        }
        if (state != State.ELIMINATED) {
            move();
        }
        lookRange.update(this);
        animator.update();

    }

    /**
     * 偵測到玩家則朝玩家方向移動
     *
     * @param player 偵測到的玩家
     */
    public void update(Player player) {
        if(newBornDelay.count()){
            this.newBorn = false;
        }
        if (state != State.ELIMINATED) {
            move(player);
        }
        lookRange.update(this);
        animator.update();
//        if(trackingDelay.count()) {
//            this.state = State.WALK;
//        }
    }

    /**
     * 怪物移動
     */
    /**
     * 沒偵測到玩家 -> 原地左右移動
     */
    private void move() {
        //若原為跑步狀態 -> 更改為走路狀態
        if (state == State.RUN) {
            changeState(State.WALK);
            animator.setMonsterImage(type, state, dir);
        }

        //啟動計數器
        //第0-1秒往左移動
        //第1-3秒往右移動
        //第3-4秒往左移動(回歸原點)
        delay.loop();
        if (delay.count()) {
            count++;
        }
        if ((count % 4 == 1 || count % 4 == 2) && !isLockRight()) {
            translateX(speed_walk);
            changeDir(Direction.RIGHT);
        } else if ((count % 4 == 0 || count % 4 == 3) && !isLockLeft()) {
            translateX(-speed_walk);
            changeDir(Direction.LEFT);
        }
    }

    /**
     * 偵測到玩家 -> 朝玩家方向移動
     *
     * @param player 偵測到的玩家
     */
    private void move(Player player) {
        //若原為走路狀態 -> 更改為跑步狀態
        if (state == State.WALK) {
            changeState(State.RUN);
            animator.setMonsterImage(type, state, dir);
        }


        if(trackingDelay.count()){
            changeState(State.WALK);
            animator.setMonsterImage(type, state, dir);
        }

        //關閉計數器
        delay.stop();
        count = 0;

        double dX = (player.painter().centerX() - painter().centerX());
        double dY = (player.painter().centerY() - painter().centerY());
        double angle = Math.atan2(dY, dX);

        double moveX = (Math.cos(angle) * speed_run);
        double moveY = (Math.sin(angle) * speed_run);

        //新版
        if(moveX>0) {
            if(!isLockRight()) {
                translateX((int)moveX);
            }
        } else {
            if(!isLockLeft()) {
                translateX((int)moveX);
            }
        }

        if(moveY>0) {
            if(!isLockBottom()) {
                translateY((int)moveY);
            }
        } else {
            if(!isLockTop()) {
                translateY((int)moveY);
            }
        }

        //舊版
//        if (!isLockLeft() && !isLockRight() && !isLockTop() && !isLockBottom()) {
//            translate((int) moveX, (int) moveY);
//        } else if ((isLockLeft() || isLockRight()) && (!isLockTop() || !isLockBottom())) {
//            translateY((int) moveY);
//        } else if ((!isLockLeft() || !isLockRight()) && (isLockTop() || isLockBottom())) {
//            translateX((int) moveX);
//        }

        //更改方向
        if (moveX > 0) {
            changeDir(Direction.RIGHT);
        }
        if (moveX < 0) {
            changeDir(Direction.LEFT);
        }
    }

    /**
     * 偵測玩家是否進到怪物可視範圍中
     *
     * @param player 玩家
     * @return 是否進到怪物可視範圍中
     */
    public boolean isDetectPlayer(Player player) {
        return lookRange.isOverlap(player);
    }

    /**
     * 更改怪物方向
     */
    private void changeDir(Direction dir) {
        //更改方向
        if (this.dir != dir) {
            this.dir = dir;
            //更改動畫
            animator.setMonsterImage(type, state, dir);
        }
    }

    /**
     * 碰撞後改變狀態
     */
    public void changeState(State state) {
        this.state = state;
        if (state == State.ELIMINATED) {
            delay.stop();
            delay.setCountLimit(UPDATE_TIMES_PER_SEC);
            delay.play();
            animator.setMonsterImage(type, state, dir);
        }
    }

    /**
     * 改變狀態後需要被刪掉
     */
    public boolean shouldEliminate() {
        if (state == State.ELIMINATED) {
            return delay.count();
        }
        return false;
    }

    /**
     * 怪物被攻擊
     *
     * @param inputPower 角色攻擊力
     */
    public void beAttacked(int inputPower) {
        hp -= inputPower;
        //若血沒了則改變狀態
        if (hp <= 0) {
            changeState(State.ELIMINATED);
        }
    }

    public int getSpeed() {
        switch (state) {
            default:
            case WALK:
                return speed_walk;
            case RUN:
                return speed_run;
        }
    }

    public void turnOnTrackingMode(){
        this.state = State.TRACKING;
        animator.setMonsterImage(type,State.RUN,dir);
        trackingDelay.play();
    }
}
