package com.WitchHunter.Animator;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.WitchHunterObject.Props.Props;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Path;
import java.awt.*;
import static com.WitchHunter.utils.Global.*;

public class PropsAnimator {

    public enum State {
        RECOVERY_POTION((new Path().img().objects().effectHPRecovery()), new int[]{0, 1, 2, 3, 4, 5, 6, 7}),
        RECOVERY_STATE((new Path().img().objects().effectStateRecovery()), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}),
        RECOVERY_BULLET((new Path().img().objects().effectBulletRecovery()),new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
        EMPOWER_BULLET((new Path().img().objects().effectBulletEmpower()),new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}),
        LANDMINE((new Path().img().objects().bulletExplodeEffect()),new int[]{0}),
        FLARE((new Path().img().objects().effectFlare()),new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11});

        private final String path;
        private final int[] arr;

        State(String path, int[] array){
            this.path = path;
            this.arr = array;
        }
    }


    private State state;

    private Image itemEffect;
    private Image itemEffectWord; //道具特效文字圖

    private final Delay delay;
    private int count;

    private final int wordWidth; //道具特效文字寬
    private final int wordHeight; //道具特效文字高
    private final int wordGap; //道具特效文字與主角間距

    public PropsAnimator(){
        this.delay = new Delay(5);
        this.count = 0;

        wordWidth = UNIT_X + 10;
        wordHeight = UNIT_Y / 2;
        wordGap = 32;
    }

    public void setItemEffect(Props.Category category) {
        count = 0;
        delay.loop();
        switch (category) {
            case RECOVERY_POTION:
                this.state = State.RECOVERY_POTION;
                itemEffectWord = SceneController.instance().irc().tryGetImage(new Path().img().objects().addHP());
                break;
            case RECOVERY_STATE:
                this.state = State.RECOVERY_STATE;
                break;
            case RECOVERY_BULLET:
                this.state = State.RECOVERY_BULLET;
                itemEffectWord = SceneController.instance().irc().tryGetImage(new Path().img().objects().addBullet());
                break;
            case EMPOWER_BULLET:
                this.state = State.EMPOWER_BULLET;
                itemEffectWord = SceneController.instance().irc().tryGetImage(new Path().img().objects().addPower());
                break;
            case LANDMINE:
                this.state = State.LANDMINE;
                break;
            case FLARE:
                this.state = State.FLARE;
                break;
        }
        itemEffect = SceneController.instance().irc().tryGetImage(state.path);
    }

    public void paintComponent(int left, int top, int right, int bottom, Graphics g) {
        switch (state) {
            case RECOVERY_POTION:
                g.drawImage(itemEffect, left-CHACRACTER_UNIT_X/2, top-CHACRACTER_UNIT_Y/2, right+CHACRACTER_UNIT_X/2,bottom+CHACRACTER_UNIT_Y/2,
                        158 * state.arr[count],
                        0,
                        158 + 158 * state.arr[count],
                        158,
                        null);
                g.drawImage(itemEffectWord, left-CHACRACTER_UNIT_X/2, top-CHACRACTER_UNIT_Y/2 - wordGap, wordWidth,wordHeight,
                        null);
                break;
            case EMPOWER_BULLET:
                g.drawImage(itemEffectWord, left-CHACRACTER_UNIT_X/2, top-CHACRACTER_UNIT_Y/2 - wordGap, wordWidth,wordHeight,
                        null);
            case RECOVERY_STATE:
                g.drawImage(itemEffect, left-CHACRACTER_UNIT_X/2, top-CHACRACTER_UNIT_Y/2, right+CHACRACTER_UNIT_X/2,bottom+CHACRACTER_UNIT_Y/2,
                        150 * state.arr[count],
                        0,
                        150 + 150 * state.arr[count],
                        150,
                        null);
                break;

            case RECOVERY_BULLET:
                g.drawImage(itemEffect, left-CHACRACTER_UNIT_X/2, top-CHACRACTER_UNIT_Y/2, right+CHACRACTER_UNIT_X/2,bottom+CHACRACTER_UNIT_Y/2,
                        129 * state.arr[count],
                        0,
                        129 + 129 * state.arr[count],
                        129,
                        null);
                g.drawImage(itemEffectWord, left-CHACRACTER_UNIT_X/2, top-CHACRACTER_UNIT_Y/2 - wordGap, wordWidth,wordHeight,
                        null);
                break;
            case FLARE:
                g.drawImage(itemEffect, left-400, top-400, right+400,bottom+400,
                        144 * state.arr[count],
                        0,
                        144 + 144 * state.arr[count],
                        144,
                        null);
                break;
        }
    }



    public void update() {
        if(delay.count()) {
            count = ++count % state.arr.length;
        }
    }
}
