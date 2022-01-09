package com.WitchHunter.GameObject;

import java.awt.*;

/** 針對碰撞等遊戲機制改寫 -> 可以想成是遊戲物件的範圍 -> 跟遊戲物件分開 -> 輔助使用！*/
public class Rect {

    /** 物件會跟範圍共用這些基本參數*/
    private int x;
    private int y;
    private int width;
    private int height;

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public final Rect translate(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }
    public final Rect translateX(int x) {
        this.x += x;
        return this;
    }
    public final Rect translateY(int y) {
        this.y += y;
        return this;
    }

    public final int centerX(){
        return this.x + this.width/2;
    }
    public final int centerY(){
        return this.y + this.height/2;
    }
    public final void setCenter(int x, int y) {
        //畫圖出來看會更清楚 -> 傳入大圖的中心點 -> 左上角應該是小圖的對齊位置 -> 剪掉小圖的寬高一半 -> 小圖的新對齊位置 -> 兩圖中心點一樣
        this.x = x - this.width/2;
        this.y = y - this.height/2;
    }

    public final Rect scale(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public final Rect scaleWidth(int width) {
        this.width = width;
        return this;
    }
    public final Rect scaleHeight(int height) {
        this.height = height;
        return this;
    }

    public final Rect setNewXY(int x, int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }
    public final Rect setNewX(int x){
        this.x = x;
        return this;
    }
    public final Rect setNewY(int y){
        this.y = y;
        return this;
    }

    public final int width() {
        return width;
    }
    public final int height() {
        return height;
    }
    public final int left() {
        return x;
    }
    public final int right() {
        return x + width;
    }
    public final int top() {
        return y;
    }
    public final int bottom() {
        return y + height;
    }

    public boolean overlap(Rect object) {
        //檢測兩個範圍是否重疊
        if(this.right() < object.left()) {
            return false;
        }
        if(this.left() > object.right()) {
            return false;
        }
        if(this.top() > object.bottom()) {
            return false;
        }
        if(this.bottom() < object.top()) {
            return false;
        }
        return true;
    }

    public final Rect clone() {
        return new Rect(left(), right(), width(), height());
    }

    public final void paint(Graphics g) {
        //畫出長方形
        g.drawRect(x,y,width,height);
    }
}
