package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.WitchHunter.utils.Global.*;

public class LoggingScene extends Scene {

    private final int buttonWidth = 250; //按鈕寬度
    private final int buttonHeight = 60; //按鈕高度
    private final int buttonGap = 100; //按鈕間隔
    private final int mouseWidth = 1; //滑鼠寬度
    private final int mouseHeight = 1; //滑鼠高度

    /** 場景需要圖片*/
    private Image loggingBackground; //登入畫面背景
    private Image devilEye; //登入畫面背景小特效
    private Image game_Title; //遊戲標題
    private Image button_Single; //單人模式
    private Image button_Multi; //多人模式
    private Image button_AboutGame; //關於遊戲
    private Image button_Exit; //離開遊戲

    /** 按鈕做成假的物件 */
    private GameObject singleButton; //單人模式物件
    private GameObject multiButton; //多人模式物件
    private GameObject aboutGameButton; //關於遊戲物件
    private GameObject exitButton; //離開遊戲物件

    private GameObject cursor; //假的滑鼠物件

    /** 按鈕相關 */
    private int singlePressed;
    private int multiPressed;
    private int aboutGamePressed;
    private int exitPressed;


    /** 標題動畫相關 */
    //標題動畫陣列
    private final int[] arr = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    private final int[] arr2 = new int[] {0,1,2,3,4,5,4,3,2,1};
    //標題動畫延遲器
    private Delay delay;
    private Delay delay2;
    //標題動畫計算
    private int count;
    private int count2;


    @Override
    public void sceneBegin() {

        AudioResourceController.getInstance().play(new Path().sounds().loginbackground());

        this.loggingBackground = SceneController.instance().irc().tryGetImage(new Path().img().backgrounds().LoggingBackground());
        this.devilEye = SceneController.instance().irc().tryGetImage(new Path().img().objects().devilEye());
        this.game_Title = SceneController.instance().irc().tryGetImage(new Path().img().buttons().gameTitle());
        this.button_Single = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_singleGame());
        this.button_Multi = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_multiGame());
        this.button_AboutGame = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_aboutGame());
        this.button_Exit = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_exitGame());

        this.singleButton = new GameObject(WINDOW_WIDTH/2-buttonWidth/2, UNIT_Y*5,buttonWidth,buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.multiButton = new GameObject(WINDOW_WIDTH/2-buttonWidth/2, UNIT_Y*5+buttonGap*1,buttonWidth,buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.aboutGameButton = new GameObject(WINDOW_WIDTH/2-buttonWidth/2, UNIT_Y*5+buttonGap*2,buttonWidth,buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.exitButton = new GameObject(WINDOW_WIDTH/2-buttonWidth/2, UNIT_Y*5+buttonGap*3,buttonWidth,buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };

        this.cursor = new GameObject(0,0,mouseWidth,mouseHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };

        this.singlePressed = 0;
        this.multiPressed = 0;
        this.aboutGamePressed = 0;
        this.exitPressed = 0;

        this.delay = new Delay(UPDATE_TIMES_PER_SEC/4);
        this.delay2 = new Delay(UPDATE_TIMES_PER_SEC/4);
        this.count = 0;
        this.count2 = 0;
        delay.loop();
        delay2.loop();
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {

        //畫出背景
        g.drawImage(loggingBackground,0,0,WINDOW_WIDTH,WINDOW_HEIGHT,null);


        //畫出標題&眼睛特效
        g.drawImage(devilEye,UNIT_X*2, UNIT_Y*10,UNIT_X*4,UNIT_Y*12,
                577*arr2[count2],0, 577+577*arr2[count2], 433,null);
        g.drawImage(devilEye,UNIT_X*15, UNIT_Y*7,UNIT_X*17,UNIT_Y*8,
                577*arr2[count2],0, 577+577*arr2[count2], 433,null);
        g.drawImage(devilEye,UNIT_X*17, UNIT_Y,UNIT_X*19,UNIT_Y*2,
                577*arr2[count2],0, 577+577*arr2[count2], 433,null);
        g.drawImage(devilEye,UNIT_X*12, UNIT_Y*11,UNIT_X*13,UNIT_Y*12,
                577*arr2[count2],0, 577+577*arr2[count2], 433,null);
        g.drawImage(devilEye,UNIT_X*5, UNIT_Y*2,UNIT_X*6,UNIT_Y*3,
                577*arr2[count2],0, 577+577*arr2[count2], 433,null);
        g.drawImage(game_Title,WINDOW_WIDTH/2-(UNIT_X*5), UNIT_Y*2,WINDOW_WIDTH/2+(UNIT_X*5),UNIT_Y*4,
                800*arr[count],0, 800+800*arr[count], 200,null);

        //畫出按鈕
        g.drawImage(button_Single,
                singleButton.painter().left(), singleButton.painter().top(),
                singleButton.painter().right(),singleButton.painter().bottom(),
                1000*singlePressed,0,
                1000+1000*singlePressed, 400,null);

        g.drawImage(button_Multi,
                multiButton.painter().left(), multiButton.painter().top(),
                multiButton.painter().right(),multiButton.painter().bottom(),
                1000*multiPressed,0,
                1000+1000*multiPressed, 400,null);

        g.drawImage(button_AboutGame,
                aboutGameButton.painter().left(), aboutGameButton.painter().top(),
                aboutGameButton.painter().right(),aboutGameButton.painter().bottom(),
                1000*aboutGamePressed,0,
                1000+1000*aboutGamePressed, 400,null);

        g.drawImage(button_Exit,
                exitButton.painter().left(), exitButton.painter().top(),
                exitButton.painter().right(),exitButton.painter().bottom(),
                1000*exitPressed,0,
                1000+1000*exitPressed, 400,null);

    }

    @Override
    public void update() {

        if(delay.count()) {
            count = ++count % arr.length;
        }
        if(delay2.count()) {
            count2 = ++count2 % arr2.length;
        }
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {


        if(state == CommandSolver.MouseState.MOVED || state == CommandSolver.MouseState.ENTERED) {

            cursor.setNewXY(e.getX(),e.getY(),mouseWidth,mouseHeight);

            if(cursor.isCollision(singleButton)){
                this.singlePressed = 1;
            } else {
                this.singlePressed = 0;
            }

            if(cursor.isCollision(multiButton)) {
                this.multiPressed = 1;
            } else {
                this.multiPressed = 0;
            }

            if(cursor.isCollision(aboutGameButton)) {
                this.aboutGamePressed = 1;
            } else {
                this.aboutGamePressed = 0;
            }

            if(cursor.isCollision(exitButton)) {
                this.exitPressed = 1;
            } else {
                this.exitPressed = 0;
            }
        }

        if(state == CommandSolver.MouseState.CLICKED) {

            cursor.setNewXY(e.getX(),e.getY(),mouseWidth,mouseHeight);

            if(cursor.isCollision(singleButton)){
                AudioResourceController.getInstance().stop(new Path().sounds().loginbackground());
                SceneController.instance().change(new LoadingScene());
            }

            if(cursor.isCollision(multiButton)) {
                AudioResourceController.getInstance().stop(new Path().sounds().loginbackground());
                SceneController.instance().change(new MultiLoginScene());
            }

            if(cursor.isCollision(aboutGameButton)) {
                SceneController.instance().change(new AboutGameScene());
            }

            if(cursor.isCollision(exitButton)) {
                System.exit(0);
            }
        }
    }
}
