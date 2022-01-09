package com.WitchHunter.Map;

import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.GameObject.WitchHunterObject.Map.MapObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.WitchHunter.utils.Global.*;


public class MapLoader {
    private final ArrayList<int[][]> mapArr;// bmp陣列
    private final ArrayList<String[]> txtArr;// txt檔陣列

    public MapLoader(final String MapPath, final String txtPath) throws IOException {
        this.mapArr = new ReadBmp().readBmp(MapPath);// 讀取bmp檔
        this.txtArr = new ReadFile().readFile(txtPath);// 讀取txt檔
    }

//    /**
//     * 創建地圖物件
//     * @param gameObjectName 地圖物件名
//     * @param gameObjectSize 地圖物件大小
//     * @param mapInfo 地圖物件資訊
//     * @param compare 比較的物件
//     * @return 地圖物件陣列
//     */
//    public ArrayList<GameObject> createObjectArray(final String gameObjectName, final int gameObjectSize, final ArrayList<MapInfo> mapInfo, final CompareClass compare) {
//        final ArrayList<GameObject> mapObjects = new ArrayList<>();
//        mapInfo.forEach((mapInformation) -> {
//            GameObject tmpObject = compare.compareClassName(gameObjectName, mapInformation.getName(), mapInformation, gameObjectSize);
//            if (tmpObject != null) {
//                mapObjects.add(tmpObject);
//            }
//        });
//        return mapObjects;
//    }

    /**
     * 創建地圖物件
     * @param mapInfos 地圖物件資訊
     * @return 地圖物件陣列
     */
    public ArrayList<MapObject> createObjectArray(ArrayList<MapInfo> mapInfos) {
        // 創建陣列儲存地圖物件
        ArrayList<MapObject> mapObjects = new ArrayList<>();

        // 根據地圖資訊創建地圖物件
        for (MapInfo mapInfo : mapInfos) {
            // test
            if (!mapInfo.getName().equals("sandLand")) {
                mapObjects.add(new MapObject(
                        mapInfo.getX() * MAP_OBJECT_WIDTH,
                        mapInfo.getY() * MAP_OBJECT_HEIGHT,
                        mapInfo.getWidth() * MAP_OBJECT_WIDTH,
                        mapInfo.getHeight() * MAP_OBJECT_HEIGHT,
                        mapInfo.getName()));
            }
        }
        return mapObjects;
    }

    /**
     * 地圖資料(整合bmp & txt)
     * @return 地圖資料陣列
     */
    public ArrayList<MapInfo> combineInfo() {  //整合需要資料   類名  x座標  y座標 尺寸(e.g. 1 * 1)
        ArrayList<MapInfo> result = new ArrayList<>();
        for (int[][] bmp : this.mapArr) {
            for (String[] txt : this.txtArr) {
                // 偵測色號是否符合
                if (bmp[1][0] == Integer.parseInt(txt[1])) {
                    // 建立對應地圖圖片資訊
                    MapInfo newMap = new MapInfo(txt[0],
                            bmp[0][0],
                            bmp[0][1],
                            Integer.parseInt(txt[2]),
                            Integer.parseInt(txt[3]));
                    result.add(newMap);
                }
            }
        }
        return result;
    }

    public ArrayList<int[][]> getMapArr() {
        return this.mapArr;
    }

    public ArrayList getTxtArr() {
        return this.txtArr;
    }

    public interface CompareClass {
        GameObject compareClassName(String gameObject, String name, MapInfo mapInfo, int MapObjectSize);
    }
}
