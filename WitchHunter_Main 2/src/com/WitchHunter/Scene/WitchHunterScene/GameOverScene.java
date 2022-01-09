package com.WitchHunter.Scene.WitchHunterScene;

import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.WitchHunterObject.Display.Letter;
import com.WitchHunter.GameObject.WitchHunterObject.Other.MyCursor;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.WitchHunter.utils.Global.*;

public class GameOverScene extends Scene {

    private ArrayList<Letter> letterList; //字母列
    private MyCursor cursor; //滑鼠
    private boolean canMove; //是否可視區自動移動
    private int diretction; //可視區自動移動方向
    private Delay delay; //可視區移動方向計時器

    @Override
    public void sceneBegin() {

        //建立字母陣列(GAME OVER)
        this.letterList = new ArrayList<>();
        letterList.add(new Letter(WINDOW_WIDTH/2-UNIT_X*5-(UNIT_X/2*7),UNIT_Y*5, Letter.Type.G));
        letterList.add(new Letter(WINDOW_WIDTH/2-UNIT_X*4-(UNIT_X/2*5),UNIT_Y*5, Letter.Type.A));
        letterList.add(new Letter(WINDOW_WIDTH/2-UNIT_X*3-(UNIT_X/2*3),UNIT_Y*5, Letter.Type.M));
        letterList.add(new Letter(WINDOW_WIDTH/2-UNIT_X*2-(UNIT_X/2*1),UNIT_Y*5, Letter.Type.E));

        letterList.add(new Letter(WINDOW_WIDTH/2+UNIT_X*1,UNIT_Y*5, Letter.Type.O));
        letterList.add(new Letter(WINDOW_WIDTH/2+UNIT_X*2+(UNIT_X/2*2),UNIT_Y*5, Letter.Type.V));
        letterList.add(new Letter(WINDOW_WIDTH/2+UNIT_X*3+(UNIT_X/2*4),UNIT_Y*5, Letter.Type.E));
        letterList.add(new Letter(WINDOW_WIDTH/2+UNIT_X*4+(UNIT_X/2*6),UNIT_Y*5, Letter.Type.R));

        this.cursor = new MyCursor((WINDOW_WIDTH/2-UNIT_X*10),(UNIT_Y*4));
        this.canMove = false;
        this.diretction = 1;
        this.delay = new Delay(UPDATE_TIMES_PER_SEC);
        delay.loop();
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        if(IS_DEBUG){
            cursor.getLookrange().paintComponent(g);
        }

        for(int i = 0; i<letterList.size(); i++) {
            Letter letter = letterList.get(i);
            if(cursor.getLookrange().isOverlap(letter)){
                letter.paint(g);
            }
        }

        g.setColor(Color.YELLOW);
        String contentWords = "- 任意點擊畫面回主選單- ";
        g.drawString(contentWords, SCREEN_X/2-((g.getFont().getSize()*contentWords.length())/2), SCREEN_Y-(UNIT_Y*2));
    }

    @Override
    public void update() {
        if(!canMove){
            cursor.translate(diretction);
        }
        if(delay.count()) {
            this.diretction *= -1;
        }
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        this.cursor.getLookrange().scaleDown();
        if(state == CommandSolver.MouseState.MOVED) {
            cursor.setNewLocation(e.getX(),e.getY());
            this.canMove = true;
        }
        if(state == CommandSolver.MouseState.CLICKED) {
            //回到主畫面場景
            SceneController.instance().change(new LoggingScene());
        }
    }
}
