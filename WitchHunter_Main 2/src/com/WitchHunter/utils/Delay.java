package com.WitchHunter.utils;

/** 延遲計時器 */
public class Delay {

    /**剛剛改這邊*/
    public int count; //計算幀數
    private int countLimit; //計算幀數極值
    private boolean isPause; //確認是否暫停計算
    private boolean isLoop; //確認是否反覆使用

    public Delay(int countLimit) {
        this.count = 0;
        this.countLimit = countLimit;
        this.isPause = true;
        this.isLoop = false;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCountLimit(int countLimit) {
        this.countLimit = countLimit;
    }

    public void stop() {
        this.isPause = true;
        this.isLoop = false;
        count = 0;
    }

    public void pause() {
        this.isPause = true;
        this.isLoop = false;
    }

    public void play() {
        this.isPause = false;
        this.isLoop = false;
    }

    public void loop() {
        isPause = false;
        isLoop = true;
    }

    /** 計算是否延遲結束 */
    public boolean count() {
        if(isPause) {
            return false;
        }
        if(count >=  countLimit) {
            if(isLoop) {
                count = 0;
            } else {
                stop();
            }
            return true;
        }
        count++;
        return false;

//        return !isPause && count++ >= countLimit ;
    }
}
