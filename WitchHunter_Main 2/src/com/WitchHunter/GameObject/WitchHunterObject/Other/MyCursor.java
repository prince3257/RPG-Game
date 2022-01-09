package com.WitchHunter.GameObject.WitchHunterObject.Other;

import com.WitchHunter.GameObject.Circle;
import static com.WitchHunter.utils.Global.*;


public class MyCursor {

    private final Circle lookrange; //滑鼠移動可視範圍
    private int x; //滑鼠座標X
    private int y; //滑鼠座標Y

    public MyCursor(int x, int y) {
        this.lookrange = new Circle(x,y,(int) HUNTER_LOOKRANGE_MIN);
        this.x = x;
        this.y = y;
    }

    //設定新座標點
    public void setNewLocation(int x, int y) {
        this.x = x-(int) HUNTER_LOOKRANGE_MIN;
        this.y = y-(int) HUNTER_LOOKRANGE_MIN;
        lookrange.setNewXY(this.x,this.y);
    }

    public Circle getLookrange() {
        return lookrange;
    }

    public void translate(int x) {
        this.x += x;
        lookrange.translate(x*15,0);
    }
}
