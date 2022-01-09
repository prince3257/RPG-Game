package com.WitchHunter.GameObject.WitchHunterObject.Map;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.utils.Path;
import java.awt.*;

public class MapObject extends GameObject {

    @Override
    public void update() {

    }

    /**
     * 地圖物件類型
     */
    public enum MapObjectType {
//        SANDLAND(new Path().img().mapMaterial().sandLand()),// 沙地
//        SEED(new Path().img().mapMaterial().seed()),// 雜草
//        SEEDLAND(new Path().img().mapMaterial().seedLand());// 草地

        //墓園地圖物件
        STONEWALL_1(new Path().img().mapMaterial().cemetery().stoneWall_1()),
        STONEWALL_2(new Path().img().mapMaterial().cemetery().stoneWall_2()),
        COFFIN(new Path().img().mapMaterial().cemetery().coffin()),
        TOMBSTONE(new Path().img().mapMaterial().cemetery().tombStone()),
        BIG_TREE(new Path().img().mapMaterial().cemetery().bigTree()),
        BUILDING(new Path().img().mapMaterial().cemetery().building()),
        POOL(new Path().img().mapMaterial().cemetery().pool()),
        WALL_1(new Path().img().mapMaterial().cemetery().wall_1()),
        WALL_2(new Path().img().mapMaterial().cemetery().wall_2()),
        WALL_3(new Path().img().mapMaterial().cemetery().wall_3()),

        //鬼屋地圖物件
        HOUSE_WALL_1(new Path().img().mapMaterial().hauntedHouse().houseWall_1()),
        HOUSE_WALL_2(new Path().img().mapMaterial().hauntedHouse().houseWall_2()),
        FIREPLACE(new Path().img().mapMaterial().hauntedHouse().firePlace()),
        BED(new Path().img().mapMaterial().hauntedHouse().bed()),
        KITCHEN(new Path().img().mapMaterial().hauntedHouse().kitchen()),
        DINING_TABLE(new Path().img().mapMaterial().hauntedHouse().diningTable()),
        SOFA_BACK(new Path().img().mapMaterial().hauntedHouse().sofa_back()),
        SOFA_LEFT(new Path().img().mapMaterial().hauntedHouse().sofa_left()),
        SOFA_RIGHT(new Path().img().mapMaterial().hauntedHouse().sofa_right()),
        PLANTS_1(new Path().img().mapMaterial().hauntedHouse().plants_1()),
        PLANTS_2(new Path().img().mapMaterial().hauntedHouse().plants_2()),
        CARPET(new Path().img().mapMaterial().hauntedHouse().carpet()),
        CHAIR(new Path().img().mapMaterial().hauntedHouse().chair()),
        CANDLE(new Path().img().mapMaterial().hauntedHouse().candle()),
        PIANO(new Path().img().mapMaterial().hauntedHouse().piano()),

        //海盜船地圖物件
        SHIP_WALL_1(new Path().img().mapMaterial().pirateShip().shipWall_1()),
        SHIP_WALL_2(new Path().img().mapMaterial().pirateShip().shipWall_2()),
        WALL_DECORATION(new Path().img().mapMaterial().pirateShip().wallDecoration()),
        CAPTAIN_BONE(new Path().img().mapMaterial().pirateShip().captainBone()),
        TABLE(new Path().img().mapMaterial().pirateShip().table()),
        TABLE_JEWELRY(new Path().img().mapMaterial().pirateShip().table_jewelry()),
        TABLE_MAP(new Path().img().mapMaterial().pirateShip().table_map()),
        TABLE_BOOK(new Path().img().mapMaterial().pirateShip().table_book()),
        TABLE_FOOD(new Path().img().mapMaterial().pirateShip().table_food()),
        SHIP_MODEL(new Path().img().mapMaterial().pirateShip().shipModel()),
        STUFF(new Path().img().mapMaterial().pirateShip().stuff()),
        BOTTLE(new Path().img().mapMaterial().pirateShip().bottle()),
        ARMY(new Path().img().mapMaterial().pirateShip().army()),
        WOOD_BOX_1(new Path().img().mapMaterial().pirateShip().woodBox_1()),
        WOOD_BARREL_1(new Path().img().mapMaterial().pirateShip().woodBarrel_1()),
        WOOD_BARREL_2(new Path().img().mapMaterial().pirateShip().woodBarrel_2()),
        LIGHT(new Path().img().mapMaterial().pirateShip().light()),

