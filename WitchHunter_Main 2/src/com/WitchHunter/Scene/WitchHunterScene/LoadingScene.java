package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DateFormat;

import static com.WitchHunter.utils.Global.*;

public class LoadingScene extends Scene {

    /** 場景需要圖片*/
    private Image wakeUpSceneOne; //登入畫面背景1
    private Image wakeUpSceneTwo; //登入畫面背景2
    private Image subtitleOne; //登入台詞1
    private Image subtitleTwo; //登入台詞2
    private Image subtitleThree; //登入台詞3

    /** 動畫相關 */
    private Delay delayStart;
    private Delay subtitleOneDelay; //動畫的delay
    private Delay subtitleTwoDelay; //動畫的delay
    private Delay subtitleThreeDelay; //動畫的delay
    private Delay wakeUpSceneOneDelay; //動畫的delay
    private Delay wakeUpSceneTwoDelay; //動畫的delay
    private Delay screammingDelay;

    private boolean subtitleOneDisplay;
    private boolean subtitleTwoDisplay;
    private boolean subtitleThreeDisplay;
    private boolean wakeUpSceneOneDisplay;
    private boolean wakeUpSceneTwoDisplay;

    /** 動畫需要陣列 */
    private final int[] arr51 = new int[] {0,1,2,3,4};
    private final int[] arr52 = new int[] {0,1,2,3,4,3,2,1};
    private final int[] arr7 = new int[] {0,1,2,3,4,5,6};

    private int count;

    @Override
    public void sceneBegin() {
        AudioResourceController.getInstance().loop(new Path().sounds().loadingsound(),99);

        this.subtitleOne = SceneController.instance().irc().tryGetImage(new Path().img().loading().subtitle_soCold());
        this.subtitleTwo = SceneController.instance().irc().tryGetImage(new Path().img().loading().subtitle_whereAmI());
        this.subtitleThree = SceneController.instance().irc().tryGetImage(new Path().img().loading().subtitle_betterHurryUP());
        this.wakeUpSceneOne = SceneController.instance().irc().tryGetImage(new Path().img().loading().wakeup_sceneOne());
        this.wakeUpSceneTwo = SceneController.instance().irc().tryGetImage(new Path().img().loading().wakeup_sceneTwo());

        this.delayStart = new Delay(UPDATE_TIMES_PER_SEC*2);
        delayStart.play();
        this.subtitleOneDelay = new Delay(UPDATE_TIMES_PER_SEC);
        this.wakeUpSceneOneDelay = new Delay(UPDATE_TIMES_PER_SEC-10);
        this.wakeUpSceneTwoDelay = new Delay(UPDATE_TIMES_PER_SEC-10);
        this.subtitleTwoDelay = new Delay(UPDATE_TIMES_PER_SEC);
        this.subtitleThreeDelay = new Delay(UPDATE_TIMES_PER_SEC*2);
        this.screammingDelay = new Delay(UPDATE_TIMES_PER_SEC*2);

        this.subtitleOneDisplay = false;
        this.subtitleTwoDisplay = false;
        this.subtitleThreeDisplay = false;
        this.wakeUpSceneOneDisplay = false;
        this.wakeUpSceneTwoDisplay = false;

        this.count = 0;
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {

        //好冷...
        if(subtitleOneDisplay){
            g.drawImage(subtitleOne,UNIT_X*2, UNIT_Y*2,UNIT_X*12, UNIT_Y*6,800*arr51[count],0,800+800*arr51[count],250,null);
        }

        //(畫面一)
        if(wakeUpSceneOneDisplay){
            g.drawImage(wakeUpSceneOne,0,0,WINDOW_WIDTH,WINDOW_HEIGHT,960*arr52[count],0,960+960*arr52[count],640,null);
        }

        //(畫面二)
        if(wakeUpSceneTwoDisplay){
            g.drawImage(wakeUpSceneTwo,0,0,WINDOW_WIDTH,WINDOW_HEIGHT,960*arr52[count],0,960+960*arr52[count],640,null);
        }

        //這...是哪裡？
        if(subtitleTwoDisplay){
            g.drawImage(subtitleTwo,UNIT_X*3, UNIT_Y*5,UNIT_X*12, UNIT_Y*8,800*arr7[count],0,800+800*arr7[count],250,null);
        }

        //(怪獸嘶吼聲)

        //我得趕快出去...
        if(subtitleThreeDisplay){
            g.drawImage(subtitleThree,UNIT_X*2, UNIT_Y*2,UNIT_X*17, UNIT_Y*7,null);
        }

        //畫面漸暗

        g.setColor(Color.YELLOW);
        String contentWords = "- 按下ENTER跳過動畫 - ";
        g.drawString(contentWords, SCREEN_X-(g.getFont().getSize()*contentWords.length()), (UNIT_Y));

    }

    @Override
    public void update() {
        if(delayStart.count()){
            this.subtitleOneDisplay = true;
            count = 0;
            delayStart.stop();
            subtitleOneDelay.loop();
        }
        if(subtitleOneDelay.count()) {
            count = ++count % arr51.length;
            if(count == arr51.length-1){
                count = 0;
                wakeUpSceneOneDisplay = true;
                wakeUpSceneOneDelay.loop();
                subtitleOneDisplay = false;
                subtitleOneDelay.stop();

            }
        }
        if(wakeUpSceneOneDelay.count()){
            count = ++count % arr52.length;
            if(count == arr52.length-1){
                count = 0;

                wakeUpSceneTwoDisplay = true;
                wakeUpSceneTwoDelay.loop();
                wakeUpSceneOneDisplay = false;
                wakeUpSceneOneDelay.stop();
            }
        }
        if(wakeUpSceneTwoDelay.count()){
            count = ++count % arr52.length;
            if(count == arr52.length-1){
                count = 0;

                subtitleTwoDisplay = true;
                subtitleTwoDelay.loop();
                wakeUpSceneTwoDisplay = false;
                wakeUpSceneTwoDelay.stop();
            }
        }

        if(subtitleTwoDelay.count()){
            count = ++count % arr7.length;
            if(count == arr7.length-1){
                count = 0;

                AudioResourceController.getInstance().play(new Path().sounds().girlDead());
                screammingDelay.loop();
                subtitleTwoDisplay = false;
                subtitleTwoDelay.stop();
            }
        }

        if(screammingDelay.count()){
            count = 0;
            AudioResourceController.getInstance().play(new Path().sounds().gethurryup());
            subtitleThreeDisplay= true;
            subtitleThreeDelay.loop();


            screammingDelay.stop();
        }

        if(subtitleThreeDelay.count()){
            subtitleThreeDisplay = false;
            subtitleThreeDelay.stop();
            SceneController.instance().change(new TeachingScene());
            AudioResourceController.getInstance().stop(new Path().sounds().loadingsound());
        }
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        if(commandCode == KeyEvent.VK_ENTER) {
            SceneController.instance().change(new TeachingScene());
            AudioResourceController.getInstance().stop(new Path().sounds().girlDead());
            AudioResourceController.getInstance().stop(new Path().sounds().gethurryup());
            AudioResourceController.getInstance().stop(new Path().sounds().loadingsound());
        }
    }
}
