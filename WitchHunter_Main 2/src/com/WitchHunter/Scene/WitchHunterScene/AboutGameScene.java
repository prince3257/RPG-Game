package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.WitchHunter.utils.Global.*;

public class AboutGameScene extends Scene {

    /**
     * 物件設定
     */
    private final int buttonWidth = 250; //按鈕寬度
    private final int buttonHeight = 60; //按鈕高度
    private final int buttonGap = 100; //按鈕間隔
    private final int mouseWidth = 1; //滑鼠寬度
    private final int mouseHeight = 1; //滑鼠高度
    private final int propsLabelWidth = UNIT_X * 2; //道具說明寬
    private final int propsLabelHeight = UNIT_Y * 2; //道具說明高
    private final int propsLabelGap = 70; //道具說明間隔
    private final int teamLabelWidth = UNIT_X * 4; //團隊介紹寬
    private final int teamLabelHeight = UNIT_Y * 6; //團隊介紹高
    private final int teamLabelGap = 50; //團隊介紹間隔

    /**
     * 場景需要圖片
     */
    private Image aboutGameBackground; //關於遊戲背景

    private Image button_story; //故事背景
    private Image story; //故事背景圖片

    private Image button_props; //道具說明
    private Image recovery_bullet; //子彈補充包說明
    private Image enpower_bullet; //子彈增強藥丸說明
    private Image recovery_potion; //補血藥水說明
    private Image recovery_state; //回復藥水說明
    private Image landmine; //地雷說明
    private Image flare; //閃光彈說明

    private Image button_team; //團隊介紹
    private Image heng; //珩
    private Image fang; //芳
    private Image han; //翰

    private Image button_back; //回主選單

    /**
     * 按鈕做成假的物件
     */
    private GameObject storyButton; //故事背景物件
    private GameObject propsButton; //道具說明物件
    private GameObject teamButton; //團隊介紹物件
    private GameObject backButton; //回主選單物件

    private GameObject recovery_bullet_label; //子彈補充包物件
    private GameObject enpower_bullet_label; //子彈增強藥丸物件
    private GameObject recovery_potion_label; //補血藥水物件
    private GameObject recovery_state_label; //回復藥水物件
    private GameObject landmine_label; //地雷物件
    private GameObject flare_label; //閃光彈物件

    private GameObject heng_label; //珩物件
    private GameObject fang_label; //芳物件
    private GameObject han_label; //翰物件

    private GameObject cursor; //假的滑鼠物件

    /**
     * 按鈕顯示相關
     */
    private int storyPressed;
    private int propsPressed;
    private int teamPressed;
    private int backPressed;

    private int recovery_bullet_pressed;
    private int enpower_bullet_pressed;
    private int recovery_potion_pressed;
    private int recovery_state_pressed;
    private int landmine_pressed;
    private int flare_pressed;

    private int heng_pressed;
    private int fang_pressed;
    private int han_pressed;

    /**
     * 按鈕事件相關
     */
    private enum Display {
        STORY,
        PROPS,
        TEAM
    }
    private Display display;

