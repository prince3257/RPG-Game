package com.WitchHunter.GameObject.WitchHunterObject.Other;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.Rect;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.GameKernel;
import com.WitchHunter.utils.Path;
import java.awt.*;

import static com.WitchHunter.utils.Global.*;

public class BossAttack implements GameKernel.GameInterface {

    public enum State {
        NORMAL,
        ELIMINATED
    }

    public enum Type {
        FIREBALL(new Path().img().objects().fireBall(),BOSS_POWER),
        POISONFOG(new Path().img().objects().poisonFog(),BOSS_POWER);

        private final String path;
        private final int power;

        Type(String path, int power) {
            this.path = path;
            this.power = power;
        }
    }

    private final Image attackImage; //子彈動畫圖片
    private double x; //子彈射出位置X座標
    private double y; //子彈射出位置Y座標

    private final double targetCenterX; //目標X中心座標
    private final double targetCenterY; //目標Y中心座標
    private final double shootingPointCenterX; //射出X中心座標
    private final double shootingPointCenterY; //射出Y中心座標

    private final Rect collider; //毒霧及火球的碰撞區

    private State state; //毒霧及火球的狀態

    private final double speed; //毒霧及火球的速度
    private final int power; //毒霧及火球的攻擊力
    private final Delay delay; // 毒霧及火球的倒數清除器

    public BossAttack(int targetCenterX, int targetCenterXY, int shootingFromX, int shootingFromY, int bulletSpeed,Type type){
        this.x = shootingFromX;
        this.y = shootingFromY;
        this.targetCenterX = targetCenterX;
        this.targetCenterY = targetCenterXY;
        this.shootingPointCenterX = shootingFromX;
        this.shootingPointCenterY = shootingFromY;
        this.collider = new Rect((int)x,(int)y,UNIT_X,UNIT_Y);
        this.state = State.NORMAL;
        this.speed = bulletSpeed;
        this.attackImage = SceneController.instance().irc().tryGetImage(type.path);
        this.power = type.power;
        this.delay = new Delay(UPDATE_TIMES_PER_SEC*10);
        delay.play();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(attackImage,(int) x,(int)y, UNIT_X, UNIT_Y, null);

    }

    @Override
    public void update() {
        if(state == State.NORMAL) {
            move();
        }
        if(delay.count()) {
            state = State.ELIMINATED;
        }
    }

    /** 運算子彈射出軌跡 */
    public void move() {
        //算出射出的XY變量
        double dX = (targetCenterX - shootingPointCenterX);
        double dY = (targetCenterY - shootingPointCenterY);
        double angle = Math.atan2(dY,dX);

        double moveX = (Math.cos(angle)*speed);
        double moveY = (Math.sin(angle)*speed);

        //移動本身及碰撞區(好像不用改到本身的XY);
        translate(moveX,moveY);
    }

    /** 子彈射出 */
    private void translate(double inputX, double inputY) {
        this.x += (int)inputX;
        this.y += (int)inputY;
        this.collider.translate((int)inputX,(int)inputY);
    }

    /** 呼叫碰撞區 */
    public Rect collider() {
        return this.collider;
    }

    /** 碰撞後改變狀態 */
    public void changeState() {
        this.state = State.ELIMINATED;
    }

    /** 改變狀態後需要被刪掉 */
    public boolean shouldEliminate() {
        return state == State.ELIMINATED;
    }

    /** 取得相關屬性 */
    public State getState() {
        return state;
    }

    public int getPower() {
        return power;
    }

}
