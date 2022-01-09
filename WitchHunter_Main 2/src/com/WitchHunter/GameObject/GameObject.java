package com.WitchHunter.GameObject;

import com.WitchHunter.utils.GameEngine.GameKernel;
import com.WitchHunter.utils.Global;

import java.awt.*;

public abstract class GameObject implements GameKernel.GameInterface {

    private Rect collider;// 遊戲物件外部範圍(可能是碰撞範圍等)
    private Rect painter;// 遊戲物件本身的範圍
    private String ID;

    /** 判斷是否撞牆需求 */
    private boolean lockLeft;
    private boolean lockRight;
    private boolean lockTop;
    private boolean lockBottom;

    public GameObject(int x, int y, int width, int height) {
        this.collider = new Rect(x,y,width,height);
        this.painter = new Rect(x,y,width,height);
        this.lockLeft = false;
        this.lockRight = false;
        this.lockTop = false;
        this.lockBottom = false;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public GameObject(int x, int y, int width, int height,
                      int x2, int y2, int width2, int height2) {
        this.collider = new Rect(x,y,width,height);
        this.painter = new Rect(x2,y2,width2,height2);
        painter.setCenter(collider.centerX(),collider.centerY());
        this.lockLeft = false;
        this.lockRight = false;
        this.lockTop = false;
        this.lockBottom = false;
    }

    public GameObject(Rect rect) {
        collider = rect.clone();
        painter = rect.clone();
        this.lockLeft = false;
        this.lockRight = false;
        this.lockTop = false;
        this.lockBottom = false;
    }

    public GameObject(Rect rect1,Rect rect2) {
        collider = rect1.clone();
        painter = rect2.clone();
        painter.setCenter(rect1.centerX(),rect1.centerY());
        this.lockLeft = false;
        this.lockRight = false;
        this.lockTop = false;
        this.lockBottom = false;
    }

    public final void translate(int x, int y) {
        this.collider.translate(x,y);
        this.painter.translate(x,y);
    }

    public final void translateX(int x) {
        this.collider.translateX(x);
        this.painter.translateX(x);
    }
    public final void translateY(int y) {
        this.collider.translateY(y);
        this.painter.translateY(y);
    }

    public final void setNewXY(int x, int y,int width, int height) {
        this.painter.setNewXY(x,y,width,height);
        this.collider.setNewXY(x,y,width,height);
    }
    public final void setNewX(int x) {
        this.painter.setNewX(x);
        this.collider.setNewX(x);
    }
    public final void setNewY(int y) {
        this.painter.setNewY(y);
        this.collider.setNewY(y);
    }

    public boolean touchTop() {
        return collider.top() <= 0;
    }

    public boolean touchLeft() {
        return collider.left() <= 0;
    }

    public boolean touchRight() {
        return collider.right() >= Global.SCREEN_X;
    }

    public boolean touchBottom() {
        return collider.bottom() >= Global.SCREEN_Y;
    }


    public boolean isCollision(GameObject object) {
        return collider.overlap(object.collider);
    }

    public final Rect collider() {
        return this.collider;
    }

    public final Rect painter() {
        return this.painter;
    }

    @Override
    public final void paint(Graphics g) {
        //先畫出遊戲物件本身
        paintComponent(g);
        if(Global.IS_DEBUG) {
            g.setColor(Color.RED);
            //遊戲物件外部的範圍
            collider.paint(g);
            g.setColor(Color.GREEN);
            //遊戲物件本身的範圍
            painter.paint(g);
            g.setColor(Color.BLACK);
        }
    }

    public boolean isLockLeft() {
        return lockLeft;
    }

    public void setLockLeft(boolean lockLeft) {
        this.lockLeft = lockLeft;
    }

    public boolean isLockRight() {
        return lockRight;
    }

    public void setLockRight(boolean lockRight) {
        this.lockRight = lockRight;
    }

    public boolean isLockTop() {
        return lockTop;
    }

    public void setLockTop(boolean lockTop) {
        this.lockTop = lockTop;
    }

    public boolean isLockBottom() {
        return lockBottom;
    }

    public void setLockBottom(boolean lockBottom) {
        this.lockBottom = lockBottom;
    }

    public abstract void paintComponent(Graphics g);
}
