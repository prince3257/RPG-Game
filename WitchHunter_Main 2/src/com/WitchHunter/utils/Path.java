package com.WitchHunter.utils;

/** 圖片路徑的統一管理 */
public class Path {

    //建構一個抽象類別使其可以被繼承並應用串連路徑的方法
    public static abstract class Flow {
        private String path;

        public Flow(String path) {
            this.path = path;
        }

        public Flow(Flow flow, String path) {
            this.path = flow.path + path;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    //資源資料夾
    private static class Resources extends Flow {
        private Resources() {
            super("/resources");
        }
    }

    //圖片資料夾
    public static class Imgs extends Flow {

        private Imgs() {
            super(new Resources(), "/images");
        }

        //關於遊戲資料夾
        public static class AboutGame extends Flow {

            private AboutGame(Flow flow) {
                super(flow, "/aboutGame");
            }

            //故事背景
            public String story() {
                return this + "/story.png";
            }

            //子彈補充包說明
            public String recovery_bullet() {
                return this + "/description_recoverybullet.png";
            }
            //子彈增強藥丸說明
            public String enpower_bullet() {
                return this + "/description_enpowerbullet.png";
            }
            //補血藥水說明
            public String recovery_potion() {
                return this + "/description_recoverypotion.png";
            }
            //回復藥水說明
            public String recovery_state() {
                return this + "/description_recoverystate.png";
            }
            //地雷說明
            public String landmine() {
                return this + "/description_landmine.png";
            }
            //閃光彈說明
            public String flare() {
                return this + "/description_flare.png";
            }


            //子彈補充包說明
            public String shortdescription_recoverybullet() {
                return this + "/shortdescription_recoverybullet.png";
            }
            //子彈增強藥丸說明
            public String shortdescription_enpowerbullet() {
                return this + "/shortdescription_enpowerbullet.png";
            }
            //補血藥水說明
            public String shortdescription_recoverypotion() {
                return this + "/shortdescription_recoverypotion.png";
            }
            //回復藥水說明
            public String shortdescription_recoverystate() {
                return this + "/shortdescription_recoverystate.png";
            }
            //地雷說明
            public String shortdescription_landmine() {
                return this + "/shortdescription_landmine.png";
            }
            //閃光彈說明
            public String shortdescription_flare() {
                return this + "/shortdescription_flare.png";
            }


            //珩
            public String heng() {
                return this + "/heng.png";
            }
            //芳
            public String fang() {
                return this + "/fang.png";
            }
            //翰
            public String han() {
                return this + "/han.png";
            }
        }

        //載入畫面資料夾
        public static class Loading extends Flow{

            private Loading(Flow flow) {
                super(flow, "/loading");
            }

            public String wakeup_sceneTwo() {
                return this + "/wakeup_sceneTwo.png";
            }

            public String wakeup_sceneOne() {
                return this + "/wakeup_sceneOne.png";
            }

            public String subtitle_soCold() {
                return this + "/subtitle_soCold.png";
            }

            public String subtitle_whereAmI() {
                return this + "/subtitle_whereAmI.png";
            }

            public String subtitle_betterHurryUP() {
                return this + "/subtitle_betterHurryUP.png";
            }

            //一般關卡遊戲畫面說明
            public String directions() {
                return this + "/directions.png";
            }


        }

        //遊戲角色資料夾
        public static class Actors extends Flow{

            private Actors(Flow flow) {
                super(flow, "/actors");
            }

            public String actors() {
                return this + "/actors.png";
            }

            public String actornormalwalk() {
                return this + "/actorWalk.png";
            }

            public String actorgetweak() {
                return this + "/actorgetweak.png";
            }

            public String actorhaunted() {
                return this + "/huantedstate.png";
            }
        }

        //遊戲Boss資料夾
        public static class Boss extends Flow{

            private Boss(Flow flow) {
                super(flow, "/boss");
            }

            public String altarstand() {
                return this + "/altarstand.png";
            }

            public String altarfog() {
                return this + "/altarfog.png";
            }

            public String altarsummon() {
                return this + "/altarsummon.png";
            }

            public String altardie() {
                return this + "/altardie.png";
            }

            public String piratestand() {
                return this + "/piratestand.png";
            }

            public String piratefog() {
                return this + "/piratefog.png";
            }

            public String piratesummon() {
                return this + "/piratesummon.png";
            }

            public String piratedie() {
                return this + "/piratedie.png";
            }

        }

        //遊戲背景資料夾
        public static class Backgrounds extends Flow{

            private Backgrounds(Flow flow) {
                super(flow, "/backgrounds");
            }

            //墓園背景_石頭地板
            public String stoneFloor() {
                return this + "/stoneFloor.png";
            }

            //鬼屋背景_木頭地板
            public String woodFloor() {
                return this + "/woodFloor.jpg";
            }

            //海盜船背景_船地板
            public String deck() {
                return this + "/deck.png";
            }

            //女巫Boss背景_祭壇地板
            public String dark() {
                return this + "/dark.png";
            }

            //海盜Boss背景_船
            public String ship() {
                return this + "/ship.png";
            }

            //教學畫面使用背景
            public String TeachingBackground() {
                return this + "/background.png";
            }

            //登入畫面使用背景
            public String LoggingBackground() {
                return this + "/loggingbackground.png";
            }

            //關於遊戲使用背景
            public String aboutGameBackground() {
                return this + "/aboutGameBackground.png";
            }



        }

        //按鈕資料夾
        public static class Buttons extends Flow {

            private Buttons(Flow flow) {
                super(flow,"/buttons");
            }

            public String button_singleMode() {
                return this + "/button_singleMode.png";
            }

            public String button_multiMode() {
                return this + "/button_multiMode.png";
            }

            public String button_rankingList() {
                return this + "/button_rankingList.png";
            }

            public String button_exit() {
                return this + "/button_exit.png";
            }

            public String button_WASD() {
                return this + "/WASD.png";
            }

            public String button_control() {
                return this + "/button_control.png";
            }

            public String button_shot() {
                return this + "/button_shot.png";
            }

            public String button_useItem() {
                return this + "/button_useitem.png";
            }

            public String mouse() {
                return this + "/mouse.png";
            }

            public String useItem() {
                return this + "/useitem.png";
            }

            public String useItem2() {
                return this + "/useitem2.png";
            }

            public String startGame() {
                return this + "/startGame.png";
            }

            public String button_aboutGame() {
                return this + "/aboutgame_button.png";
            }

            public String button_singleGame() {
                return this + "/single_button.png";
            }

            public String button_multiGame() {
                return this + "/multi_button.png";
            }

            public String button_exitGame() {
                return this + "/exit_button.png";
            }

            public String gameTitle() {
                return this + "/gametitle.png";
            }

            //故事背景
            public String button_story() {
                return this + "/story_button.png";
            }
            //道具說明
            public String button_props() {
                return this + "/props_button.png";
            }
            //團隊介紹
            public String button_team() {
                return this + "/team_button.png";
            }
            //回主選單
            public String button_back() {
                return this + "/back_button.png";
            }

            //登入大廳
            public String multi_loby() {
                return this + "/multi_loby.png";
            }
            //準備區說明
            public String readyDescription() {
                return this + "/readydescription.png";
            }
            //外觀選擇說明
            public String appearanceDescription() {
                return this + "/appearancedescription.png";
            }

        }

        //地圖素材資料夾
        public static class MapMaterial extends Flow {

            private MapMaterial(Flow flow) {
                super(flow, "/mapMaterial");
            }

//            //沙地test
//            public String sandLand() {
//                return this + "/sandLand.png";
//            }
//
//            public String seed() {
//                return this + "/seed.png";
//            }
//
//            public String seedLand() {
//                return this + "/seedLand.png";
//            }

            //墓園地圖素材
            public static class Cemetery extends Flow {

                private Cemetery(Flow flow) {
                    super(flow, "/cemetery");
                }

                //石牆1
                public String stoneWall_1() {
                    return this + "/stoneWall_1.jpg";
                }
                //石牆2
                public String stoneWall_2() {
                    return this + "/stoneWall_2.jpg";
                }
                //棺材
                public String coffin() {
                    return this + "/coffin.png";
                }
                //墓碑
                public String tombStone() {
                    return this + "/tombStone.png";
                }
                //大樹
                public String bigTree() {
                    return this + "/bigTree.png";
                }
                //建築
                public String building() {
                    return this + "/building.png";
                }
                //噴泉
                public String pool() {
                    return this + "/pool.png";
                }
                //牆1
                public String wall_1() {
                    return this + "/wall_1.png";
                }
                //牆2
                public String wall_2() {
                    return this + "/wall_2.png";
                }
                //牆3
                public String wall_3() {
                    return this + "/wall_3.png";
                }
            }

            //鬼屋地圖素材
            public static class HauntedHouse extends Flow {

                private HauntedHouse(Flow flow) {
                    super(flow, "/hauntedHouse");
                }

                //鬼屋牆1
                public String houseWall_1() {
                    return this + "/houseWall_1.jpg";
                }
                //鬼屋牆2
                public String houseWall_2() {
                    return this + "/houseWall_2.jpg";
                }
                //壁爐
                public String firePlace() {
                    return this + "/firePlace.png";
                }
                //床
                public String bed() {
                    return this + "/bed.png";
                }
                //廚房
                public String kitchen() {
                    return this + "/kitchen.png";
                }
                //餐桌
                public String diningTable() {
                    return this + "/diningTable.png";
                }
                //沙發_背向
                public String sofa_back() {
                    return this + "/sofa_back.png";
                }
                //沙發_面右
                public String sofa_left() {
                    return this + "/sofa_left.png";
                }
                //沙發_面左
                public String sofa_right() {
                    return this + "/sofa_right.png";
                }
                //盆栽1
                public String plants_1() {
                    return this + "/plants_1.png";
                }
                //盆栽2
                public String plants_2() {
                    return this + "/plants_2.png";
                }
                //地毯
                public String carpet() {
                    return this + "/carpet.png";
                }
                //椅子
                public String chair() {
                    return this + "/chair.png";
                }
                //蠟燭
                public String candle() {
                    return this + "/candle.png";
                }
                //鋼琴
                public String piano() {
                    return this + "/piano.png";
                }
            }

            //海盜船地圖素材
            public static class PirateShip extends Flow {

                private PirateShip(Flow flow) {
                    super(flow, "/pirateShip");
                }

                //船牆1
                public String shipWall_1() {
                    return this + "/shipWall_1.jpg";
                }
                //船牆2
                public String shipWall_2() {
                    return this + "/shipWall_2.jpg";
                }
                //船長室牆壁
                public String wallDecoration() {
                    return this + "/wallDecoration.png";
                }
                //船長骨骸
                public String captainBone() {
                    return this + "/captainBone.png";
                }
                //儲藏室桌
                public String table() {
                    return this + "/table.png";
                }
                //珠寶桌
                public String table_jewelry() {
                    return this + "/table_jewelry.png";
                }
                //地圖桌
                public String table_map() {
                    return this + "/table_map.png";
                }
                //書桌
                public String table_book() {
                    return this + "/table_book.png";
                }
                //食物桌
                public String table_food() {
                    return this + "/table_food.png";
                }
                //模型船
                public String shipModel() {
                    return this + "/shipModel.png";
                }
                //瓶瓶罐罐
                public String stuff() {
                    return this + "/stuff.png";
                }
                //空瓶
                public String bottle() {
                    return this + "/bottle.png";
                }
                //武器箱
                public String army() {
                    return this + "/army.png";
                }
                //木箱1
                public String woodBox_1() {
                    return this + "/woodBox_1.jpg";
                }
                //木桶1
                public String woodBarrel_1() {
                    return this + "/woodBarrel_1.png";
                }
                //木桶2
                public String woodBarrel_2() {
                    return this + "/woodBarrel_2.png";
                }
                //燈
                public String light() {
                    return this + "/light.png";
                }
            }

            //女巫Boss地圖素材
            public static class Boss_Witch extends Flow {

                private Boss_Witch(Flow flow) {
                    super(flow, "/boss_witch");
                }

                //岩漿1
                public String lava_1() {
                    return this + "/lava_1.jpg";
                }
                //岩漿2
                public String lava_2() {
                    return this + "/lava_2.jpg";
                }
                //岩漿3
                public String lava_3() {
                    return this + "/lava_3.jpg";
                }
                //岩漿4
                public String lava_4() {
                    return this + "/lava_4.jpg";
                }
                //女神雕像
                public String statue_goddess() {
                    return this + "/statue_goddess.png";
                }
                //龍雕像
                public String statue_dragon() {
                    return this + "/statue_dragon.png";
                }
                //龍骨
                public String dragonBone() {
                    return this + "/dragonBone.png";
                }
                //石柱1
                public String column_1() {
                    return this + "/column_1.png";
                }
                //石柱2
                public String column_2() {
                    return this + "/column_2.png";
                }
                //火
                public String fire() {
                    return this + "/fire.png";
                }
            }

            //海盜Boss地圖素材
            public static class Boss_Pirate extends Flow {

                private Boss_Pirate(Flow flow) {
                    super(flow, "/boss_pirate");
                }

                //鐵絲地板
                public String ironWire() {
                    return this + "/ironWire.jpg";
                }
                //透明遮擋
                public String transparent() {
                    return this + "/transparent.png";
                }
                //透明遮擋1
                public String transparent_1() {
                    return this + "/transparent_1.png";
                }
                //透明遮擋2
                public String transparent_2() {
                    return this + "/transparent_2.png";
                }
                //小船
                public String littleShip() {
                    return this + "/littleShip.png";
                }
                //大砲_朝下
                public String cannon_down() {
                    return this + "/cannon_down.png";
                }
                //大砲_朝上
                public String cannon_up() {
                    return this + "/cannon_up.png";
                }
                //船舵
                public String rudder() {
                    return this + "/rudder.png";
                }
                //海盜旗
                public String pirateFlag() {
                    return this + "/pirateFlag.png";
                }
                //木箱2
                public String woodBox_2() {
                    return this + "/woodBox_2.png";
                }
                //骨頭
                public String bone() {
                    return this + "/bone.png";
                }
                //沙袋
                public String sandBag() {
                    return this + "/sandBag.png";
                }
            }

            //取得墓園地圖素材資料
            public Cemetery cemetery() {
                return new Cemetery(this);
            }

            //取得鬼屋地圖素材資料
            public HauntedHouse hauntedHouse() {
                return new HauntedHouse(this);
            }

            //取得海盜船地圖素材資料
            public PirateShip pirateShip() {
                return new PirateShip(this);
            }

            //取得女巫Boss地圖素材資料
            public Boss_Witch boss_witch() {
                return new Boss_Witch(this);
            }

            //取得海盜Boss地圖素材資料
            public Boss_Pirate boss_pirate() {
                return new Boss_Pirate(this);
            }
        }

        //怪物角色資料夾
        public static class Monsters extends Flow {

            private Monsters(Flow flow) {
                super(flow, "/monsters");
            }

            public String newborneffect() {
                return this + "/newBornEffect.png";
            }

            //墓園怪物資料夾
            public static class Cemetery extends Flow {

                private Cemetery(Flow flow) {
                    super(flow, "/cemetery");
                }

                //木乃伊狗
                public String mummyDog() {
                    return this + "/mummyDog.png";
                }

                //殭屍
                public String zombie() {
                    return this + "/zombie.png";
                }

                //大幽靈
                public String masterGhost() {
                    return this + "/masterGhost.png";
                }
            }

            //鬼屋怪物資料夾
            public static class HauntedHouse extends Flow {

                private HauntedHouse(Flow flow) {
                    super(flow, "/hauntedHouse");
                }

                //哀號鬼
                public String wailingGhost() {
                    return this + "/wailingGhost.png";
                }

                //書鬼
                public String bookGhost() {
                    return this + "/bookGhost.png";
                }

                //泰迪熊
                public String soulTeddy() {
                    return this + "/soulTeddy.png";
                }
            }

            //海盜船怪物資料夾
            public static class PirateShip extends Flow {

                private PirateShip(Flow flow) {
                    super(flow, "/pirateShip");
                }

                //骨頭魚
                public String boneFish() {
                    return this + "/boneFish.png";
                }

                //懷錶鬼
                public String phantomWatch() {
                    return this + "/phantomWatch.png";
                }

                //骨頭士兵
                public String skeleton() {
                    return this + "/skeleton.png";
                }
            }

            //取得墓園資料
            public Cemetery cemetery() {
                return new Cemetery(this);
            }

            //取得鬼屋資料
            public HauntedHouse hauntedHouse() {
                return new HauntedHouse(this);
            }

            //取得海盜船資料
            public PirateShip pirateShip() {
                return new PirateShip(this);
            }
        }

        //遊戲輔助物件資料夾
        public static class Objects extends Flow{

            public Objects(Flow flow) {
                super(flow, "/objs");
            }

            public String doorOpenEffect() {
                return this + "/dooropen.png";
            }

            //道具圖片

            public String propsRecoveryPotion() {
                return this + "/recoverypotion.png";
            }

            public String propsRecoveryState() {
                return this + "/recoverystate.png";
            }

            public String propsRecoveryBullet() {
                return this + "/recoverybullet.png";
            }

            public String propsEmpowerBullet() {
                return this + "/empowerbullet.png";
            }

            public String propsFlare() {
                return this + "/flare.png";
            }

            public String propsLandmine() {
                return this + "/landmine.png";
            }

            public String propsLandmineExplode() {
                return this + "/landmineexplode.png";
            }

            public String itemFrame() {
                return this + "/frame.png";
            }

            public String itemContent() {
                return this + "/content.png";
            }

            public String fireBall() {
                return this + "/fireBall.png";
            }

            public String poisonFog() {
                return this + "/fog.png";
            }

            public String devilEye() {return this + "/devileye.png";}


            //子彈圖片&特效

            public String bulletDefault() {
                return this + "/defaultbullet.png";
            }

            public String bulletUpgrade() {
                return this + "/upgradebullet.png";
            }

            public String bulletSuper() {
                return this + "/superbullet.png";
            }

            public String bulletExplodeEffect() {
                return this + "/bulletexplode.png";
            }

            //使用道具特效

            public String effectBulletEmpower() {
                return this + "/bulletempower.png";
            }

            public String effectStateRecovery() {
                return this + "/staterecovery.png";
            }

            public String effectBulletRecovery() {
                return this + "/bulletrecovery.png";
            }

            public String effectHPRecovery() {
                return this + "/hprecovery.png";
            }

            public String effectFlare() {
                return this + "/flarelaunch.png";
            }

            //增加子彈
            public String addBullet() {
                return this + "/addBullet.png";
            }
            //增加HP
            public String addHP() {
                return this + "/addHP.png";
            }
            //增加攻擊
            public String addPower() {
                return this + "/addPower.png";
            }

            //道具名字
            public String name_recoverypotion() {
                return this + "/name_recoverypotion.png";
            }
            public String name_recoverystate() {
                return this + "/name_recoverystate.png";
            }
            public String name_recoverybullet() {
                return this + "/name_recoverybullet.png";
            }
            public String name_empowerbullet() {
                return this + "/name_empowerbullet.png";
            }
            public String name_landmine() {
                return this + "/name_landmine.png";
            }
            public String name_flare() {
                return this + "/name_flare.png";
            }

        }

        //letters資料夾
        public static class Letters extends Flow{

            public Letters(Flow flow) {
                super(flow, "/letters");
            }

            public String letterA() {
                return this + "/A.png";
            }
            public String letterB() {
                return this + "/B.png";
            }
            public String letterC() {
                return this + "/C.png";
            }
            public String letterD() {
                return this + "/D.png";
            }
            public String letterE() {
                return this + "/E.png";
            }
            public String letterF() {
                return this + "/F.png";
            }
            public String letterG() {
                return this + "/G.png";
            }
            public String letterH() {
                return this + "/H.png";
            }
            public String letterI() {
                return this + "/I.png";
            }
            public String letterJ() {
                return this + "/J.png";
            }
            public String letterK() {
                return this + "/K.png";
            }
            public String letterL() {
                return this + "/L.png";
            }
            public String letterM() {
                return this + "/M.png";
            }
            public String letterN() {
                return this + "/N.png";
            }
            public String letterO() {
                return this + "/O.png";
            }
            public String letterP() {
                return this + "/P.png";
            }
            public String letterQ() {
                return this + "/Q.png";
            }
            public String letterR() {
                return this + "/R.png";
            }
            public String letterS() {
                return this + "/S.png";
            }
            public String letterT() {
                return this + "/T.png";
            }
            public String letterU() {
                return this + "/U.png";
            }
            public String letterV() {
                return this + "/V.png";
            }
            public String letterW() {
                return this + "/W.png";
            }
            public String letterX() {
                return this + "/X.png";
            }
            public String letterY() {
                return this + "/Y.png";
            }
            public String letterZ() {
                return this + "/Z.png";
            }

        }

        //numbers資料夾
        public static class Numbers extends Flow{

            public Numbers(Flow flow) {
                super(flow, "/numbers");
            }

            public String number0() {
                return this + "/0.png";
            }
            public String number1() {
                return this + "/1.png";
            }
            public String number2() {
                return this + "/2.png";
            }
            public String number3() {
                return this + "/3.png";
            }
            public String number4() {
                return this + "/4.png";
            }
            public String number5() {
                return this + "/5.png";
            }
            public String number6() {
                return this + "/6.png";
            }
            public String number7() {
                return this + "/7.png";
            }
            public String number8() {
                return this + "/8.png";
            }
            public String number9() {
                return this + "/9.png";
            }
            public String numbers() {
                return this + "/numbers.png";
            }
            public String skeleton() {
                return this + "/skeleton.png";
            }
            public String skeleton2() {
                return this + "/skeleton2.png";
            }
            public String heart() {
                return this + "/heart.png";
            }
            public String bullet() {
                return this + "/bullet.png";
            }
            public String escapefail() {
                return this + "/escapefail.png";
            }
            public String escapesuccessed() {
                return this + "/escapesuccessed.png";
            }
            public String finale() {
                return this + "/finale.png";
            }
            public String allNumbers() {
                return this + "/allnumbers.png";
            }
            public String slash() {
                return this + "/slash.png";
            }


        }

        //取得關於遊戲
        public AboutGame aboutGame() {
            return new AboutGame(this);
        }

        //取得載入動畫
        public Loading loading() {
            return new Loading(this);
        }

        //取得遊戲角色
        public Actors actors() {
            return new Actors(this);
        }

        //取得怪物
        public Monsters monsters() {
            return new Monsters(this);
        }

        //取得遊戲Boss
        public Boss boss() {
            return new Boss(this);
        }

        //取得遊戲背景
        public Backgrounds backgrounds() {
            return new Backgrounds(this);
        }

        //取得按鈕
        public Buttons buttons() {
            return new Buttons(this);
        }

        //取得地圖
        public MapMaterial mapMaterial() {
            return new MapMaterial(this);
        }

        //取得遊戲輔助物件
        public Objects objects() {
            return new Objects(this);
        }

        //取得字母
        public Letters letters() {
            return new Letters(this);
        }

        //取得數字
        public Numbers numbers() {
            return new Numbers(this);
        }

    }

    //音效資料夾
    public static class Sounds extends Flow {

        private Sounds() {
            super(new Resources(), "/sounds");
        }

        public String alarm() {
            return this + "/alarm01.wac";
        }

        public String bomb() {
            return this + "/bomb.wav";
        }

        public String soloSceneBackground() {
            return this + "/backgroundsoundtrack.wav";
        }

        public String doorOpened() {
            return this + "/dooropened.wav";
        }

        public String girlGetHurt() {
            return this + "/girlgethurt.wav";
        }

        public String girlDead() {
            return this + "/girldie.wav";
        }

        public String BOOM() {return this + "/BOOM.wav";}

        public String powerUp1() {return this + "/powerup1.wav";}

        public String powerUp2() {return this + "/powerup2.wav";}

        public String recoverWater() {return this + "/recoverwater.wav";}

        public String reloadBullet() {return this + "/reloadbullet.wav";}

        public String bossSceneBackground() {return this + "/battlemusic.wav";}

        public String shotting() {return this + "/shotting.wav";}

        public String teachingBackground() {return this + "/teachingbackground.wav";}

        public String setBomb() {return this + "/setbomb.wav";}

        public String bombBoom() {return this + "/bombboom.wav";}

        public String bloodHand() {return this + "/bloodhandtest.wav";}

        public String demonlaugh() {return this + "/demonlaugh.wav";}

        public String loadingsound() {return this + "/loadingsound.wav";}

        public String gethurryup() {return this + "/gethurryup.wav";}

        public String loginbackground() {return this + "/logingbackgroundmusic.wav";}

    }

    //地圖資料夾
    public static class Map extends Flow {

        private Map() {
            super(new Resources(), "/map");
        }

//        //沙地test
//        public String bmp_seedMap() {
//            return this + "/seedMap.bmp";
//        }
//        public String txt_seedMap() {
//            return this + "/seedMap.txt";
//        }

        //墓園地圖
        public String bmp_cemeteryMap() {
            return this + "/cemetery.bmp";
        }
        public String txt_cemeteryMap() {
            return this + "/cemetery.txt";
        }

        //鬼屋地圖
        public String bmp_hauntedHouseMap() {
            return this + "/hauntedHouse.bmp";
        }
        public String txt_hauntedHouseMap() {
            return this + "/hauntedHouse.txt";
        }

        //鬼屋地圖
        public String bmp_pirateShipMap() {
            return this + "/pirateShip.bmp";
        }
        public String txt_pirateShipMap() {
            return this + "/pirateShip.txt";
        }

        //女巫Boss地圖
        public String bmp_bossWitchMap() {
            return this + "/boss_witch.bmp";
        }
        public String txt_bossWitchMap() {
            return this + "/boss_witch.txt";
        }

        //海盜Boss地圖
        public String bmp_bossPirateMap() {
            return this + "/boss_pirate.bmp";
        }
        public String txt_bossPirateMap() {
            return this + "/boss_pirate.txt";
        }
    }


    /** 取得資料夾 */
    public Imgs img() {
        return new Imgs();
    }

    public Sounds sounds() {
        return new Sounds();
    }

    public Map map() {
        return new Map();
    }

}
