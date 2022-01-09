package com.WitchHunter.utils.Cameras;
import com.WitchHunter.GameObject.Rect;
import com.WitchHunter.GameObject.GameObject;


import java.awt.*;

public class SmallMap {

    private final double zoomX; // 小地圖的X縮放率
    private final double zoomY; // 小地圖的Y縮放率

    public SmallMap(Double inputZoomX, Double inputZoomY) {
        this.zoomX = inputZoomX;
        this.zoomY = inputZoomY;
    }


    /** 將目標物件換成方格，可以自訂顏色跟大小 */
    public void paint(Graphics g, GameObject target, Color c, int width, int height) {
        Rect targetPainter = target.painter();
        g.setColor(c);
        g.fillRect(targetPainter.left(),
                targetPainter.top(),
                width,
                height);
    }


    /** 將目標物件用圖片代替，可以自訂大小 */
    public void paint(Graphics g, GameObject target, Image img, int width, int height){
        Rect targetPainter = target.painter();
        g.setColor(Color.RED);
        g.drawImage(img, targetPainter.left(), targetPainter.top(), width, height, null);
    }

    /** 開啟小地圖 */
    public void start(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(zoomX, zoomY);  // 縮放畫布
    }

}

