package com.WitchHunter.Map;

import com.WitchHunter.GameObject.WitchHunterObject.Map.MapObject;
import com.WitchHunter.utils.Path;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    public enum Type {
        //一般地圖
        CEMETERY(new Path().img().backgrounds().stoneFloor()),
        HAUNTED_HOUSE(new Path().img().backgrounds().woodFloor()),
        PIRATE_SHIP(new Path().img().backgrounds().deck()),

        //魔王地圖
        BOSS_WITCH(new Path().img().backgrounds().dark()),
        BOSS_PIRATE(new Path().img().backgrounds().ship());

        private final String path;

        Type(String path) {
            this.path = path;
        }

        public String path() {
            return path;
        }
    }

    private static MapLoader mapLoader;// 地圖載入器
    private static ArrayList<MapObject> mapObjects;
    private final Type type;

    /**
     * 建構子設為private，強迫使用Builder創建
     */
    private Map(Type type) {
        this.type = type;
    }

    /**
     * 使用Builder創建不同地圖
     */
    public static class Builder {
//        /**
//         * 創建草地地圖
//         * @return 草地地圖
//         */
//        public Builder setSeedMap() {
//            try {
//                mapLoader = new MapLoader(new Path().map().bmp_seedMap(), new Path().map().txt_seedMap());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return this;
//        }

        /**
         * 創建墓園地圖
         * @return 墓園地圖
         */
        public Builder setCemeteryMap() {
            try {
                mapLoader = new MapLoader(new Path().map().bmp_cemeteryMap(), new Path().map().txt_cemeteryMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 創建鬼屋地圖
         * @return 鬼屋地圖
         */
        public Builder setHauntedHouseMap() {
            try {
                mapLoader = new MapLoader(new Path().map().bmp_hauntedHouseMap(), new Path().map().txt_hauntedHouseMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 創建海盜船地圖
         * @return 海盜船地圖
         */
        public Builder setPirateShipMap() {
            try {
                mapLoader = new MapLoader(new Path().map().bmp_pirateShipMap(), new Path().map().txt_pirateShipMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 創建女巫Boss地圖
         * @return 女巫Boss地圖
         */
        public Builder setBossWitchMap() {
            try {
                mapLoader = new MapLoader(new Path().map().bmp_bossWitchMap(), new Path().map().txt_bossWitchMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 創建海盜Boss地圖
         * @return 海盜Boss地圖
         */
        public Builder setBossPirateMap() {
            try {
                mapLoader = new MapLoader(new Path().map().bmp_bossPirateMap(), new Path().map().txt_bossPirateMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 建立地圖
         * @return 地圖
         */
        public Map build(Type type) {
            Map map = new Map(type);
            // 整合地圖物件資訊
            ArrayList<MapInfo> mapInfos = mapLoader.combineInfo();
            // 根據資訊創建地圖物件
            mapObjects = mapLoader.createObjectArray(mapInfos);
            return map;
        }
    }

    /**
     * 取得地圖物件陣列
     * @return 地圖物件陣列
     */
    public ArrayList<MapObject> getMapObjects() {
        return mapObjects;
    }
    /**
     * 取得地圖類型
     * @return 地圖類型
     */
    public Type type() {
        return type;
    }

    /**
     * 畫出地圖(每秒畫FRAME_LIMIT次)
     * @param g 繪圖套件
     */
    public void paintMap(Graphics g) {
        for (MapObject mapObject : mapObjects) {
            mapObject.paintComponent(g);
        }
    }
}
