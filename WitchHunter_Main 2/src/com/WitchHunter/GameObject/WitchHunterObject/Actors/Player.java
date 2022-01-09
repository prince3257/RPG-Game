package com.WitchHunter.GameObject.WitchHunterObject.Actors;

import com.WitchHunter.Animator.Animator;
import com.WitchHunter.Animator.Animator.*;
import com.WitchHunter.Animator.PropsAnimator;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.GameEngine.GameKernel;
import com.WitchHunter.utils.Global;
import com.WitchHunter.GameObject.GameObject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Locale;

import static com.WitchHunter.utils.Global.*;

public class Player extends GameObject implements GameKernel.GameInterface, CommandSolver.KeyCommandListener {

    /** 動畫器相關 */
    private final Animator animator; //動畫器
    private State state; //動畫狀態
    private boolean actorHaunted; //玩家是否驚嚇狀態
    private final Delay hauntedDelay; //玩家驚嚇計時
    private final Delay flareDelay; //信號彈計時

    /** 玩家性質相關 */
    private final String playerName; //玩家名稱
    private int playerHP; //玩家血量
    private int playerSpeed; //玩家速度
    private Direction dir; //朝向方位

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    private boolean useItem; //使用道具
    private final PropsAnimator propsAnimator; //道具特效
    private final Delay propsEffectDelay; //道具特效顯示時間


    public Player(int x, int y, int type,String playerInputName){
        super(x,y,CHACRACTER_UNIT_X,CHACRACTER_UNIT_Y);
        this.state = Animator.State.STAND;
        this.animator = new Animator(type,state);
        this.playerName = playerInputName;
        this.playerHP = HUNTER_HP;
        this.playerSpeed = HUNTER_SPEED;
        this.dir = Global.Direction.DOWN;
        this.actorHaunted = false;
        this.hauntedDelay = new Delay(UPDATE_TIMES_PER_SEC*8);
        this.flareDelay = new Delay(UPDATE_TIMES_PER_SEC*2);
        this.useItem = false;
        this.propsAnimator = new PropsAnimator();
        this.propsEffectDelay = new Delay(UPDATE_TIMES_PER_SEC*2);
    }

    @Override
    public void paintComponent(Graphics g) {
        //顯示血條
        g.setColor(Color.RED);
        for (int i = 0; i < playerHP; i++) {
            g.fillRect(painter().centerX() - HUNTER_HP_WIDTH*(playerHP/2) +(i*HUNTER_HP_WIDTH),
                    painter().top()-20,
                    HUNTER_HP_WIDTH,HUNTER_HP_HEIGHT);
        }

        //顯示名稱
        g.setColor(Color.BLUE);
        int nameLength = g.getFontMetrics().stringWidth(playerName);
        g.drawString(playerName.toUpperCase(Locale.ROOT),painter().centerX()-(nameLength/2),painter().bottom()+20);

        //玩家動畫器
        animator.paintComponent(dir,painter().left(),painter().top(), painter().right(),painter().bottom(),g);

        //使用道具後在身上的動畫器
        if(useItem){
            propsAnimator.paintComponent(painter().left(), painter().top(), painter().right(), painter().bottom(), g);
        }
    }

    @Override
    public void update() {
        //玩家死亡改變動畫狀態
        if(this.playerHP <= 0) {
            changeState(Animator.State.DEAD);
            animator.setActorHaunted(false);
        }

        //驚嚇狀態倒數後解除
        if(hauntedDelay.count()) {
            recoverSpeed();
        }

        //更新玩家動畫
        animator.update();

        //道具特效展示後結束
        if(propsEffectDelay.count()) {
            useItem = false;
//            propsEffectDelay.stop();
        }

        //如果使用道具 -> 更新道具特效動畫器
        if(useItem){
            propsAnimator.update();
        }
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        if(commandCode == KeyEvent.VK_W ||commandCode == KeyEvent.VK_A ||
                commandCode == KeyEvent.VK_S ||commandCode == KeyEvent.VK_D) {
            if(playerHP>=0){
                changeState(Animator.State.WALK);
                if(!actorHaunted){
                    switch (commandCode) {
                        case KeyEvent.VK_D:
                            this.dir = Direction.RIGHT;
                            break;
                        case  KeyEvent.VK_A:
                            this.dir = Direction.LEFT;
                            break;
                        case KeyEvent.VK_W:
                            this.dir = Direction.UP;
                            break;
                        case KeyEvent.VK_S:
                            this.dir = Direction.DOWN;
                            break;
                    }
                } else {
                    switch (commandCode) {
                        case KeyEvent.VK_D:
                            this.dir = Direction.LEFT;
                            break;
                        case  KeyEvent.VK_A:
                            this.dir = Direction.RIGHT;
                            break;
                        case KeyEvent.VK_W:
                            this.dir = Direction.DOWN;
                            break;
                        case KeyEvent.VK_S:
                            this.dir = Direction.UP;
                            break;
                    }
                }

                move();
            }
        }

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        //放開任何鍵就不動
        changeState(Animator.State.STAND);
    }

