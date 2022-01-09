package com.WitchHunter.Controller;

import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.Scene.Scene;

import java.awt.*;
import java.awt.event.MouseEvent;

/** 場景統一切換的控制器 */
public class SceneController {

    private static SceneController sceneController;

    private SceneController() {
        lastIrc = new ImageResourceController();
        currentIrc = new ImageResourceController();
    }

    public static SceneController instance() {
        if(sceneController == null) {
            sceneController =  new SceneController();
        }
        return sceneController;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    private Scene currentScene;
    private Scene lastScene;
    private ImageResourceController lastIrc;
    private ImageResourceController currentIrc;

    public void change(Scene scene) { //這邊要注意，由於先更換場景＆圖片 -> 會影響後面update的狀態
        lastScene = currentScene;

        //釋放資源前置作業 -> 換完場景要同步把圖片資源交換順序 -> 下一次update就會釋放目前使用的記憶體 -> lastIrc.clear()
        ImageResourceController tmp = currentIrc;
        currentIrc = lastIrc;
        lastIrc = tmp;

        if(scene != null) {
            scene.sceneBegin(); //新的圖會存到新的地址
        }
        currentScene = scene;
    }


    /** 下方透過Scene的function統一操控各場景 -> 有點像是delegate的概念 -> 未來擴充時會比較好改善 */
    public void paint(Graphics g) {
        if(currentScene != null) {
            currentScene.paint(g);
        }
    }

    public void update() {
        if(lastScene != null) {
            lastScene.sceneEnd(); //如果剛剛改變場景 -> change()，這邊就會把目前畫面上的資料清空
            lastIrc.clear(); // 把目前圖片資源釋放出來 -> 剛剛change()已經交換過
            lastScene = null;
        }
        if(currentScene != null) {
            currentScene.update();
        }
    }

    public void keyPressed(int commandCode, long trigTime) {
        currentScene.keyPressed(commandCode,trigTime);
    }

    public void keyReleased(int commandCode, long trigTime) {
        currentScene.keyReleased(commandCode,trigTime);
    }

    public void keyTyped(char c, long trigTime) {
        currentScene.keyTyped(c,trigTime);
    }

    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        currentScene.mouseTrig(e,state,trigTime);
    }

    public ImageResourceController irc() {
        return currentIrc;
    }
}
