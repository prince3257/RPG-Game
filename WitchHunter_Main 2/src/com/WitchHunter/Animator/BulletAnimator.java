package com.WitchHunter.Animator;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Path;

import java.awt.*;

import static com.WitchHunter.utils.Global.*;

public class BulletAnimator {

    private final Image bulletExplode;
    private final Delay delay;
    private int count;
    private int[] arr = {0,1,2,3,4,5};

    public BulletAnimator(){
        this.bulletExplode = SceneController.instance().irc().tryGetImage(new Path().img().objects().bulletExplodeEffect());
        this.delay = new Delay(UPDATE_TIMES_PER_SEC/6);
        this.count = 0;
    }

    public final void expolde() {
        this.delay.loop();
    }

    public void paintComponent(int left, int top, int right, int bottom, Graphics g) {
        g.drawImage(bulletExplode, left-20, top-20, right+20,bottom+20,
                85*count,
                0,
                85+85*count,
                85
                ,null);
    }

    public void update() {
        if(delay.count()) {
            count = ++count % arr.length;
        }
    }
}