    /**玩家狀態＆動畫相關 */

    //改變玩家動畫
    public void changeState(Animator.State newState){
        this.state = newState;
        this.animator.setState(newState);
    }

    //玩家受到驚嚇
    public void getHaunted(){
        this.actorHaunted = true;
        this.hauntedDelay.setCount(0);
        this.hauntedDelay.play();
        this.animator.setActorHaunted(true);
    }

    //玩家被攻擊
    public void beAttacked(int inputPower){
        this.playerHP -= inputPower;
        if(playerHP<=0) {
            this.state = Animator.State.DEAD;
        }
    }

    //改變玩家朝向
    public void facingDirection(int mouseX, int mouseY) {
        int dX = mouseX - painter().centerX();
        int dY = mouseY - painter().centerY();
        double position = Math.atan2(dX,dY);
        if(position >= (Math.PI *3)/4 || position <= -(Math.PI*3)/4) {
            this.dir = Direction.UP;
        }
        if(position < (Math.PI *3)/4 && position >= Math.PI/4) {
            this.dir = Direction.RIGHT;
        }
        if((position < Math.PI/4 && position > 0) || (position > -Math.PI/4 && position < 0)) {
            this.dir = Direction.DOWN;
        }
        if(position < -Math.PI/4 && position > -(Math.PI*3)/4) {
            this.dir = Direction.LEFT;
        }
    }

    //讓玩家移動
    public void move() {

        switch (this.state) {
            case STAND:
                this.playerSpeed = 0;
                break;
            case WALK:
                if(actorHaunted) {
                    this.playerSpeed = HUNTER_SPEED_WEAK;
                } else {
                    this.playerSpeed = HUNTER_SPEED;
                }
                break;
        }

        switch (this.dir) {
            case RIGHT:
                if(painter().right()+playerSpeed<=BACKGROUND_WIDTH && !isLockRight()) {
                    translateX(playerSpeed);
                }
                break;
            case LEFT:
                if(painter().left()-playerSpeed>=0 && !isLockLeft()) {
                    translateX(-playerSpeed);
                }
                break;
            case UP:
                if(painter().top()-playerSpeed>=0 && !isLockTop()) {
                    translateY(-playerSpeed);
                }
                break;
            case DOWN:
                if(painter().bottom()+playerSpeed<=BACKGROUND_HEIGHT && !isLockBottom()) {
                    translateY(playerSpeed);
                }
                break;
        }

    }

    /** 道具特效相關 */

    //使用道具後，更新道具特效動畫器
    public void setUseItem() {
        this.useItem = true;
        propsEffectDelay.play();
    }

    //玩家回血
    public void recoverHP() {
        this.playerHP += HUNTER_RECOVERY_HP;
        if(playerHP>HUNTER_HP){
            playerHP = 50;
        }
    }

    //玩家回復速度
    public void recoverSpeed() {
        this.actorHaunted = false;
        this.animator.setActorHaunted(false);
        this.playerSpeed = HUNTER_SPEED;
        this.hauntedDelay.stop();
    }

    /** 取得相關屬性 */

    //取得道具特效動畫器
    public PropsAnimator getPropsAnimator() {
        return propsAnimator;
    }

    //取得玩家速度
    public int speed() {
        return playerSpeed;
    }

    //取得玩家HP
    public int getPlayerHP() {
        return playerHP;
    }

    public boolean isActorHaunted() {
        return actorHaunted;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }

    public void setPlayerType(int typeNum) {
        this.animator.setType(typeNum);
    }
}
