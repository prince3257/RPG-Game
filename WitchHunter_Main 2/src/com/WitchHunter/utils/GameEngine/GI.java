package com.WitchHunter.utils.GameEngine;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.Scene.WitchHunterScene.*;

import java.awt.*;
import java.awt.event.*;

//原始透過JPanel操作 -> 但功能太少若要做動作邏輯&畫面更新會太麻煩
/** 遊戲主體：去操作相關場景，讓其做動作 -> 透過update()：處理每次動作邏輯 & paint()：更新畫面(透過遊戲引擎呼叫) */
public class GI implements GameKernel.GameInterface, CommandSolver.MouseCommandListener, CommandSolver.KeyListener {


    public GI(){
        SceneController.instance().change(new LoggingScene()); //開啟新畫面
    }

    @Override
    public void paint(Graphics g) {
        /** 這邊生成畫面將物件畫出來 -> 每秒跑UPDATE_TIMES_PER_SEC次 */
        SceneController.instance().paint(g);
    }

    @Override
    public void update() {
        /** 這邊做更新 -> 每秒跑NANOSECOND_PER_UPDATE次 */
        SceneController.instance().update();
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        SceneController.instance().mouseTrig(e,state,trigTime);
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        SceneController.instance().keyPressed(commandCode,trigTime);
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        SceneController.instance().keyReleased(commandCode,trigTime);
    }

    @Override
    public void keyTyped(char c, long trigTime) {
        SceneController.instance().keyTyped(c,trigTime);
    }
}
