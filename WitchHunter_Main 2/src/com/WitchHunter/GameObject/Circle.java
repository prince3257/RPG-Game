package com.WitchHunter.GameObject;

import java.awt.*;

public class Circle {

    /** 物件會跟範圍共用這些基本參數*/
    private float x;
    private float y;
    private float r;

    public Circle(float actorX, float actorY, int r) {
        this.x = actorX;
        this.y = actorY;
        this.r = r;
    }

    public Circle(GameObject actor, int r) {
        this.x = (actor.painter().left() + actor.painter().width()/2) - r/2;
        this.y = (actor.painter().top() + actor.painter().height()/2) - r/2;
        this.r = r;
    }

    public void scaleUp(){
        if(this.r >= 1500) {
            return;
        }
        this.r += 20;
    }

    public void scaleDown(){
        if(this.r <= 500) {
            return;
        }
        this.r -= 20;
    }

    public final Circle translate(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    public final Circle translateX(float x) {
        this.x += x;
        return this;
    }
    public final Circle translateY(float y) {
        this.y += y;
        return this;
    }


    public void setNewXY(int x, int y) {
        this.x = x+r/2;
        this.y = y+r/2;
    }


    /** 檢測兩個範圍是否重疊 */
    public boolean isOverlap(GameObject object) {

        float objectCenterX = object.painter().left() + object.painter().width()/2;
        float objectCenterY = object.painter().top() + object.painter().height()/2;

        float objectLongDis = (float) Math.sqrt((object.painter().width()/2)*(object.painter().width()/2)+(object.painter().height()/2)*(object.painter().height()/2));

        float circleCenterX = x + r/2;
        float circleCenterY = y + r/2;

        float disX = Math.abs(objectCenterX-circleCenterX);
        float disY = Math.abs(objectCenterY-circleCenterY);

        return Math.sqrt((disX*disX) + (disY*disY))<(r/2+objectLongDis);
    }


    public void paintComponent(Graphics g) {
        g.setColor(Color.RED);
        g.drawOval((int) x,(int)y,(int)r,(int)r);
    }


    public void update(GameObject actor) {
        this.x = (actor.painter().left() + actor.painter().width()/2) - r/2;
        this.y = (actor.painter().top() + actor.painter().height()/2) - r/2;
    }
}