    @Override
    public void sceneBegin() {
        //準備背景圖
        this.aboutGameBackground = SceneController.instance().irc().tryGetImage(new Path().img().backgrounds().aboutGameBackground());

        //準備按鈕圖
        this.button_story = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_story());
        this.button_props = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_props());
        this.button_team = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_team());
        this.button_back = SceneController.instance().irc().tryGetImage(new Path().img().buttons().button_back());

        //準備內容圖
        this.story = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().story());
        this.recovery_bullet = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().recovery_bullet());
        this.enpower_bullet = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().enpower_bullet());
        this.recovery_potion = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().recovery_potion());
        this.recovery_state = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().recovery_state());
        this.landmine = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().landmine());
        this.flare = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().flare());
        this.heng = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().heng());
        this.fang = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().fang());
        this.han = SceneController.instance().irc().tryGetImage(new Path().img().aboutGame().han());

        //生成按鈕物件
        this.storyButton = new GameObject((WINDOW_WIDTH / 2 - buttonWidth / 2) - buttonWidth - buttonGap, UNIT_Y * 2, buttonWidth, buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.propsButton = new GameObject(WINDOW_WIDTH / 2 - buttonWidth / 2, UNIT_Y * 2, buttonWidth, buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.teamButton = new GameObject((WINDOW_WIDTH / 2 - buttonWidth / 2) + buttonWidth + buttonGap, UNIT_Y * 2, buttonWidth, buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.backButton = new GameObject(WINDOW_WIDTH / 2 - buttonWidth / 2, SCREEN_Y - buttonGap, buttonWidth, buttonHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };

        //生成內容物件
        this.recovery_bullet_label = new GameObject((WINDOW_WIDTH / 2 - propsLabelWidth / 2) - propsLabelWidth - propsLabelGap, storyButton.painter().bottom() + propsLabelGap, propsLabelWidth, propsLabelHeight) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.enpower_bullet_label = new GameObject((WINDOW_WIDTH / 2 - propsLabelWidth / 2) - propsLabelWidth - propsLabelGap, recovery_bullet_label.painter().bottom() + propsLabelGap, propsLabelWidth, propsLabelHeight) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.recovery_potion_label = new GameObject(WINDOW_WIDTH / 2 - propsLabelWidth / 2, storyButton.painter().bottom() + propsLabelGap, propsLabelWidth, propsLabelHeight) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.recovery_state_label = new GameObject(WINDOW_WIDTH / 2 - propsLabelWidth / 2, recovery_potion_label.painter().bottom() + propsLabelGap, propsLabelWidth, propsLabelHeight) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.landmine_label = new GameObject((WINDOW_WIDTH / 2 - propsLabelWidth / 2) + propsLabelWidth + propsLabelGap, storyButton.painter().bottom() + propsLabelGap, propsLabelWidth, propsLabelHeight) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.flare_label = new GameObject((WINDOW_WIDTH / 2 - propsLabelWidth / 2) + propsLabelWidth + propsLabelGap, landmine_label.painter().bottom() + propsLabelGap, propsLabelWidth, propsLabelHeight) {
            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.heng_label = new GameObject((WINDOW_WIDTH / 2 - teamLabelWidth / 2) - teamLabelWidth - teamLabelGap, storyButton.painter().bottom() + teamLabelGap, teamLabelWidth, teamLabelHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.fang_label = new GameObject(WINDOW_WIDTH / 2 - teamLabelWidth / 2, storyButton.painter().bottom() + teamLabelGap, teamLabelWidth, teamLabelHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };
        this.han_label = new GameObject((WINDOW_WIDTH / 2 - teamLabelWidth / 2) + teamLabelWidth + teamLabelGap, storyButton.painter().bottom() + teamLabelGap, teamLabelWidth, teamLabelHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };

        //生成假滑鼠物件
        this.cursor = new GameObject(0, 0, mouseWidth, mouseHeight) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };

        //初始化按鈕顯示
        this.storyPressed = 0;
        this.propsPressed = 0;
        this.teamPressed = 0;
        this.backPressed = 0;

        this.recovery_bullet_pressed = 0;
        this.enpower_bullet_pressed = 0;
        this.recovery_potion_pressed = 0;
        this.recovery_state_pressed = 0;
        this.landmine_pressed = 0;
        this.flare_pressed = 0;

        this.heng_pressed = 0;
        this.fang_pressed = 0;
        this.han_pressed = 0;

        //初始化按鈕事件
        this.display = Display.STORY;
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {

        //畫出背景
        g.drawImage(aboutGameBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        //畫出按鈕
        g.drawImage(button_story,
                storyButton.painter().left(), storyButton.painter().top(),
                storyButton.painter().right(), storyButton.painter().bottom(),
                1000 * storyPressed, 0,
                1000 + 1000 * storyPressed, 400, null);

        g.drawImage(button_props,
                propsButton.painter().left(), propsButton.painter().top(),
                propsButton.painter().right(), propsButton.painter().bottom(),
                1000 * propsPressed, 0,
                1000 + 1000 * propsPressed, 400, null);

        g.drawImage(button_team,
                teamButton.painter().left(), teamButton.painter().top(),
                teamButton.painter().right(), teamButton.painter().bottom(),
                1000 * teamPressed, 0,
                1000 + 1000 * teamPressed, 400, null);

        g.drawImage(button_back,
                backButton.painter().left(), backButton.painter().top(),
                backButton.painter().right(), backButton.painter().bottom(),
                1000 * backPressed, 0,
                1000 + 1000 * backPressed, 400, null);

        //畫出內容
        switch (display) {
            case STORY:
                g.drawImage(story,
                        storyButton.painter().centerX(),
                        storyButton.painter().bottom() + 50,
                        teamButton.painter().centerX() - storyButton.painter().centerX(),
                        UNIT_Y * 6,
                        null);
                break;
            case PROPS:
                g.drawImage(recovery_bullet,
                        recovery_bullet_label.painter().left(), recovery_bullet_label.painter().top(),
                        recovery_bullet_label.painter().right(), recovery_bullet_label.painter().bottom(),
                        propsLabelWidth * recovery_bullet_pressed, 0,
                         propsLabelWidth + propsLabelWidth * recovery_bullet_pressed, propsLabelHeight, null);
                g.drawImage(enpower_bullet,
                        enpower_bullet_label.painter().left(), enpower_bullet_label.painter().top(),
                        enpower_bullet_label.painter().right(), enpower_bullet_label.painter().bottom(),
                        propsLabelWidth * enpower_bullet_pressed, 0,
                        propsLabelWidth + propsLabelWidth * enpower_bullet_pressed, propsLabelHeight, null);
                g.drawImage(recovery_potion,
                        recovery_potion_label.painter().left(), recovery_potion_label.painter().top(),
                        recovery_potion_label.painter().right(), recovery_potion_label.painter().bottom(),
                        propsLabelWidth * recovery_potion_pressed, 0,
                        propsLabelWidth + propsLabelWidth * recovery_potion_pressed, propsLabelHeight, null);
                g.drawImage(recovery_state,
                        recovery_state_label.painter().left(), recovery_state_label.painter().top(),
                        recovery_state_label.painter().right(), recovery_state_label.painter().bottom(),
                        propsLabelWidth * recovery_state_pressed, 0,
                        propsLabelWidth + propsLabelWidth * recovery_state_pressed, propsLabelHeight, null);
                g.drawImage(landmine,
                        landmine_label.painter().left(), landmine_label.painter().top(),
                        landmine_label.painter().right(), landmine_label.painter().bottom(),
                        propsLabelWidth * landmine_pressed, 0,
                        propsLabelWidth + propsLabelWidth * landmine_pressed, propsLabelHeight, null);
                g.drawImage(flare,
                        flare_label.painter().left(), flare_label.painter().top(),
                        flare_label.painter().right(), flare_label.painter().bottom(),
                        propsLabelWidth * flare_pressed, 0,
                        propsLabelWidth + propsLabelWidth * flare_pressed, propsLabelHeight, null);
                break;
            case TEAM:
                g.drawImage(heng,
                        heng_label.painter().left(), heng_label.painter().top(),
                        heng_label.painter().right(), heng_label.painter().bottom(),
                        teamLabelWidth * heng_pressed, 0,
                        teamLabelWidth + teamLabelWidth * heng_pressed, teamLabelHeight, null);
                g.drawImage(fang,
                        fang_label.painter().left(), fang_label.painter().top(),
                        fang_label.painter().right(), fang_label.painter().bottom(),
                        teamLabelWidth * fang_pressed, 0,
                        teamLabelWidth + teamLabelWidth * fang_pressed, teamLabelHeight, null);
                g.drawImage(han,
                        han_label.painter().left(), han_label.painter().top(),
                        han_label.painter().right(), han_label.painter().bottom(),
                        teamLabelWidth * han_pressed, 0,
                        teamLabelWidth + teamLabelWidth * han_pressed, teamLabelHeight, null);
                break;
        }
    }

    @Override
    public void update() {
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {


        if (state == CommandSolver.MouseState.MOVED || state == CommandSolver.MouseState.ENTERED) {

            cursor.setNewXY(e.getX(), e.getY(), mouseWidth, mouseHeight);

            //滑鼠移到按鈕事件 -> 按鈕變色
            if (cursor.isCollision(storyButton)) {
                this.storyPressed = 1;
            } else {
                this.storyPressed = 0;
            }
            if (cursor.isCollision(propsButton)) {
                this.propsPressed = 1;
            } else {
                this.propsPressed = 0;
            }
            if (cursor.isCollision(teamButton)) {
                this.teamPressed = 1;
            } else {
                this.teamPressed = 0;
            }
            if (cursor.isCollision(backButton)) {
                this.backPressed = 1;
            } else {
                this.backPressed = 0;
            }

            //滑鼠移到道具事件 -> 顯示道具說明
            if (cursor.isCollision(recovery_bullet_label)) {
                this.recovery_bullet_pressed = 1;
            } else {
                this.recovery_bullet_pressed = 0;
            }
            if (cursor.isCollision(enpower_bullet_label)) {
                this.enpower_bullet_pressed = 1;
            } else {
                this.enpower_bullet_pressed = 0;
            }
            if (cursor.isCollision(recovery_potion_label)) {
                this.recovery_potion_pressed = 1;
            } else {
                this.recovery_potion_pressed = 0;
            }
            if (cursor.isCollision(recovery_state_label)) {
                this.recovery_state_pressed = 1;
            } else {
                this.recovery_state_pressed = 0;
            }
            if (cursor.isCollision(landmine_label)) {
                this.landmine_pressed = 1;
            } else {
                this.landmine_pressed = 0;
            }
            if (cursor.isCollision(flare_label)) {
                this.flare_pressed = 1;
            } else {
                this.flare_pressed = 0;
            }

            //滑鼠移到人物事件 -> 顯示人物說明
            if (cursor.isCollision(heng_label)) {
                this.heng_pressed = 1;
            } else {
                this.heng_pressed = 0;
            }
            if (cursor.isCollision(fang_label)) {
                this.fang_pressed = 1;
            } else {
                this.fang_pressed = 0;
            }
            if (cursor.isCollision(han_label)) {
                this.han_pressed = 1;
            } else {
                this.han_pressed = 0;
            }
        }

        if (state == CommandSolver.MouseState.CLICKED) {

            cursor.setNewXY(e.getX(), e.getY(), mouseWidth, mouseHeight);

            //點擊按鈕 -> 切換畫面/場景
            if (cursor.isCollision(storyButton)) {
                display = Display.STORY;
            }
            if (cursor.isCollision(propsButton)) {
                display = Display.PROPS;
            }
            if (cursor.isCollision(teamButton)) {
                display = Display.TEAM;
            }
            if (cursor.isCollision(backButton)) {
                SceneController.instance().change(new LoggingScene());
            }
        }
    }
}
