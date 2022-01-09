package com.WitchHunter.Map;

public class MapInfo {
    /**
     * 圖片資訊
     */
    private final String name;// 圖片名
    private final int x;// 圖片x軸位置
    private final int y;// 圖片y軸位置
    private final int width;// 圖片寬
    private final int height;// 圖片高

    public MapInfo(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