        //女巫Boss地圖物件
        LAVA_1(new Path().img().mapMaterial().boss_witch().lava_1()),
        LAVA_2(new Path().img().mapMaterial().boss_witch().lava_2()),
        LAVA_3(new Path().img().mapMaterial().boss_witch().lava_3()),
        LAVA_4(new Path().img().mapMaterial().boss_witch().lava_4()),
        STATUE_GODDESS(new Path().img().mapMaterial().boss_witch().statue_goddess()),
        STATUE_DRAGON(new Path().img().mapMaterial().boss_witch().statue_dragon()),
        DRAGON_BONE(new Path().img().mapMaterial().boss_witch().dragonBone()),
        COLUMN_1(new Path().img().mapMaterial().boss_witch().column_1()),
        COLUMN_2(new Path().img().mapMaterial().boss_witch().column_2()),
        FIRE(new Path().img().mapMaterial().boss_witch().fire()),

        //海盜Boss地圖物件
        IRON_WIRE(new Path().img().mapMaterial().boss_pirate().ironWire()),
        TRANSPARENT(new Path().img().mapMaterial().boss_pirate().transparent()),
        TRANSPARENT_1(new Path().img().mapMaterial().boss_pirate().transparent_1()),
        TRANSPARENT_2(new Path().img().mapMaterial().boss_pirate().transparent_2()),
        LITTLE_SHIP(new Path().img().mapMaterial().boss_pirate().littleShip()),
        CANNON_DOWN(new Path().img().mapMaterial().boss_pirate().cannon_down()),
        CANNON_UP(new Path().img().mapMaterial().boss_pirate().cannon_up()),
        RUDDER(new Path().img().mapMaterial().boss_pirate().rudder()),
        PIRATE_FLAG(new Path().img().mapMaterial().boss_pirate().pirateFlag()),
        WOOD_BOX_2(new Path().img().mapMaterial().boss_pirate().woodBox_2()),
        BONE(new Path().img().mapMaterial().boss_pirate().bone()),
        SANDBAG(new Path().img().mapMaterial().boss_pirate().sandBag());

        private final String path;// 圖形路徑

        /**
         * 建構子
         * @param path 建立圖形路徑
         */
        MapObjectType(String path) {
            this.path = path;
        }
    }

    private MapObjectType type;// 地圖物件類型
    private final Image img;// 地圖圖片

