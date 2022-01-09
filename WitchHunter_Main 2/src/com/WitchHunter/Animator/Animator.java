package com.WitchHunter.Animator;

import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Global;
import com.WitchHunter.utils.Path;
import com.WitchHunter.Controller.SceneController;
import java.awt.*;

import static com.WitchHunter.utils.Global.*;

public class Animator {

    public enum State {
        WALK(new int[] {0,1,2,1},HUNTER_SPEED),
        STAND(new int[] {1},0),
        DEAD(new int[] {0,1,2},HUNTER_SPEED_WEAK);

        private final int[] arr;
        private final int speed;

        State(int[] arr, int speed) {
            this.arr = arr;
            this.speed = speed;
        }
    }

    private final Image actor;
    private final Image actorDead;
    private final Image actorHaunted;
    private final Delay delay;
    private final Delay delayDeadAnimiation;
    private int deadUpdateNum;
    private int count;
    private State state;
    private boolean actorGetHaunted;
    private int type;

    public Animator(int type, State state){
        this.actor = SceneController.instance().irc().tryGetImage(new Path().img().actors().actornormalwalk());
        this.actorDead = SceneController.instance().irc().tryGetImage(new Path().img().actors().actorgetweak());
        this.actorHaunted = SceneController.instance().irc().tryGetImage(new Path().img().actors().actorhaunted());
        this.delay = new Delay(0);
        this.delay.loop();
        this.delayDeadAnimiation = new Delay(UPDATE_TIMES_PER_SEC/3);
        this.deadUpdateNum = 0;
        this.count = 0;
        this.type = type;
        setState(state);
        this.actorGetHaunted = false;
    }

    public void setType(int typeNum) {
        this.type = typeNum;
    }

    public final void setState(State state) {
        if(state == State.DEAD){
            count = 0;
        }
        this.state = state;
        this.delay.setCountLimit(state.speed);
    }

    public void setActorHaunted(boolean haunted){
        this.actorGetHaunted = haunted;
    }

    public void paintComponent(Global.Direction dir, int left, int top, int right, int bottom, Graphics g) {
        if(state == State.DEAD) {
            delayDeadAnimiation.loop();
            g.drawImage(actorDead, left-200, top-100, right+200,bottom+100,
                    (UNIT_X * 3) * 2 + UNIT_X * state.arr[count],
                    (UNIT_Y * 2) + (deadUpdateNum % 4) * UNIT_Y,
                    (UNIT_X * 3) * 2 + UNIT_X + UNIT_X * state.arr[count],
                    (UNIT_Y * 2) + UNIT_Y + (deadUpdateNum % 4) * UNIT_Y,
                    null);
        } else {
            if(actorGetHaunted) {
                g.drawImage(actorHaunted, left, top-UNIT_Y, right,top,
                        33 * state.arr[count],
                        53 * dir.getValue(),
                        33 + 33 * state.arr[count],
                        53 + 53 * dir.getValue(),
                        null);
            }
            g.drawImage(actor, left, top, right,bottom,
                    (type % 4) * Global.CUTRANGE * 3 + Global.CUTRANGE * state.arr[count],
                    (type / 4) * Global.CUTRANGE * 4 + Global.CUTRANGE * dir.getValue(),
                    (type % 4) * Global.CUTRANGE * 3 + Global.CUTRANGE + Global.CUTRANGE * state.arr[count],
                    (type / 4) * Global.CUTRANGE * 4 + Global.CUTRANGE + Global.CUTRANGE * dir.getValue()
                    ,null);
        }

    }

    public void update() {

        if(delay.count()) {
            count = ++count % state.arr.length;
        }
        if(delayDeadAnimiation.count()) {
            deadUpdateNum++;
        }
    }
}
