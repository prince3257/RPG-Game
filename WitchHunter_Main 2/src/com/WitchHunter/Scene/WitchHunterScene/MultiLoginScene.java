package com.WitchHunter.Scene.WitchHunterScene;


import com.WitchHunter.Animator.Animator;
import com.WitchHunter.Controller.AudioResourceController;
import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Actors.Player;
import com.WitchHunter.Internet.Client;
import com.WitchHunter.Internet.CommandReceiver;
import com.WitchHunter.Scene.Scene;
import com.WitchHunter.utils.Delay;
import com.WitchHunter.utils.GameEngine.CommandSolver;
import com.WitchHunter.utils.Global;
import com.WitchHunter.utils.Path;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import static com.WitchHunter.utils.Global.*;

public class MultiLoginScene extends Scene {


    private int backgroundImageWidth; //背景圖片寬
    private int backgroundImageHeight; //背景圖片高
    private Image backgroundImg; //背景圖片
    private Image lobyButton; // 登入大廳
    private Image appearanceDescription; //選擇外觀說明
    private Image readyDescription; //準備區說明

    private Player localPlayer; //本地玩家
    private int localTypeRecord; //本地玩家外觀紀錄
    private Player remotePlayer; //遠端玩家
    private int remoteTypeRecord; //遠端玩家外觀紀錄
    private int randomMap;

    private Image allActors; //全部外觀
    private ArrayList<GameObject> appearances; //全部外觀選擇區
    private GameObject readyArea; //準備區
    private final int[] arr = new int[]{0,1,2,3,4,5,6,7,7};//門的動畫陣列
    private Image doorImage;
    private GameObject door;
    private int count;
    private Delay delay;
    private Delay delayForChangeSence;
    private boolean closeDoor;

    private boolean isOK;

    @Override
    public void sceneBegin() {

        Client.getInstance().start(5300, IP);

        /** 加入音效 */
        AudioResourceController.getInstance().loop(new Path().sounds().teachingBackground(),99);

        /** 背景圖片 */
        this.backgroundImageWidth = WINDOW_WIDTH;
        this.backgroundImageHeight = WINDOW_HEIGHT;
        this.backgroundImg = SceneController.instance().irc().tryGetImage(new Path().img().backgrounds().TeachingBackground());
        this.lobyButton = SceneController.instance().irc().tryGetImage(new Path().img().buttons().multi_loby());
        this.readyDescription = SceneController.instance().irc().tryGetImage(new Path().img().buttons().readyDescription());
        this.appearanceDescription = SceneController.instance().irc().tryGetImage(new Path().img().buttons().appearanceDescription());
        this.doorImage = SceneController.instance().irc().tryGetImage(new Path().img().objects().doorOpenEffect());

        /** 建構玩家 */
        this.localPlayer = new Player(WINDOW_WIDTH/4, (WINDOW_HEIGHT*3)/4,1,"本地玩家");
        this.localTypeRecord = 1;
        this.remotePlayer = new Player((WINDOW_WIDTH*3)/4,(WINDOW_HEIGHT*3)/4,2,"遠端玩家");
        this.remoteTypeRecord = 1;

        /** 讓玩家選外觀 */
        this.allActors = SceneController.instance().irc().tryGetImage(new Path().img().actors().actornormalwalk());
        this.appearances = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            appearances.add(new GameObject(UNIT_X*(2+(i*2)),UNIT_Y*4+30,UNIT_X,UNIT_Y) {
                @Override
                public void paintComponent(Graphics g) {

                }

                @Override
                public void update() {

                }
            });
        }
        this.readyArea = new GameObject(WINDOW_WIDTH/2-(UNIT_X*3),WINDOW_HEIGHT-UNIT_Y*2,UNIT_X*6,UNIT_Y*2) {
            @Override
            public void paintComponent(Graphics g) {

            }

            @Override
            public void update() {

            }
        };

        //創建門實體
        this.door = new GameObject(WINDOW_WIDTH/2-100, WINDOW_HEIGHT-UNIT_Y*2-200, 200, 200) {

            @Override
            public void update() {

            }

            @Override
            public void paintComponent(Graphics g) {

            }
        };
        this.closeDoor =true;
        this.delay = new Delay(UPDATE_TIMES_PER_SEC/4);
        delay.loop();
        this.delayForChangeSence = new Delay(UPDATE_TIMES_PER_SEC*2);
        this.count = 0;

//        Server.getInstance().start();
//        Client.getInstance().start(5300, "192.168.1.28");

    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(backgroundImg,0,0, backgroundImageWidth, backgroundImageHeight,null);

