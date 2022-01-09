package com.WitchHunter.Animator;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.WitchHunterObject.Monsters.Monster;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.Global;

import java.awt.*;

import static com.WitchHunter.utils.Global.UPDATE_TIMES_PER_SEC;

public class MonsterAnimator {

    private final Image img; //圖片路徑
    private int width; //圖片寬
    private int height; //圖片高
    private int[] arr; //動畫圖片數
    private int startX; //切割x起始點
    private int startY; //切割y起始點
    private final Delay delay; //一輪動畫時間
    private int count; //計次到第幾輪

    public MonsterAnimator(String path, Monster.Type type, Monster.State state, Global.Direction dir) {
        img = SceneController.instance().irc().tryGetImage(path);
        delay = new Delay(0);
        setMonsterImage(type, state, dir);
    }

    /**
     * 設定圖片數量陣列
     * @param imgNum 圖片數量
     */
    public void setArr(int imgNum) {
        this.arr = new int[imgNum];
        for (int i = 0; i < imgNum; i++) {
            arr[i] = i;
        }
    }
    /**
     * 設定圖片延遲時間
     * @param delayTime 延遲時間
     */
    public void setDelay(int delayTime) {
        this.delay.stop();
        this.delay.setCountLimit(delayTime);
        this.delay.loop();
        this.count = 0;
    }

    /**
     * 設定各種怪物處於各種狀態下的圖片切割
     * @param type 怪物類型
     * @param state 怪物狀態
     * @param dir 怪物方向
     */
    public void setMonsterImage(Monster.Type type, Monster.State state, Global.Direction dir) {
        switch (type) {
            case MUMMY_DOG:
            {
                width = 80;
                height = 80;
                startX = 0;
                if (state == Monster.State.ELIMINATED) {
                    setArr(6);
                    if (dir == Global.Direction.LEFT) {
                        startY = 160;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 240;
                    }
                } else {
                    setArr(5);
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 80;
                    }
                }
            }
            break;
            case ZOMBIE:
            {
                width = 80;
                height = 105;
                startX = 0;
                if (state == Monster.State.ELIMINATED) {
                    setArr(6);
                    if (dir == Global.Direction.LEFT) {
                        startY = 210;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 315;
                    }
                } else {
                    setArr(3);
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 105;
                    }
                }
            }
            break;
            case MASTER_GHOST:
            {
                width = 100;
                height = 100;
                startX = 0;
                setArr(6);
                if (state == Monster.State.ELIMINATED) {
                    if (dir == Global.Direction.LEFT) {
                        startY = 200;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 300;
                    }
                } else {
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 100;
                    }
                }
            }
            break;
            case WAILING_GHOST:
            {
                width = 80;
                height = 80;
                startX = 0;
                setArr(6);
                if (state == Monster.State.ELIMINATED) {
                    if (dir == Global.Direction.LEFT) {
                        startY = 160;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 240;
                    }
                } else {
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 80;
                    }
                }
            }
            break;
            case BOOK_GHOST:
            {
                width = 100;
                startX = 0;
                setArr(6);
                if (state == Monster.State.ELIMINATED) {
                    height = 150;
                    if (dir == Global.Direction.LEFT) {
                        startY = 200;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 350;
                    }
                } else {
                    height = 100;
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 100;
                    }
                }
            }
            break;
            case SOUL_TEDDY:
            {
                width = 100;
                height = 150;
                startX = 0;
                setArr(6);
                if (state == Monster.State.ELIMINATED) {
                    if (dir == Global.Direction.LEFT) {
                        startY = 300;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 450;
                    }
                } else {
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 150;
                    }
                }
            }
            break;
            case BONE_FISH:
            {
                width = 120;
                startX = 0;
                setArr(6);
                if (state == Monster.State.ELIMINATED) {
                    height = 90;
                    if (dir == Global.Direction.LEFT) {
                        startY = 160;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 250;
                    }
                } else {
                    height = 80;
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 80;
                    }
                }
            }
            break;
            case PHANTOM_WATCH:
            {
                width = 170;
                height = 150;
                startX = 0;
                setArr(6);
                if (state == Monster.State.ELIMINATED) {
                    if (dir == Global.Direction.LEFT) {
                        startY = 300;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 450;
                    }
                } else {
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 150;
                    }
                }
            }
            break;
            case SKELETON:
            {
                width = 100;
                height = 100;
                startX = 0;
                if (state == Monster.State.ELIMINATED) {
                    setArr(6);
                    if (dir == Global.Direction.LEFT) {
                        startY = 200;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 300;
                    }
                } else {
                    setArr(4);
                    if (dir == Global.Direction.LEFT) {
                        startY = 0;
                    }
                    if (dir == Global.Direction.RIGHT) {
                        startY = 100;
                    }
                }
            }
            break;
        }

        //設定delay時間
        switch (state) {
            case WALK:
                setDelay(UPDATE_TIMES_PER_SEC / 3);
                break;
            case RUN: setDelay(UPDATE_TIMES_PER_SEC / 12);
                break;
            case ELIMINATED:
                setDelay(UPDATE_TIMES_PER_SEC / 6);
                break;
        }
    }

    public void paintComponent(int left, int top, int right, int bottom, Graphics g) {
        //前四個 -> 預計呈現在畫布上的位置(dx1,dy1)到(dx2,dy2)
        //後四個 -> 資料來源的定位點位置從(sx1,sy1)到(sx2,sy2)
        g.drawImage(img, left, top, right, bottom,
                startX + width * arr[count],
                startY,
                width + width * arr[count],
                startY + height,
                null);
    }

    public void update() {
        if (delay.count()) {
            count = ++count % arr.length;
        }
    }
}
