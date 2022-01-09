package com.WitchHunter.GameObject.WitchHunterObject.Other;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.Rect;
import com.WitchHunter.Animator.BulletAnimator;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.GameKernel;
import com.WitchHunter.utils.Path;

import java.awt.*;

import static com.WitchHunter.utils.Global.*;
import static com.WitchHunter.utils.Global.UPDATE_TIMES_PER_SEC;

public class Bullet implements GameKernel.GameInterface {

    public enum State {
        NORMAL,
        EXPLODE,
        ELIMINATED
    }

    public enum Type {
        DEFAULT(new Path().img().objects().bulletDefault()),
        UPGRADE(new Path().img().objects().bulletUpgrade()),
        SUPER(new Path().img().objects().bulletSuper());

        private String path;

        Type(String inputPath){
            this.path = inputPath;
        }
    }

    private double x; //子彈射出位置X座標
    private double y; //子彈射出位置Y座標

    private final double targetCenterX; //目標X中心座標
    private final double targetCenterY; //目標Y中心座標
    private final double shootingPointCenterX; //射出X中心座標
    private final double shootingPointCenterY; //射出Y中心座標

    private final Rect bulletCollider; //子彈的碰撞區

    private State bulletState; //子彈的狀態
    private Type bulletType; //子彈的種類

    private final double speed; //子彈速度

    private final Image bulletImage; //初級子彈

    private Delay delay; //碰撞後爆炸

    private BulletAnimator animator; //子彈動畫

    public Bullet(int targetCenterX, int targetCenterXY, int shootingFromX, int shootingFromY, int bulletSpeed, int power){
        this.x = shootingFromX;
        this.y = shootingFromY;
        this.targetCenterX = targetCenterX;
        this.targetCenterY = targetCenterXY;
        this.shootingPointCenterX = shootingFromX;
        this.shootingPointCenterY = shootingFromY;
        this.bulletCollider = new Rect((int)x,(int)y,BULLET_WIDTH,BULLET_HEIGHT);
        this.bulletState = State.NORMAL;
        this.speed = bulletSpeed;
        this.bulletImage = SceneController.instance().irc().tryGetImage(new Path().img().objects().bulletDefault());
        this.setType(power);
        this.delay = new Delay(UPDATE_TIMES_PER_SEC);
        this.animator = new BulletAnimator();
    }

    //設定子彈種類
    private void setType(int inputPower) {
        if(inputPower >= 15) {
            this.bulletType = Type.SUPER;
        } else
        if(inputPower >= 10){
            this.bulletType = Type.UPGRADE;
        }
        else {
            this.bulletType = Type.DEFAULT;
        }

    }

    @Override
    public void paint(Graphics g) {
       if(bulletState == State.EXPLODE) {
            animator.expolde();
            animator.paintComponent(bulletCollider.left(),bulletCollider.top(),bulletCollider.right(),bulletCollider.bottom(),g);
        } else {
           g.drawImage(SceneController.instance().irc().tryGetImage(bulletType.path),(int)x, (int)y, 15,15,null);
       }
    }

    @Override
    public void update() {
        if(bulletState == State.EXPLODE) {
            animator.update();
        }
        if(delay.count()){
            this.bulletState = State.ELIMINATED;
        }
        if(bulletState == State.NORMAL){
            move();
        }
    }

    /** 運算子彈射出軌跡 */
    private void move() {

        double dX = (targetCenterX - shootingPointCenterX);
        double dY = (targetCenterY - shootingPointCenterY);
        double angle = Math.atan2(dY,dX);

        double moveX = (Math.cos(angle)*speed);
        double moveY = (Math.sin(angle)*speed);

        translate(moveX,moveY);
    }

    /** 子彈射出 */
    public void translate(double inputX, double inputY) {
        this.x += (int)inputX;
        this.y += (int)inputY;
        this.bulletCollider.translate((int)inputX,(int)inputY);
    }

    public void setNewX(double x){
        this.x = x;
    }

    public void setNewY(double y){
        this.y = y;
    }

    /** 呼叫碰撞區 */
    public Rect BulletCollider() {
        return this.bulletCollider;
    }

    /** 碰撞後改變狀態 */
    public void changeBulletState() {
        this.bulletState = State.EXPLODE;
        delay.play();
    }

    /** 改變狀態後需要被刪掉 */
    public boolean shouldEliminate() {
        return bulletState == State.ELIMINATED;
    }

    /** 取得屬性 */
    public State getBulletState() {
        return bulletState;
    }
}
