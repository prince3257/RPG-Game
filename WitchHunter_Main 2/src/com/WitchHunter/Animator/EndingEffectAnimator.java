package com.WitchHunter.Animator;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Path;
import java.awt.*;

public class EndingEffectAnimator {

    public enum State {
        FAIL(new Path().img().numbers().escapefail(),new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}),
        SUCCESSED(new Path().img().numbers().escapesuccessed(),new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}),
        FINALE(new Path().img().numbers().finale(),new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});

        private final String path;
        private final int[] arr;

        State (String path, int[] arr) {
            this.path = path;
            this.arr = arr;
        }
    }

    private Image endingEffect; //特效圖片
    private final Delay delay; //特效計時器
    public int count;
    public State state;

    public EndingEffectAnimator(State state){
        this.delay = new Delay(9);
        this.count = 0;
        this.state = state;
    }

    public void setEndingEffect(State state) {
        endingEffect = SceneController.instance().irc().tryGetImage(state.path);
        this.state = state;
        delay.loop();
        count = 0;
    }

    public void paintComponent(int left, int top, int right, int bottom, Graphics g) {
        int x = 336;
        int y = 312;
        if(state == State.FINALE) {
            x = 960;
            y = 640;
        }
        g.drawImage(endingEffect,left,top,right,bottom,x*state.arr[count],0,x+x*state.arr[count],y,null);
    }

    public void update() {
        if(delay.count()) {
            count = ++count % state.arr.length;
        }
    }

}
