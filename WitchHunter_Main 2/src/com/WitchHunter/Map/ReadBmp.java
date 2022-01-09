package com.WitchHunter.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadBmp {
    /**
     * 讀取bmp檔
     * @param path bmp檔案路徑
     * @return 讀取完成之bmp圖片陣列
     */
    public  ArrayList<int[][]> readBmp(String path) {
        ArrayList<int[][]> rgbArr = new ArrayList<>();// 色塊二維陣列
        BufferedImage bi = null;// 圖片內容

        try {
            bi = ImageIO.read(getClass().getResource(path));// 讀取圖片
        } catch (IOException ex) {
            Logger.getLogger(ReadBmp.class.getName()).log(Level.SEVERE, null, ex);
        }

        int width = bi.getWidth();// 圖片寬
        int height = bi.getHeight();// 圖片高
        int minX = bi.getMinX();// 圖片最小X座標(0)
        int minY = bi.getMinY();// 圖片最小Y座標(0)

        // X軸由0跑到圖片寬
        for (int x = minX; x < width; x++) {
            // Y軸由0跑到圖片高
            for (int y = minY; y < height; y++) {
                int[][] rgbContent = new int[2][];// 圖片顏色內容
                int pixel = bi.getRGB(x, y);// 每像素圖片顏色
                rgbContent[0] = new int[]{x, y};// 座標位置
                rgbContent[1] = new int[]{pixel};// 座標顏色
                rgbArr.add(rgbContent);// 將圖片內容增加至色塊陣列
            }
        }
        return rgbArr;
    }
}