        //遮罩
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g.fillRect(0,0,backgroundImageWidth,backgroundImageHeight);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g.drawImage(lobyButton,WINDOW_WIDTH/2-(UNIT_X*3),UNIT_Y,UNIT_X*6, UNIT_Y+(UNIT_Y/2),null);
        g.drawImage(appearanceDescription,WINDOW_WIDTH/2-(UNIT_X*4),UNIT_Y*2, UNIT_X*8, UNIT_Y*2+UNIT_Y,null);
        g.drawImage(readyDescription,readyArea.painter().left(),readyArea.painter().top(),readyArea.painter().width(),readyArea.painter().height(),null);

        g.drawImage(doorImage, door.painter().left(), door.painter().top(), door.painter().right(), door.painter().bottom(),
                0,
                96 * arr[count],
                100,
                96 + 96 * arr[count],
                null);

        localPlayer.paint(g);
        remotePlayer.paint(g);
        for(int i = 0; i < 8; i++){
            g.drawImage(allActors,appearances.get(i).painter().left(),appearances.get(i).painter().top(),appearances.get(i).painter().right(),appearances.get(i).painter().bottom(),
                    CUTRANGE+(CUTRANGE*3*(i%4)),CUTRANGE*4*(i/4),CUTRANGE+(CUTRANGE*3*(i%4))+CUTRANGE,CUTRANGE*4*(i/4)+CUTRANGE,null);
        }

    }

    @Override
    public void update() {

        while (!isOK){
            try {
                Client.getInstance().connect();
                System.out.println("Works");
                isOK= true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        updateFrame(localPlayer,PLAYER1);

        if(door.isCollision(localPlayer) &&door.isCollision(remotePlayer)) {
            if(closeDoor){
                AudioResourceController.getInstance().shot(new Path().sounds().doorOpened());//碰撞門觸發音效
                delay.loop();
                closeDoor = false;
            }
            if (delay.count()) {
                count = ++count % arr.length;
                delayForChangeSence.play();
            }
        }

        if(delayForChangeSence.count()){
            //要把剛剛角色的type傳到下一個場景
            MultiBattleMode newMultiBattle = new MultiBattleMode();
            newMultiBattle.setActorsAppearance(localTypeRecord,remoteTypeRecord);
            newMultiBattle.setNewMap(randomMap);
            SceneController.instance().change(newMultiBattle);
            AudioResourceController.getInstance().stop(new Path().sounds().teachingBackground());//停音樂
        }

        action(new CommandReceiver() {
            @Override
            public void run(Client.Command command) {
                switch (command.getCommandCode()) {
                    case Global.Commands.MOVE:
                        if (command.getSerialNum() == PLAYER1) {
                            if (command.getStrs().get(0).equals("A") || command.getStrs().get(0).equals("a")) {
                                if(localPlayer.getPlayerHP()>=0){
                                    localPlayer.changeState(Animator.State.WALK);
                                    localPlayer.setDir(Direction.LEFT);
                                    localPlayer.move();
                                }
//                                leftCollision(localPlayer, localPlayer.speed());
                            }
                            if (command.getStrs().get(0).equals("D") || command.getStrs().get(0).equals("d")) {
                                if(localPlayer.getPlayerHP()>=0){
                                    localPlayer.changeState(Animator.State.WALK);
                                    localPlayer.setDir(Direction.RIGHT);
                                    localPlayer.move();
                                }
//                                rightCollision(localPlayer, localPlayer.speed());
                            }
                            if (command.getStrs().get(0).equals("W") || command.getStrs().get(0).equals("w")) {
                                if(localPlayer.getPlayerHP()>=0){
                                    localPlayer.changeState(Animator.State.WALK);
                                    localPlayer.setDir(Direction.UP);
                                    localPlayer.move();
                                }
//                                topCollision(localPlayer, localPlayer.speed());
                            }
                            if (command.getStrs().get(0).equals("S") || command.getStrs().get(0).equals("s")) {
                                if(localPlayer.getPlayerHP()>=0){
                                    localPlayer.changeState(Animator.State.WALK);
                                    localPlayer.setDir(Direction.DOWN);
                                    localPlayer.move();
                                }
//                                bottomCollision(hunter, hunter.speed());
                            }
                        }

                        if (command.getSerialNum() == PLAYER2) {
                            if (command.getStrs().get(0).equals("A") || command.getStrs().get(0).equals("a")) {
                                if(remotePlayer.getPlayerHP()>=0){
                                    remotePlayer.changeState(Animator.State.WALK);
                                    remotePlayer.setDir(Direction.LEFT);
                                    remotePlayer.move();
                                }
//                                leftCollision(hunter2, hunter2.speed());
                            }
                            if (command.getStrs().get(0).equals("D") || command.getStrs().get(0).equals("d")) {
                                if(remotePlayer.getPlayerHP()>=0){
                                    remotePlayer.changeState(Animator.State.WALK);
                                    remotePlayer.setDir(Direction.RIGHT);
                                    remotePlayer.move();
                                }
//                                rightCollision(hunter2, hunter2.speed());
                            }
                            if (command.getStrs().get(0).equals("W") || command.getStrs().get(0).equals("w")) {
                                if(remotePlayer.getPlayerHP()>=0){
                                    remotePlayer.changeState(Animator.State.WALK);
                                    remotePlayer.setDir(Direction.UP);
                                    remotePlayer.move();
                                }
//                                topCollision(hunter2, hunter2.speed());
                            }
                            if (command.getStrs().get(0).equals("S") || command.getStrs().get(0).equals("s")) {
                                if(remotePlayer.getPlayerHP()>=0){
                                    remotePlayer.changeState(Animator.State.WALK);
                                    remotePlayer.setDir(Direction.DOWN);
                                    remotePlayer.move();
                                }
//                                bottomCollision(hunter2, hunter2.speed());
                            }
                        }
                        break;
                    case Global.Commands.UPDATE_FRAME:
                        if(command.getSerialNum() == PLAYER2 ) {
                            if (!command.getStrs().isEmpty()) {
                                remotePlayer.setNewX(Integer.parseInt(command.getStrs().get(0)));
                                remotePlayer.setNewY(Integer.parseInt(command.getStrs().get(1)));
                            }
                        }
                        break;
                    case Global.Commands.MAP:
                        if(command.getSerialNum() == PLAYER2){
                            randomMap = Integer.parseInt((command.getStrs().get(0)));
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        for(int i = 0; i < appearances.size(); i++){
            GameObject obj = appearances.get(i);
            if(localPlayer.isCollision(obj)){
                localPlayer.setPlayerType(i);
                localTypeRecord = i;
            }
            if(remotePlayer.isCollision(obj)){
                remotePlayer.setPlayerType(i);
                remoteTypeRecord = i;
            }
        }

        localPlayer.update();
        remotePlayer.update();

    }

    @Override
    public void keyPressed ( int commandCode, long trigTime){

        //判斷主角有沒有撞牆
        if (commandCode == KeyEvent.VK_A) {
            localPlayer.setLockRight(localPlayer.collider().right() >= WINDOW_WIDTH);
        }
        if (commandCode == KeyEvent.VK_W) {
            localPlayer.setLockBottom(localPlayer.collider().bottom() >= WINDOW_HEIGHT - 50);
        }
        if (commandCode == KeyEvent.VK_D) {
            localPlayer.setLockRight(localPlayer.collider().right() >= WINDOW_WIDTH);
        }
        if (commandCode == KeyEvent.VK_S) {
            localPlayer.setLockBottom(localPlayer.collider().bottom() >= WINDOW_HEIGHT - 50);
        }

        localPlayer.keyPressed(commandCode,trigTime);

        ////////////

        if(commandCode == KeyEvent.VK_W) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add("W");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        if(commandCode == KeyEvent.VK_A) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add("A");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        if(commandCode == KeyEvent.VK_S) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add("S");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

        if(commandCode == KeyEvent.VK_D) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add("D");
            Client.getInstance().sendCommand(new Client.Command(PLAYER1, Global.Commands.MOVE,IP, arr));
        }

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        if(commandCode == KeyEvent.VK_ESCAPE) {
            AudioResourceController.getInstance().stop(new Path().sounds().teachingBackground());
            SceneController.instance().change(new LoggingScene());
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }


    //網路模組用
    public void action(CommandReceiver commandReceiver) {
        Client.getInstance().consume(commandReceiver);
    }

    public void updateFrame(GameObject o, int id) {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(Integer.toString(o.painter().left()));
        arr.add(Integer.toString(o.painter().top()));
        Client.getInstance().sendCommand(new Client.Command(id, Global.Commands.UPDATE_FRAME,IP,arr));

    }
}
