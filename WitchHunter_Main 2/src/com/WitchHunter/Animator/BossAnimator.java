package com.WitchHunter.Animator;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.WitchHunterObject.Monsters.Boss;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Path;

import java.awt.*;

import static com.WitchHunter.utils.Global.UPDATE_TIMES_PER_SEC;

public class BossAnimator {

    public enum State {
        STAND,
        FIREBALL,
        FOG,
        SUMMON_GHOST,
        DEAD
    }

    private final Image bossStand;
    private final Image bossFog;
    private final Image bossSummon;
    private final Image bossDead;
    private final Boss.Type type;
    private final Delay delay;
    public int count;
    public State state;
    public int[] arr;

    public BossAnimator(Boss.Type type, State state){
        if(type == Boss.Type.ALTAR) {
            bossStand = SceneController.instance().irc().tryGetImage(new Path().img().boss().altarstand());
            bossFog = SceneController.instance().irc().tryGetImage(new Path().img().boss().altarfog());
            bossSummon = SceneController.instance().irc().tryGetImage(new Path().img().boss().altarsummon());
            bossDead = SceneController.instance().irc().tryGetImage(new Path().img().boss().altardie());
        } else {
            bossStand = SceneController.instance().irc().tryGetImage(new Path().img().boss().piratestand());
            bossFog = SceneController.instance().irc().tryGetImage(new Path().img().boss().piratefog());
            bossSummon = SceneController.instance().irc().tryGetImage(new Path().img().boss().piratesummon());
            bossDead = SceneController.instance().irc().tryGetImage(new Path().img().boss().piratedie());
        }
        this.type = type;
        this.delay = new Delay(0);
        this.delay.loop();
        this.count = 0;
        setState(state);

    }


    public State getState(){
        return this.state;
    }

    public void setArr(int imgNum) {
        this.arr = new int[imgNum];
        for (int i = 0; i < imgNum; i++) {
            arr[i] = i;
        }
    }

    public final void setState(State state) {

        this.state = state;
        if(state == State.FOG || state == State.FIREBALL) {
            switch (type) {
                case ALTAR:
                    setDelay(UPDATE_TIMES_PER_SEC/24);
                    setArr(24);
                    break;
                case PIRATE:
                    setDelay(UPDATE_TIMES_PER_SEC/13);
                    setArr(13);
                    break;
            }
        }
        else if(state == State.SUMMON_GHOST) {
            switch (type) {
                case ALTAR:
                    setDelay(UPDATE_TIMES_PER_SEC/38);
                    setArr(38);
                    break;
                case PIRATE:
                    setDelay(UPDATE_TIMES_PER_SEC/16);
                    setArr(16);
                    break;
            }
        }
        else if( state == State.DEAD){
            switch (type) {
                case ALTAR:
                    setDelay(UPDATE_TIMES_PER_SEC/12);
                    setArr(12);
                    break;
                case PIRATE:
                    setDelay(UPDATE_TIMES_PER_SEC/8);
                    setArr(8);
                    break;
            }
        } else {
            switch (type) {
                case ALTAR:
                    setDelay(UPDATE_TIMES_PER_SEC/12);
                    setArr(12);
                    break;
                case PIRATE:
                    setDelay(UPDATE_TIMES_PER_SEC/8);
                    setArr(8);
                    break;
            }
        }
    }

    public void paintComponent(int left, int top, int right, int bottom, Graphics g) {
        int x = 0;
        int y = 0;
        switch (type) {
            case ALTAR:
                x = 600;
                y = 600;
                break;
            case PIRATE:
                x = 240;
                y = 300;
                break;
        }
        if(state == State.STAND) {
            g.drawImage(bossStand,left,top,right,bottom,x*arr[count],0,x+x*arr[count],y,null);
        }
        if(state == State.FOG || state == State.FIREBALL) {
            g.drawImage(bossFog,left,top,right,bottom,x*arr[count],0,x+x*arr[count],y,null);
        }
        if(state == State.SUMMON_GHOST) {
            g.drawImage(bossSummon,left,top,right,bottom,x*arr[count],0,x+x*arr[count],y,null);
        }
        if(state == State.DEAD) {
            g.drawImage(bossDead,left,top,right,bottom,x*arr[count],0,x+x*arr[count],y,null);
        }
    }

    public void setDelay(int delayTime) {
        this.delay.stop();
        this.delay.setCountLimit(delayTime);
        this.delay.loop();
        this.count = 0;
    }

    public void update() {
        if(delay.count()) {
            count = ++count % arr.length;
        }
    }
}
