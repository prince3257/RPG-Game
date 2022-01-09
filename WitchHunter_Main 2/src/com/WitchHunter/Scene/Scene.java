package com.WitchHunter.Scene;

import com.WitchHunter.utils.GameEngine.CommandSolver;

import java.awt.*;
import java.awt.event.MouseEvent;

/** 將遊戲所有物件依據場景切割配置 -> 因此相關屬性因素都在場景中 */
public abstract class Scene implements CommandSolver.MouseCommandListener, CommandSolver.KeyListener {


    public abstract void sceneBegin();

    public abstract void sceneEnd();

    public abstract void paint(Graphics g);

    public abstract void update();

    //可以在這邊先預設Mouse/Keyboard的介面 -> 讓他擁有基本功能 -> 但繼承的子類可能不知道會有這些方法可以改寫
    //或是可以建構希望繼承子類使用的abstract function -> 但沒有使用的場景就一定要實現(可能return null)
    @Override
    public void keyPressed(int commandCode, long trigTime) {

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }



    //也可以寫成以下 -> 不同的接口設定 -> 設定完善會讓後續程式設計更好變更or擴充
//    public abstract CommandSolver.MouseCommandListener mouseCommandListener();
//    public abstract CommandSolver.KeyListener keyListener();




    //以下為若有場景暫停的需求可使用的方法

//    private boolean isPause;
//
//    public com.company.GameProject.Scene() {
//        this.isPause = false;
//    }
//
//    public abstract void sceneBegin();
//
//    public abstract void sceneEnd();
//
//    public void paint(Graphics g) {
//        if (isPause) {
//            paintPause(g);
//        } else {
//            paintPaly(g);
//        }
//    }
//
//    public abstract void paintPaly(Graphics g);
//
//    public abstract void paintPause(Graphics g);
//
//    public abstract void update();
//
//    public void sceneUpdate() {
//        if (!isPause) {
//            update();
//        }
//    }
//
//    public void pause() {
//        this.isPause = true;
//    }

}
