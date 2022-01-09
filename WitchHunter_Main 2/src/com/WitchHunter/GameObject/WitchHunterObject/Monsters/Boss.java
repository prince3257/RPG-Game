package com.WitchHunter.GameObject.WitchHunterObject.Monsters;

import com.WitchHunter.Animator.BossAnimator;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.GameKernel;
import com.WitchHunter.utils.Global;

import java.awt.*;
import java.util.Locale;
import static com.WitchHunter.utils.Global.*;

public class Boss extends GameObject implements GameKernel.GameInterface {

    public enum Type{
        ALTAR("祭壇怪物"),
        PIRATE("海盜船怪物");

        String name;

        Type(String name) {
            this.name = name;
        }
    }

    /** 動畫器相關 */
    private final BossAnimator bossAnimator; //動畫器
    private BossAnimator.State state; //動畫狀態

    /** BOSS性質相關 */
    private final Type type;//魔王種類
    private final String bossName; //魔王名稱
    private int bossHP; //魔王血量
    private boolean recoverHP; //自動回血
    public boolean canUseSkill;
    private final Delay delay; //自動回血時間


    public Boss(int x, int y, Type type){
        //這邊在建構時要注意 -> 範圍跟物件不一樣大小所以有8個參數
        super(x,y,BOSS_WIDTH,BOSS_HEIGHT);
        this.type = type;
        this.bossName = type.name;
        this.bossHP = BOSS_HP;
        this.recoverHP = false;
        this.state = BossAnimator.State.STAND;
        this.bossAnimator = new BossAnimator(type, state);
        this.canUseSkill =false;
        this.delay = new Delay(UPDATE_TIMES_PER_SEC*5);
        delay.loop();
    }


    @Override
    public void paintComponent(Graphics g) {
        //顯示血條
        g.setColor(Color.RED);
        for (int i = 0; i < bossHP; i++) {
            g.fillRect(painter().centerX() - BOSS_HP_WIDTH*(bossHP /2) +(i*BOSS_HP_WIDTH),painter().top()-20,BOSS_HP_WIDTH,BOSS_HP_HEIGHT);
        }
        //顯示名稱
        g.setColor(Color.BLUE);
        int nameLength = g.getFontMetrics().stringWidth(bossName);
        g.drawString(bossName.toUpperCase(Locale.ROOT),painter().centerX()-(nameLength/2),painter().bottom()+20);
        //印出魔王動畫(施展技能等等)
        bossAnimator.paintComponent(painter().left(),painter().top(), painter().right(),painter().bottom(),g);
    }

    @Override
    public void update() {
        if(delay.count()){
            if(recoverHP) {
                bossHP += 5;
            }
        }
        bossAnimator.update();
        if(bossHP<(BOSS_HP*0.3)){
            recoverHP = true;
        }
    }


    //改變BOSS動畫
    public void changeState(BossAnimator.State newState){
        this.bossAnimator.setState(newState);
    }

    //魔王被攻擊
    public void beAttacked(int inputPower) {
        this.bossHP -= inputPower;
        //若血沒了則改變狀態
        if (bossHP <= 0) {
            changeState(BossAnimator.State.DEAD);
        }
    }

    public int getBossHP() {
        return bossHP;
    }

    public Type getType() {
        return type;
    }
}
