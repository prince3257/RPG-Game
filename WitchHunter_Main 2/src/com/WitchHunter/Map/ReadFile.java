package com.WitchHunter.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ReadFile {
    /**
     * 讀取txt檔
     * @param path txt檔案路徑
     * @return 讀取完成之txt字串陣列
     * @throws IOException 輸入/輸出異常
     */
    public ArrayList<String[]> readFile(String path) throws IOException {
        ArrayList<String> tmp = new ArrayList<>();
        String pathName = MapLoader.class.getResource(path).getFile();// 檔案路徑名稱
        pathName = java.net.URLDecoder.decode(pathName, "UTF-8");// 路徑解碼
        File filename = new File(pathName);// 欲讀取之txt檔
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";// 存取txt讀取字串
        // 逐行讀取整筆檔案
        while ((line = br.readLine()) != null) {
            tmp.add(line);
        }
        ArrayList<String[]> filterArr = new ArrayList<>();// 將讀取的檔案存為陣列

        tmp.forEach(new Consumer() {
            @Override
            public void accept(Object a) {
                String[] tmp = new String[4];
                tmp[0] = ((String) a).split(",")[0];// 圖片名
                tmp[1] = ((String) a).split(",")[1];// 色號
                tmp[2] = ((String) a).split(",")[3];// 圖片寬
                tmp[3] = ((String) a).split(",")[4];// 圖片高
                filterArr.add(tmp);
            }
        });
        return filterArr;
    }
}
