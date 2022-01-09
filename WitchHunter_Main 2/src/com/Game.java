package com;

import javax.swing.*;
import java.awt.event.KeyEvent;

import com.WitchHunter.Internet.Server;
import com.WitchHunter.utils.GameEngine.GI;
import com.WitchHunter.utils.GameEngine.GameKernel;

import static com.WitchHunter.utils.Global.*;


public class Game {


    public static void main(String[] args) {

//        new Thread(() -> Server.getInstance().start()).start();

        /** 開設視窗畫面 */
        JFrame jf = new JFrame();
        jf.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        jf.setTitle("ENDLESS NIGHTMARE");
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 關閉視窗時關閉程式

        /** 關聯鍵盤按鍵＆方向指令 */
        int[][] commands = {
//                {KeyEvent.VK_UP, KeyEvent.VK_UP},
//                {KeyEvent.VK_DOWN, KeyEvent.VK_DOWN},
//                {KeyEvent.VK_LEFT, KeyEvent.VK_LEFT},
//                {KeyEvent.VK_RIGHT, KeyEvent.VK_RIGHT},
                {KeyEvent.VK_W, KeyEvent.VK_W},
                {KeyEvent.VK_A, KeyEvent.VK_A},
                {KeyEvent.VK_S, KeyEvent.VK_S},
                {KeyEvent.VK_D, KeyEvent.VK_D},
                {KeyEvent.VK_1, KeyEvent.VK_1},
                {KeyEvent.VK_2, KeyEvent.VK_2},
                {KeyEvent.VK_3, KeyEvent.VK_3},
                {KeyEvent.VK_4, KeyEvent.VK_4},
                {KeyEvent.VK_5, KeyEvent.VK_5},
                {KeyEvent.VK_ESCAPE,KeyEvent.VK_ESCAPE},
                {KeyEvent.VK_ENTER,KeyEvent.VK_ENTER}

        };

        /** 遊戲的本體 -> 邏輯＆畫面處理 */
        GI gi = new GI();

        /** 遊戲核心(處理各種大小監聽、指令集的偵測) -> 使用建造者模式設定(更加自由開放) */
        GameKernel gk = new GameKernel.Builder(gi,LIMIT_DELTA_TIME,NANOSECOND_PER_UPDATE) // 把【遊戲主體】＆【畫面每秒處理次數】＆【邏輯每秒處理次數】 丟進遊戲引擎
                .initListener(commands) // 加入指令集
                .enableKeyboardTrack(gi)
                .enableMouseTrack(gi) // 打開滑鼠的監聽器
//                .keyTypedMode()// 監聽一次性的鍵盤動作 -> 避免重複偵測
                .keyCleanMode()
                .gen(); // 設定完就可以生產

        /** 將遊戲核心丟到畫布上實現 */
        jf.add(gk); // 由於GameKernel繼承Canvas -> 可以被JFrame吃

//        /** 第一次使用 -> 使用JPanel裝各種東西，但功能太少，改以GameInterface處理 */
//        /** 開設容器->包裝各種內容(各種view) 不一定會使用到 */
//        JPanel jp = new JPanel();
//        jf.add(jp);

//        Timer t = new Timer(10, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                jp.move();
//                jf.repaint();
//            }
//        });
//        t.start();

        /** 將畫布實現 */
        jf.setVisible(true); //若是設成visible -> JFrame會自動呼叫底下的paint()來繪製畫面
                            // -> 再觸發paintComponent() 對旗下的component觸發paint() 及 pointComponent()


//        /** 第一次使用 -> 製作讀秒器 -> 讓遊戲邏輯＆畫面更新維持正常 */
//        long startTime = System.nanoTime(); //取得目前系統時間的奈秒
//        long passedUpdated = 0; //實際應該更新的次數 (當畫面延遲，// 補上資料運算的次數)
//        long lastRepaintTime = System.nanoTime(); //最後一次畫面更新的時間
//        long paintTimes = 0;
//        long timer = System.nanoTime();
//
//        System.out.println(startTime);
//        System.out.println(lastRepaintTime);
//        while (true) {
//            /** 自動把遊戲邏輯更新 -> 使用while不斷檢查邏輯次數 -> 相對嚴謹 */
//            long currentTime = System.nanoTime(); //系統當前時間
//            long totalTime = currentTime - startTime; //從開始到現在經過的時間
//            long targetTotalUpdated = totalTime / (NANOSECOND_PER_UPDATE); //開始到現在應該更新的次數
//            while (passedUpdated < targetTotalUpdated) { //如果當前經過的次數小於實際應該要更新的次數
////                jp.move(); //更新邏輯
//                passedUpdated++; //增加更新次數
//            }
//            /** 計算fps */
//            if(currentTime - timer >= 1000000000) { //每過一秒
//                System.out.println("FPS: " + paintTimes);
//                paintTimes = 0;
//                timer = currentTime;
//            }
//            /** 自動把畫面更新 -> 沒有用while只會在條件成立時更新一次 -> 相對鬆散 */
//            if(LIMIT_DELTA_TIME <= currentTime - lastRepaintTime) {
//                //畫出畫面
//                lastRepaintTime = currentTime; //最後畫的時間變成當前的時間
//                jf.repaint(); //觸發繪圖
//                paintTimes++;
//            }
//        }
        gk.run(true);

    }
}
