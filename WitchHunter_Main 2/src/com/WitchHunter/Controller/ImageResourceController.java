package com.WitchHunter.Controller;

import com.WitchHunter.utils.Global;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/** 單例模式Singleton -> 先把這個類別想成設計圖*/
public class ImageResourceController {

    private static class KeyPair {
        private String path;
        private Image img;

        public KeyPair(String path, Image img) {
            this.path = path;
            this.img = img;
        }
    }

    //以下為舊版本 -> 使用單例 -> 但在場景交替釋放時會有問題
//    //單例 -> 這個就是之後會共同存取的單一實體
//    private static ImageResourceController irc;
//
//    //內容 -> 建構時同步建構內容
//    private ImageResourceController() {
//        imgPairs = new ArrayList<>();
//    }
//
//    public static ImageResourceController instance() {
//        //可以取到同一個irc
//        if(irc == null) {
//            irc = new ImageResourceController();
//        }
//        return irc;
//    }

    private ArrayList<KeyPair> imgPairs; //由於irc是單一且唯一的實體 -> 因此依附在irc內部的imgPairs就會跟著有共同存取的效果

    public ImageResourceController() {
        imgPairs = new ArrayList<>();
    }

    //外部使用時 -> 會先走上面的instance() -> 在由return的irc去使用這個function -> 再吐出Image
    public Image tryGetImage(String path) {
        KeyPair pair = findKeyPair(path);
        if(pair == null) {
            return addImage(path);
        }
        return pair.img;
    }

    public void clear() {
        imgPairs.clear();
    }

    private Image addImage(String path) {
        try {
            if(Global.IS_DEBUG) {
                System.out.println("Loading image form: "+path);
            }
            Image image = ImageIO.read(getClass().getResource(path));
            imgPairs.add(new KeyPair(path, image));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private KeyPair findKeyPair(String path) {
        for(int i = 0; i<imgPairs.size();i++) {
            KeyPair pair = imgPairs.get(i);
            if(pair.path.equals(path)) {
                return pair;
            }
        }
        return null;
    }


}