    public MapObject(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        switch (type) {
//            case "sandLand":
//                this.type = MapObjectType.SANDLAND;
//                break;
//            case "seed":
//                this.type = MapObjectType.SEED;
//                break;
//            default:
//                this.type = MapObjectType.SEEDLAND;
            //墓園物件
            case "stoneWall_1":
                this.type = MapObjectType.STONEWALL_1;
                break;
            case "stoneWall_2":
                this.type = MapObjectType.STONEWALL_2;
                break;
            case "coffin":
                this.type = MapObjectType.COFFIN;
                break;
            case "tombStone":
                this.type = MapObjectType.TOMBSTONE;
                break;
            case "bigTree":
                this.type = MapObjectType.BIG_TREE;
                break;
            case "building":
                this.type = MapObjectType.BUILDING;
                break;
            case "pool":
                this.type = MapObjectType.POOL;
                break;
            case "wall_1":
                this.type = MapObjectType.WALL_1;
                break;
            case "wall_2":
                this.type = MapObjectType.WALL_2;
                break;
            case "wall_3":
                this.type = MapObjectType.WALL_3;
                break;

            //鬼屋物件
            case "houseWall_1":
                this.type = MapObjectType.HOUSE_WALL_1;
                break;
            case "houseWall_2":
                this.type = MapObjectType.HOUSE_WALL_2;
                break;
            case "firePlace":
                this.type = MapObjectType.FIREPLACE;
                break;
            case "bed":
                this.type = MapObjectType.BED;
                break;
            case "kitchen":
                this.type = MapObjectType.KITCHEN;
                break;
            case "diningTable":
                this.type = MapObjectType.DINING_TABLE;
                break;
            case "sofa_back":
                this.type = MapObjectType.SOFA_BACK;
                break;
            case "sofa_left":
                this.type = MapObjectType.SOFA_LEFT;
                break;
            case "sofa_right":
                this.type = MapObjectType.SOFA_RIGHT;
                break;
            case "plants_1":
                this.type = MapObjectType.PLANTS_1;
                break;
            case "plants_2":
                this.type = MapObjectType.PLANTS_2;
                break;
            case "carpet":
                this.type = MapObjectType.CARPET;
                break;
            case "chair":
                this.type = MapObjectType.CHAIR;
                break;
            case "candle":
                this.type = MapObjectType.CANDLE;
                break;
            case "piano":
                this.type = MapObjectType.PIANO;
                break;

            //海盜船物件
            case "shipWall_1":
                this.type = MapObjectType.SHIP_WALL_1;
                break;
            case "shipWall_2":
                this.type = MapObjectType.SHIP_WALL_2;
                break;
            case "wallDecoration":
                this.type = MapObjectType.WALL_DECORATION;
                break;
            case "captainBone":
                this.type = MapObjectType.CAPTAIN_BONE;
                break;
            case "table":
                this.type = MapObjectType.TABLE;
                break;
            case "table_jewelry":
                this.type = MapObjectType.TABLE_JEWELRY;
                break;
            case "table_map":
                this.type = MapObjectType.TABLE_MAP;
                break;
            case "table_book":
                this.type = MapObjectType.TABLE_BOOK;
                break;
            case "table_food":
                this.type = MapObjectType.TABLE_FOOD;
                break;
            case "shipModel":
                this.type = MapObjectType.SHIP_MODEL;
                break;
            case "stuff":
                this.type = MapObjectType.STUFF;
                break;
            case "bottle":
                this.type = MapObjectType.BOTTLE;
                break;
            case "army":
                this.type = MapObjectType.ARMY;
                break;
            case "woodBox_1":
                this.type = MapObjectType.WOOD_BOX_1;
                break;
            case "woodBarrel_1":
                this.type = MapObjectType.WOOD_BARREL_1;
                break;
            case "woodBarrel_2":
                this.type = MapObjectType.WOOD_BARREL_2;
                break;
            case "light":
                this.type = MapObjectType.LIGHT;
                break;

            //女巫Boss物件
            case "lava_1":
                this.type = MapObjectType.LAVA_1;
                break;
            case "lava_2":
                this.type = MapObjectType.LAVA_2;
                break;
            case "lava_3":
                this.type = MapObjectType.LAVA_3;
                break;
            case "lava_4":
                this.type = MapObjectType.LAVA_4;
                break;
            case "statue_goddess":
                this.type = MapObjectType.STATUE_GODDESS;
                break;
            case "statue_dragon":
                this.type = MapObjectType.STATUE_DRAGON;
                break;
            case "dragonBone":
                this.type = MapObjectType.DRAGON_BONE;
                break;
            case "column_1":
                this.type = MapObjectType.COLUMN_1;
                break;
            case "column_2":
                this.type = MapObjectType.COLUMN_2;
                break;
            case "fire":
                this.type = MapObjectType.FIRE;
                break;

            //海盜Boss物件
            case "ironWire":
                this.type = MapObjectType.IRON_WIRE;
                break;
            case "transparent":
                this.type = MapObjectType.TRANSPARENT;
                break;
            case "transparent_1":
                this.type = MapObjectType.TRANSPARENT_1;
                break;
            case "transparent_2":
                this.type = MapObjectType.TRANSPARENT_2;
                break;
            case "littleShip":
                this.type = MapObjectType.LITTLE_SHIP;
                break;
            case "cannon_down":
                this.type = MapObjectType.CANNON_DOWN;
                break;
            case "cannon_up":
                this.type = MapObjectType.CANNON_UP;
                break;
            case "rudder":
                this.type = MapObjectType.RUDDER;
                break;
            case "pirateFlag":
                this.type = MapObjectType.PIRATE_FLAG;
                break;
            case "woodBox_2":
                this.type = MapObjectType.WOOD_BOX_2;
                break;
            case "bone":
                this.type = MapObjectType.BONE;
                break;
            case "sandBag":
                this.type = MapObjectType.SANDBAG;
                break;
        }

        // 以地圖類型抓取圖片
        img = SceneController.instance().irc().tryGetImage(this.type.path);
    }

    /**
     * 取得地圖物件類型
     * @return 地圖物件類型
     */
    public MapObjectType getType() {
        return type;
    }

    /**
     * 畫出地圖物件(每秒畫FRAME_LIMIT次)
     * @param g 繪圖套件
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }
}
